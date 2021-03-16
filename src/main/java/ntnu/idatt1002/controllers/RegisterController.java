package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ntnu.idatt1002.App;

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
        boolean userSuccesfullyRegistered = true; // uses registerSerivice to register a new user. has usernameField.getText(), passwordField.getText() and repeatPasswordField.getText() as parameters.

        if(userSuccesfullyRegistered){
            App.setRoot("main");
        } else {
            errorMessageText.setText("Could not register new user!");
        }
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
