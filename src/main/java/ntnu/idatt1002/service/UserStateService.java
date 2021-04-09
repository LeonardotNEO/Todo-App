package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.dao.UserStateDAO;

/**
 * Class communicates with UserStateDAO to fetch current userData.
 * When player logs inn, userstate.ser is updated. This makes it possible to skip login if user did not log out last time.
 * When player logs out, userstate.ser is set to null.
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

    public static User getCurrentUser(){
        return UserDAO.deserializeUser(UserStateService.getCurrentUserUsername());
    }

    /**
     * Method that return the string username stored in userstate.ser savefile
     * @return
     */
    public static String getCurrentUserUsername(){
        return UserDAO.deserializeUser(UserStateDAO.getUsername()).getUsername();
    }

    /**
     * Set the name of the current user to userstate.ser
     * @param username
     */
    public static void setCurrentUserUsername(String username){
        UserStateDAO.setUserState(username, UserStateDAO.getSelectedCategory(), UserStateDAO.getSelectedSort(), UserStateDAO.getRememberMe());
    }
}
