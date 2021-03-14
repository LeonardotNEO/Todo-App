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

    public void buttonRegister(ActionEvent event) throws IOException {
        App.setRoot("main");
    }

    public void buttonLogin(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}
