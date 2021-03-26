package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;

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
    public static void saveLogin(String username, boolean rememberMe){
        // Set UserState
        UserStateService.setCurrentUserUsername(username);

        // Set selectedCategory to the first one
        if(CategoryService.getCategoriesCurrentUser().length > 0){
            UserStateService.setCurrentUserCategory(CategoryService.getCategoriesCurrentUser()[0]);
        }

        if(rememberMe) {
            UserStateService.setCurrentUserRememberMe(true);
        } else {
            UserStateService.setCurrentUserRememberMe(false);
        }
    }

    /**
     * Sets userstate to null
     */
    public static void logOut(){
        UserStateService.setCurrentUserUsername(null);
        UserStateService.setCurrentUserCategory(null);
        UserStateService.setCurrentUserSorting(null);
        UserStateService.setCurrentUserRememberMe(false);
    }
}
