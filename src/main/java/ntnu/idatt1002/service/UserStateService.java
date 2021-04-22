package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.Storage;
import ntnu.idatt1002.dao.UserStateDAO;

/**
 * Class communicates with UserStateDAO to fetch current userData.
 */
public class UserStateService {

    /**
     * Method for checking if userState contains an current user
     * @return true if there currently is a saved user
     */
    public static boolean checkIfUserState(){
        return UserStateDAO.getUsername() != null;
    }

    /**
     * methode for calling getting the current user
     * @return
     */
    public static User getCurrentUser(){
        return Storage.getUser(UserStateService.getCurrentUserUsername());
    }

    /**
     * Method that return the string username stored in userstate.ser savefile
     * @return
     */
    public static String getCurrentUserUsername(){
        return UserStateDAO.getUsername();
    }

    /**
     * Set the name of the current user to userstate.ser
     * @param username
     */
    public static void setCurrentUserUsername(String username){
        UserStateDAO.setUserState(username, UserStateDAO.getSelectedCategory(), UserStateDAO.getSelectedSort(), UserStateDAO.getRememberMe());
    }
}
