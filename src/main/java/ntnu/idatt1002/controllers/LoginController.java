package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import ntnu.idatt1002.App;

import java.io.IOException;

public class LoginController {

    public void buttonRegister(ActionEvent event) throws IOException{
        App.setRoot("register");
    }

    public void buttonLogin(ActionEvent event) throws IOException{
        App.setRoot("dashboard");
    }
}
