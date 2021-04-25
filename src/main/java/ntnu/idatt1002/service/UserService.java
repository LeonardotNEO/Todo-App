package ntnu.idatt1002.service;

import ntnu.idatt1002.model.Notification;
import ntnu.idatt1002.model.Task;
import ntnu.idatt1002.model.User;
import ntnu.idatt1002.dao.*;

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
        return UserDAO.delete(user);
    }

    /**
     * Method used for editing a user.
     * We transfer values from old user to new user, and transfer projects, categories, tasks and notifications
     * @param oldUser
     * @param newUser
     */
    public static void editUser(User oldUser, User newUser){
        // check if new users username already exists
        if(RegisterService.checkIfUsernameValid(newUser.getUsername()) || UserStateService.getCurrentUserUsername().equals(newUser.getUsername())){
            // create new user
            UserDAO.serialize(newUser);

            // transfer categories
            String[] categories = CategoryDAO.list(oldUser.getUsername());
            for(String category : categories){
                CategoryDAO.add(newUser.getUsername(), category);
            }
            
            // transfer notifications
            ArrayList<Notification> notifications = NotificationDAO.list(oldUser.getUsername());
            notifications.forEach(notif -> notif.setUsername(newUser.getUsername()));
            NotificationDAO.serialize(notifications);

            // transfer projects
            String[] projects = ProjectService.getProjectsCurrentUser();
            for(String project : projects){
                ProjectDAO.add(newUser.getUsername(), project);

                // transfer categories of project
                String[] projectCategories = CategoryDAO.list(oldUser.getUsername(), project);
                for(String projectCategory : projectCategories){
                    CategoryDAO.add(newUser.getUsername(), project, projectCategory);
                }
            }

            // transfer tasks
            ArrayList<Task> tasks = TaskDAO.list(oldUser.getUsername());
            tasks.forEach(task -> task.setUserName(newUser.getUsername()));
            TaskDAO.serialize(tasks);

            // delete old user
            if(!UserStateService.getCurrentUserUsername().equals(newUser.getUsername())){
                UserDAO.delete(oldUser.getUsername());
            }

            // update UserStateService
            UserStateService.setCurrentUserUsername(newUser.getUsername());
        }
    }
}
