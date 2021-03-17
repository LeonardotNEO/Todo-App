package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.RegisterService;

import java.io.IOException;

public class RegisterController {

    @FXML Text errorMessageText;
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

        if (!RegisterService.checkIfPasswordValid(passwordField.getText(), repeatPasswordField.getText())){
            errorMessage += "Password syntax is wrong \n";
        } else if(!RegisterService.checkIfUsernameValid(usernameField.getText())){
            errorMessage += "Username is not valid \n";
        } else {
            boolean userSuccesfullyRegistered = RegisterService.registerNewUser(usernameField.getText(), passwordField.getText());

            if(userSuccesfullyRegistered){
                App.setRoot("main");
            } else {
                errorMessage += "Error in saving user to storage! \n";
            }
        }

        errorMessageText.setText(errorMessage);
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
