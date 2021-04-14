package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import java.io.IOException;

public class NotificationMenuPopupController {

    /**
     * Communicate with mainController to update its main-content to notificationHistory.fxml when button in notificationBell-menu is clicked
     * @param event
     * @throws IOException
     */
    public void buttonNotificationHistory(ActionEvent event) throws IOException {
        // open notification History page
        MainController.getInstance().setMainContent("notificationHistory");

        // close popup
        NavbarController.hideNotificationMenuPopup();
    }
}
