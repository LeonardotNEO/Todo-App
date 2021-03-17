package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.LoginService;
import ntnu.idatt1002.service.RegisterService;

import java.io.IOException;

public class RegisterController {

    @FXML Label errorMessageLabel;
    @FXML TextField usernameField;
    @FXML TextField passwordField;
    @FXML TextField repeatPasswordField;

    /**
     * Method that register a new user
     * @param event
     * @throws IOException
     */
    public void buttonRegister(ActionEvent event) throws IOException {
        String errorMessage = "";

        if(!RegisterService.checkIfUsernameValid(usernameField.getText())){
            errorMessage += "Username must be more than 3 characters \n";
        }

        if (!RegisterService.checkIfPasswordValid(passwordField.getText(), repeatPasswordField.getText())){
            errorMessage += "The passwords do not match \n";
        } else if(!RegisterService.checkIfPasswordValidSyntax(passwordField.getText(), repeatPasswordField.getText())){
            errorMessage += "Password must be more than 6 characters \n";
        }


        if(errorMessage.isEmpty()){
            boolean userSuccesfullyRegistered = RegisterService.registerNewUser(usernameField.getText(), passwordField.getText());

            if(userSuccesfullyRegistered){
                App.setRoot("main");
                LoginService.saveLogin(usernameField.getText());
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
