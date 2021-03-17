package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.UserState;
import ntnu.idatt1002.dao.UserDAO;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginService {

    public static boolean checkIfLoginSyntaxValid(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
            return false;
        } else {
            return true;
        }

    }

    public static boolean checkIfLoginValid(String username, String password){
        ArrayList<User> users = UserDAO.getUsers();
        AtomicBoolean result = new AtomicBoolean(false);

        users.forEach(user -> {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                result.set(true);
                System.out.println(user.getUsername().equals(username) + " " + user.getPassword().equals(password));
            }
        });


        return result.get();
    }

    public static void saveLogin(){
        //UserState userState
    }
}
