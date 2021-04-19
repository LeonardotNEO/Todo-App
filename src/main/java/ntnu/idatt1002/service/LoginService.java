package ntnu.idatt1002.service;

import ntnu.idatt1002.App;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;

import java.io.IOException;

/**
 * A class which provides some necessary features for the login of teh application
 */
public class LoginService {

    /**
     * Check if login syntax is valid
     * @param username
     * @return
     */
    public static boolean checkIfLoginSyntaxValid(String username){
        if(username.isEmpty()){
            return false;
        } else {
            return true;
        }

    }

    /**
     * Check if user with username and password exists in savefiles
     * @param username
     * @param password
     * @return
     */
    public static boolean checkIfLoginValid(String username, String password){
        boolean result = false;
        User user = UserDAO.deserialize(username);

        if(user != null) {
            if (user.getPassword().isEmpty() || user.getPassword().equals(password)) {
                result = true;
            }
        }


        return result;
    }

    /**
     * When logged in, save user-information to userState savefile
     * @param username
     */
    public static void saveLogin(String username, boolean rememberMe){
        // Set UserState
        UserStateService.setCurrentUserUsername(username);

        UserStateService.getCurrentUser().setRememberMe(rememberMe);
    }

    /**
     * Sets userstate to null
     */
    public static void logOut(){
        UserStateService.setCurrentUserUsername(null);
    }

    /**
     * Method for logging the user in
     * @param username
     * @param rememberMe
     * @throws IOException
     */
    public static void login(String username, boolean rememberMe) throws IOException {
        LoginService.saveLogin(username, rememberMe);
        App.setRoot("main");
        App.updateThemeCurrentUser(UserStateService.getCurrentUser().getTheme());
    }
}
