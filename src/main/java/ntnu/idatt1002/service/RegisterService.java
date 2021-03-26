package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RegisterService {

    /**
     * Communicates with UserDAO to register new user
     * @param name
     * @param password
     * @return
     */
    public static boolean registerNewUser(String name, String password, boolean rememberMe){
        User newUser = new User(name, password, UserDAO.generateSalt());

        // Update savefiles to include this new user
        UserDAO.serializeUser(newUser);

        // Set current user to this username
        UserStateService.setCurrentUserUsername(name);

        // Update UserState to rember if user want to be remembered
        if(rememberMe){
            UserStateService.setCurrentUserRememberMe("true");
        } else {
            UserStateService.setCurrentUserRememberMe("false");
        }

        // Add empty categories to user
        CategoryService.addCategoryToCurrentUser("Trash bin");
        CategoryService.addCategoryToCurrentUser("Finished tasks");

        return true;
    }

    /**
     * Method for checking if password syntax is valid
     * @param password
     * @param repeatPassword
     * @return
     */
    public static boolean checkIfPasswordValidSyntax(String password, String repeatPassword){
        if(password.length() > 6 || repeatPassword.length() > 6){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for checking password and repeat-password are equal
     * @param password
     * @param repeatPassword
     * @return
     */
    public static boolean checkIfPasswordValid(String password, String repeatPassword){
        if(password.equals(repeatPassword)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for checking if syntax of username is valid
     * @param username
     * @return
     */
    public static boolean checkIfUsernameValid(String username){
        if(username.length() > 3){
            return true;
        } else {
            return false;
        }
    }
}
