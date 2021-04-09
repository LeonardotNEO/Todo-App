package ntnu.idatt1002.controllers;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.LoginService;

import java.io.IOException;

/**
 * A class which contains the buttons related to the login page of the application
 */
public class LoginController {

    @FXML Label errorMessageLabel;
    @FXML TextField usernameField;
    @FXML TextField passwordField;
    @FXML JFXCheckBox rememberMe;

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
                LoginService.saveLogin(usernameField.getText(), rememberMe.isSelected());
                App.setRoot("main");
            } else {
                errorMessage += "Username or password is wrong";
            }
        }

        errorMessageLabel.setText(errorMessage);
    }

    /**
     * Press login button if enter is pressed
     */
    public void onKeyPressed(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            try {
                buttonLogin(new ActionEvent());
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
