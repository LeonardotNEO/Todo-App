package ntnu.idatt1002.service;

import ntnu.idatt1002.Notification;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.dao.TaskDAO;
import ntnu.idatt1002.dao.UserDAO;

import java.util.ArrayList;

/**
 * A class which deletes the user currently logged inn
 */
public class UserService {

    /**
     * Deletes the current user logged inn.
     * @return false if user failed to be deleted
     */
    public static boolean deleteUser() {
        String user = UserStateService.getCurrentUserUsername();
        if(user == null) return false;
        UserStateService.setCurrentUserUsername(null);
        return UserDAO.deleteUser(user);
    }

    /**
     * Method used for editing a user.
     * We transfer values from old user to new user, and transfer categories, tasks and notifications
     * @param oldUser
     * @param newUser
     */
    public static boolean editUser(User oldUser, User newUser){
        // check if new users username already exists
        if(RegisterService.checkIfUsernameValid(newUser.getUsername()) || UserStateService.getCurrentUserUsername().equals(newUser.getUsername())){
            // create new user
            UserDAO.serializeUser(newUser);

            // transfer categories
            String[] categories = CategoryDAO.getCategoriesByUser(oldUser.getUsername());
            for(String category : categories){
                CategoryDAO.addCategory(newUser.getUsername(), category);
            }

            // transfer tasks
            ArrayList<Task> tasks = TaskDAO.getTasksByUser(oldUser.getUsername());
            tasks.forEach(task -> {
                task.setUserName(newUser.getUsername());
            });
            TaskDAO.saveTasks(tasks);

            // transfer notifications
            ArrayList<Notification> notifications = NotificationDAO.getNotifsByUser(oldUser.getUsername());
            notifications.forEach(notif -> {
                notif.setUsername(newUser.getUsername());
            });
            NotificationDAO.saveNotifs(notifications);


            // delete old user
            if(!UserStateService.getCurrentUserUsername().equals(newUser.getUsername())){
                UserDAO.deleteUser(oldUser.getUsername());
            }

            // update UserStateService
            UserStateService.setCurrentUserUsername(newUser.getUsername());
            return true;
        } else {
            return false;
        }
    }
}
