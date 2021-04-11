package ntnu.idatt1002.service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.dao.UserLogDAO;

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
        UserDAO.serialize(newUser);
        UserLogDAO.setUserRegistration(name);

        // Set current user to this username
        UserStateService.setCurrentUserUsername(name);

        // Update UserState to rember if user want to be remembered
        UserStateService.getCurrentUser().setRememberMe(rememberMe);

        // Add premade categories to user
        for (String premadeCategory : CategoryService.getPremadeCategories()) {
            CategoryService.addCategoryToCurrentUser(premadeCategory);
        }

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
    public static boolean checkIfUsernameValidSyntax(String username){
        if(username.length() > 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for checking if user already exists
     * @param username
     * @return
     */
    public static boolean checkIfUsernameValid(String username){
        boolean result = true;

        for(User user : UserDAO.getUsers()){
            if(user.getUsername().equals(username)){
                result = false;
            }
        }

        return result;
    }
}
