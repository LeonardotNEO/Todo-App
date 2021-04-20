package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
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
        if(UserStateDAO.getUsername() != null){
            return true;
        } else {
            return false;
        }
    }

    /**
     * methode for calling getting the current user
     * @return
     */
    public static User getCurrentUser(){
        return UserDAO.deserialize(UserStateService.getCurrentUserUsername());
    }

    /**
     * Method that return the string username stored in userstate.ser savefile
     * @return
     */
    public static String getCurrentUserUsername(){
        return UserDAO.deserialize(UserStateDAO.getUsername()).getUsername();
    }

    /**
     * Set the name of the current user to userstate.ser
     * @param username
     */
    public static void setCurrentUserUsername(String username){
        UserStateDAO.setUserState(username, UserStateDAO.getSelectedCategory(), UserStateDAO.getSelectedSort(), UserStateDAO.getRememberMe());
    }
}
