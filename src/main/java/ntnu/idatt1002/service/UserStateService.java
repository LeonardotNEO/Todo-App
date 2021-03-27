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
     * Method that returns the String category stored in userstate.ser savefile
     * @return
     */
    public static String getCurrentUserCategory(){
        return UserStateDAO.getSelectedCategory();
    }

    /**
     * Method that returns the String sorting stored in userstate.ser savefile
     * @return
     */
    public static String getCurrentUserSorting(){
        return UserStateDAO.getSelectedSort();
    }

    public static boolean getCurrentUserRememberMe(){
        return UserStateDAO.getRememberMe();
    }

    /**
     * Set the name of the current user to userstate.ser
     * @param username
     */
    public static void setCurrentUserUsername(String username){
        UserStateDAO.setUserState(username, UserStateDAO.getSelectedCategory(), UserStateDAO.getSelectedSort(), UserStateDAO.getRememberMe());
    }

    /**
     * Set the category of the current user
     * @param selectedCategory
     */
    public static void setCurrentUserCategory(String selectedCategory){
        UserStateDAO.setUserState(UserStateDAO.getUsername(), selectedCategory, UserStateDAO.getSelectedSort(), UserStateDAO.getRememberMe());
    }

    /**
     * Set the sorting of the current user
     * @param selectedSorting
     */
    public static void setCurrentUserSorting(String selectedSorting){
        UserStateDAO.setUserState(UserStateDAO.getUsername(), UserStateDAO.getSelectedCategory(), selectedSorting, UserStateDAO.getRememberMe());
    }

    /**
     * Set the remember me value of user
     * @param rememberMe
     */
    public static void setCurrentUserRememberMe(boolean rememberMe){
        UserStateDAO.setUserState(UserStateDAO.getUsername(), UserStateDAO.getSelectedCategory(), UserStateDAO.getSelectedSort(), rememberMe);
    }
}
