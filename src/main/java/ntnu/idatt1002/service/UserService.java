package ntnu.idatt1002.service;

import ntnu.idatt1002.Notification;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
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
        return Storage.deleteUser(user);
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
            Storage.editUser(oldUser, newUser);

            // update UserStateService
            UserStateService.setCurrentUserUsername(newUser.getUsername());
            return true;
        } else {
            return false;
        }
    }
}
