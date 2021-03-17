package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

public class NavbarController {

    /**
     * Communicate with mainController to updates its main-content to dashboard.fxml when button in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonDashboard(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("dashboard");
    }

    /**
     * Communicate with mainController to updates its main-content to account.fxml when button in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonAccount(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("account");
    }

    /**
     * Communicate with mainController to updates its main-content to notificationHistory.fxml when button on notifcations is clicked
     * @param event
     * @throws IOException
     */
    public void buttonNotificationHistory(ActionEvent event) throws IOException{
        MainController.getInstance().setMainContent("notificationHistory");
    }

    /**
     * method for loading login.fxml
     * @param event
     * @throws IOException
     */
    public void buttonLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
        UserStateService.setCurrentUser(null);
    }



}
