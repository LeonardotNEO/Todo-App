package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.LoginService;

import java.io.IOException;

public class LoginController {

    @FXML Label errorMessageLabel;
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
        String errorMessage = "";

        if(!LoginService.checkIfLoginSyntaxValid(usernameField.getText(), passwordField.getText())){
            errorMessage += "Username or password cant be empty \n";
        }

        if(errorMessage.isEmpty()){
            boolean login = LoginService.checkIfLoginValid(usernameField.getText(), passwordField.getText());

            if(login){
                LoginService.saveLogin(usernameField.getText());
                App.setRoot("main");
            } else {
                errorMessage += "Username or password is wrong";
            }
        }

        errorMessageLabel.setText(errorMessage);
    }
}
