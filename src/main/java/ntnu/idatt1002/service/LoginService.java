package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;

public class LoginService {

    public static boolean checkIfLoginSyntaxValid(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            return false;
        } else {
            return true;
        }

    }

    public static boolean checkIfLoginValid(String username, String password){
        boolean result = false;
        User user = UserDAO.deserializeUser(username);

        if(user.getPassword().equals(password)){
            result = true;
        }

        return result;
    }

    public static void saveLogin(){
        //UserState userState
    }
}
