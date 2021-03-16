package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ntnu.idatt1002.App;

import java.io.IOException;

public class LoginController {

    @FXML Text errorMessageText;
    @FXML TextField usernameField;
    @FXML TextField passwordField;

    /**
     * Loads login page to stage
     * @param event
     * @throws IOException
     */
    public void buttonRegister(ActionEvent event) throws IOException{
        App.setRoot("register");
    }

    /**
     * Logs the user in, uses loginService to check if input is valid
     * @param event
     * @throws IOException
     */
    public void buttonLogin(ActionEvent event) throws IOException{
        boolean login = true; // boolean login needs to check with loginService if credentials are OK (send in usernameField.getText() and passwordField.getText())

        if(login){
            App.setRoot("main");

            // Should set userState to the user with input in usernamefield
        } else {
            errorMessageText.setText("Login credentials are wrong!");
        }
    }
}
