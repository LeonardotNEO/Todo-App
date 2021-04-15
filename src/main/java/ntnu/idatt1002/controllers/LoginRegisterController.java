package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ntnu.idatt1002.service.LoginService;
import ntnu.idatt1002.service.RegisterService;

import java.io.IOException;

/**
 * Controller that handles login inn and creation of a new user
 */
public class LoginRegisterController {
    @FXML PasswordField passwordField;
    @FXML PasswordField repeatPasswordField;
    @FXML Button finishButton;
    @FXML CheckBox keepLoggedInn;
    @FXML CheckBox noPassword;
    @FXML TextField usernameField;
    @FXML Label errorMessageLabel;

    /**
     * Initializes all the content and data needed to create a new user.
     */
    public void initializeRegister() {
        finishButton.setText("Register");
        finishButton.setOnAction(actionEvent -> {
            try {
                register();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Initialized the login process with all the required fields
     */
    public void initializeLogin() {
        noPassword.setVisible(false);
        noPassword.setManaged(false);
        repeatPasswordField.setVisible(false);
        repeatPasswordField.setManaged(false);
        finishButton.setText("Login");
        finishButton.setOnAction(actionEvent -> {
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Sets the username in the username field
     * @param username username of the user
     */
    public void setUsername(String username) {
        usernameField.setText(username);
    }

    /**
     * method that handles the login and validates inputs.
     * @throws IOException
     */
    public void login() throws IOException {
        String errorMessage = "";
        if(!LoginService.checkIfLoginValid(usernameField.getText(), passwordField.getText())) {
            errorMessage += "Username or password cant be empty\n";
        }

        if(errorMessage.isEmpty()) {
            boolean loginValid = LoginService.checkIfLoginValid(usernameField.getText(), passwordField.getText());

            if(loginValid) {
                LoginService.login(usernameField.getText(), keepLoggedInn.isSelected());
            } else {
                errorMessage += "Username or password is wrong";
            }
        }
        errorMessageLabel.setText(errorMessage);
    }

    /**
     * method that handles the registration and validates inputs.
     * @throws IOException
     */
    public void register() throws IOException {
        String errorMessage = "";

        if(!RegisterService.checkIfUsernameValidSyntax(usernameField.getText())) {
            errorMessage += "Username must be more than 0 characters \n";
        }
        if(!RegisterService.checkIfUsernameValid(usernameField.getText())) {
            errorMessage += "Username already exists, choose antoher username\n";
        }

        if(!RegisterService.checkIfPasswordValid(passwordField.getText(), repeatPasswordField.getText())) {
            errorMessage += "The passwords do not match \n";
        } else if(!RegisterService.checkIfPasswordValidSyntax(passwordField.getText(), repeatPasswordField.getText())) {
            errorMessage += "Password must be more than 6 characters \n";
        }

        if(errorMessage.isEmpty()) {
            boolean userSuccessfullyRegistered =  RegisterService.registerNewUser(
                        usernameField.getText(),
                        passwordField.getText(),
                        keepLoggedInn.isSelected()
                );

            if(userSuccessfullyRegistered) {
                LoginService.login(usernameField.getText(), keepLoggedInn.isSelected());
            } else {
                errorMessage += "Error in saving user to storage! \n";
            }
        }
        errorMessageLabel.setText(errorMessage);
    }
}
