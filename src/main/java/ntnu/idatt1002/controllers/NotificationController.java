package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ntnu.idatt1002.Notification;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.service.NotificationService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

/**
 * A class which contains the methods related to a notification
 */
public class NotificationController {

    private int notificationID;
    @FXML private Label title;
    @FXML private Label description;

    public void display(Notification notification){
        notificationID = notification.getNotifId();
        title.setText(notification.getTitle());
        description.setText(notification.getDescription());
    }

    public void checkNotification() throws IOException {
        Notification notification = NotificationDAO.deserializeNotif(UserStateService.getCurrentUser().getUsername(), notificationID);
        notification.setChecked(true);
        NotificationService.editNotification(notification);

        // update UI of popup
        VBox notificationMenuVBox = (VBox) NavbarController.getNotificationMenuPopup().getChildren().get(1);
        NavbarController.updateNotificationBellPopup(notificationMenuVBox);
    }
}
