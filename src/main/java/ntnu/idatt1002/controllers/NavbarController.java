package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import ntnu.idatt1002.App;

import java.io.IOException;

public class NavbarController {


    public void buttonDashboard(ActionEvent event) throws IOException {
        accessMainController().setMainContent("dashboard");
    }

    public void buttonAccount(ActionEvent event) throws IOException {
        accessMainController().setMainContent("account");
    }

    public void buttonLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    public MainController accessMainController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
        AnchorPane frame = fxmlLoader.load();
        MainController c = (MainController) fxmlLoader.getController();
        return c;
    }
}
