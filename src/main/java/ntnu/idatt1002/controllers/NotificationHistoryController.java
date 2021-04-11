package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ntnu.idatt1002.Notification;
import ntnu.idatt1002.service.NotificationService;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class which contains the methods related to the notification history page of the application
 */
public class NotificationHistoryController {

    @FXML private VBox notificationsVBox;

    /**
     * At initializing of NotificationHistory.fxml we load in all notifications of the user
     */
    public void initialize(){
        // When notificationHistoryPage is loaded, put in notifications to VBox
        addNotificationsToPanel(NotificationService.getNotificationsByUser());
    }

    /**
     * When newNotificationButton is pressed, add a new notification UI element
     * @param event
     * @throws IOException
     */
    public void newNotification(ActionEvent event) throws IOException {

    }

    /**
     * Method for adding notification UI elements to NotificationHistory.fxml
     * @param notifications
     */
    public void addNotificationsToPanel(ArrayList<Notification> notifications){
        notifications.forEach(notification -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/notification.fxml"));

            AnchorPane notif = null;
            try {
                notif = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            NotificationController notificationController = loader.getController();

            // add properties to notification UI
            notificationController.display(notification);

            // add notifUI to VBox
            notificationsVBox.getChildren().add(notif);
        });


    }

}
