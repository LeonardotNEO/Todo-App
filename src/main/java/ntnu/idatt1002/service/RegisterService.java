package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.UserState;
import ntnu.idatt1002.dao.UserDAO;

public class RegisterService {

    public static boolean registerNewUser(String name, String password){
        User newUser = new User(name, password, UserDAO.generateSalt());
        UserDAO.serializeUser(newUser);

        UserState userState = new UserState();
        userState.setUserState(newUser);
        return true;
    }

    public static boolean checkIfPasswordValid(String password, String repeatPassword){
        if(password.equals(repeatPassword) && password.length() > 6 && repeatPassword.length() > 6){
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIfUsernameValid(String username){
        if(username.length() > 3){
            return true;
        } else {
            return false;
        }
    }
}
