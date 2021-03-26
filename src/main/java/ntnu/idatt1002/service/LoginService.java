package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.dao.UserStateDAO;

/**
 * A class which provides some necessary features for the login of teh application
 */
public class LoginService {

    /**
     * Check if login syntax is valid
     * @param username
     * @param password
     * @return
     */
    public static boolean checkIfLoginSyntaxValid(String username, String password){
        if(username.isEmpty() || password.isEmpty()){
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
        User user = UserDAO.deserializeUser(username);

        if(user != null && user.getPassword().equals(password)){
            result = true;
        }


        return result;
    }

    /**
     * When logged in, save user-information to userState savefile
     * @param username
     */
    public static void saveLogin(String username){
        UserStateService.setCurrentUserUsername(username);
        if(CategoryService.getCategoriesCurrentUser().length > 0){
            UserStateService.setCurrentUserCategory(CategoryService.getCategoriesCurrentUser()[0]);
        }
    }
}
