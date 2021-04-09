package ntnu.idatt1002.controllers;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.LoginService;
import ntnu.idatt1002.service.RegisterService;

import java.io.IOException;

/**
 * A class which contains the buttons related to the register page of the application
 */
public class RegisterController {

    @FXML Label errorMessageLabel;
    @FXML TextField usernameField;
    @FXML TextField passwordField;
    @FXML TextField repeatPasswordField;
    @FXML JFXCheckBox rememberMe;

    /**
     * Method that register a new user
     * @param event
     * @throws IOException
     */
    public void buttonRegister(ActionEvent event) throws IOException {
        String errorMessage = "";

        if(!RegisterService.checkIfUsernameValidSyntax(usernameField.getText())){
            errorMessage += "Username must be more than 0 characters \n";
        }
        if(!RegisterService.checkIfUsernameValid(usernameField.getText())){
            errorMessage += "Username already exists, choose another username \n";
        }

        if (!RegisterService.checkIfPasswordValid(passwordField.getText(), repeatPasswordField.getText())){
            errorMessage += "The passwords do not match \n";
        } else if(!RegisterService.checkIfPasswordValidSyntax(passwordField.getText(), repeatPasswordField.getText())){
            errorMessage += "Password must be more than 6 characters \n";
        }


        if(errorMessage.isEmpty()){
            boolean userSuccesfullyRegistered = RegisterService.registerNewUser(usernameField.getText(), passwordField.getText(), rememberMe.isSelected());

            if(userSuccesfullyRegistered){
                App.setRoot("main");
                LoginService.saveLogin(usernameField.getText(), rememberMe.isSelected());
            } else {
                errorMessage += "Error in saving user to storage! \n";
            }
        }

        errorMessageLabel.setText(errorMessage);
    }

    /**
     * Loads login stage
     * @param event
     * @throws IOException
     */
    public void buttonLogin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}
