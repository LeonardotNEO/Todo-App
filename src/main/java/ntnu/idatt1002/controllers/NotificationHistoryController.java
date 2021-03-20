package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.Notification;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.dao.UserStateDAO;
import ntnu.idatt1002.service.NotificationService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class NotificationHistoryController {

    @FXML private VBox notificationsVBox;

    /**
     * At initializing of NotificationHistory.fxml we load in all notifcations of the user
     */
    public void initialize(){
        // When notificationHistoryPage is loaded, put in notifications to VBox
        addNotificationsToPanel(NotificationService.getNotificationsByUser());
    }

    public void newNotification(ActionEvent event) throws IOException {
        Random random = new Random(); // only for testing atm. We should make notification templates generated for different situations

        if(NotificationService.newNotification("Notification " + random.nextInt(), "this is some description")){
            MainController.getInstance().setMainContent("notificationHistory");
            MainController.getInstance().setNavbar("navbar");
        }

    }

    /**
     * Method for adding notification UI elements to NotificationHistory.fxml
     * @param notifications
     */
    public void addNotificationsToPanel(ArrayList<Notification> notifications){
        notifications.forEach(notification -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/notification.fxml"));

            BorderPane notif = null;
            try {
                notif = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            NotificationController notificationController = loader.getController();

            // add properties to notification UI
            notificationController.setTitle(notification.getTitle());
            notificationController.setDescription(notification.getDescription());

            String[] fullDate = notification.getDateDue().split("[T.]");
            String date = fullDate[0];
            String clock = fullDate[1];
            notificationController.setDueDate("This notification is due at date: " + date + " and time: " + clock);

            // add notifUI to VBox
            notificationsVBox.getChildren().add(notif);
        });


    }

}
