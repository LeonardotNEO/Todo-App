package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import ntnu.idatt1002.App;

import java.io.IOException;

public class MainController {

    public void buttonLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}
