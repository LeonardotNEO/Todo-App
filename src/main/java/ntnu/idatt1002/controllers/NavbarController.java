package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import ntnu.idatt1002.App;

import java.io.IOException;

public class NavbarController {

    /**
     * We get an istance of MainController and use its setMainContent method. We then specify to load dashboard page into main page content
     * @param event
     * @throws IOException
     */
    public void buttonDashboard(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("dashboard");
    }

    public void buttonAccount(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("account");
    }

    public void buttonLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
}
