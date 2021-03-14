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

    public void buttonRegister(ActionEvent event) throws IOException{
        App.setRoot("register");
    }

    public void buttonLogin(ActionEvent event) throws IOException{
        App.setRoot("main");
    }
}
