package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        ArrayList<Notification> notifications = NotificationService.getNotificationsByUser();

        notifications.forEach(notification -> {
            Pane pane = null;
            try {
                pane = FXMLLoader.load(getClass().getResource("/fxml/notification.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Label title = (Label) pane.lookup("#title");
            title.setText(notification.getTitle());

            Label description = (Label) pane.lookup("#description");
            description.setText(notification.getDescription());

            Label dueDate = (Label) pane.lookup("#dueDate");
            String[] fullDate = notification.getDateDue().split("[T.]");
            String date = fullDate[0];
            String clock = fullDate[1];
            dueDate.setText("This notification is due at date: " + date + " and time: " + clock);

            notificationsVBox.getChildren().add(0, pane);
        });
    }

    public void newNotification(ActionEvent event) throws IOException {
        Random random = new Random(); // only for testing atm. We should make notification templates generated for different situations

        if(NotificationService.newNotification("Notification " + random.nextInt(), "this is some description")){
            MainController.getInstance().setMainContent("notificationHistory");
            MainController.getInstance().setNavbar("navbar");
        }

    }

}
