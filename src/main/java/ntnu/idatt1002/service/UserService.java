package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;

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
}