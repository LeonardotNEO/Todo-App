package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ntnu.idatt1002.model.Notification;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.service.NotificationService;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;

/**
 * Controller for notification.fxml
 */
public class NotificationController {

    private int notificationID;
    @FXML private Label title;
    @FXML private Label description;
    @FXML private Label date;
    @FXML private Button checkNotification;

    /**
     * Method used for displaying a notification UI element
     * @param notification The notification object
     * @param showCheckmark boolean for checking if we want to show checkmark on this notification UI element
     */
    public void display(Notification notification, boolean showCheckmark){
        notificationID = notification.getNotifId();
        title.setText(notification.getTitle());
        description.setText(notification.getDescription());
        date.setText(DateUtils.getFormattedFullDate(notification.getDateActive()));

        if(!showCheckmark){
            checkNotification.setVisible(false);
            checkNotification.setManaged(false);
        }
    }

    /**
     * Method for handling when the checkNotification button is pressed
     * @throws IOException
     */
    public void checkNotification() throws IOException {
        Notification notification = NotificationDAO.deserialize(UserStateService.getCurrentUser().getUsername(), notificationID);
        notification.setChecked(true);
        NotificationService.editNotification(notification);

        // update UI of popup
        VBox notificationMenuVBox = (VBox) NavbarController.getNotificationMenuPopup().getChildren().get(1);
        NavbarController.updateNotificationBellPopup(notificationMenuVBox);
    }

}
