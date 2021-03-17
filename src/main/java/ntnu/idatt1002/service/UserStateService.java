package ntnu.idatt1002.service;


import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;

/**
 * Class communcating with UserStateDAO to fetch current userData.
 * When player logs inn, userData is updated. This makes it possible to skip login if user did not log out last time.
 * When player logs out, userData is set to null.
 */
public class UserStateService {

    public boolean checkIfUserState(){
        boolean found = false;


        return false;
    }

    public static User getCurrentCurrentUser(){
        //return UserDAO.deserializeUser(//Get username from UserStateDAO)
        return new User();
    }
}
