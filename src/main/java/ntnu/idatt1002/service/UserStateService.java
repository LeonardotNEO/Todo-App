package ntnu.idatt1002.service;


import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.dao.UserStateDAO;

/**
 * Class communcating with UserStateDAO to fetch current userData.
 * When player logs inn, userstate.ser is updated. This makes it possible to skip login if user did not log out last time.
 * When player logs out, userstate.ser is set to null.
 */
public class UserStateService {

    /**
     * Method for checking if userState contains an current user
     * @return
     */
    public static boolean checkIfUserState(){
        if(UserStateDAO.getUserState() != null){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that return the user stored in userstate.ser savefile
     * @return
     */
    public static User getCurrentUser(){
        return UserDAO.deserializeUser(UserStateDAO.getUserState());
    }

    /**
     * Set the name of the current user to userstate.ser
     * @param username
     */
    public static void setCurrentUser(String username){
        UserStateDAO.setUserState(username);
    }
}
