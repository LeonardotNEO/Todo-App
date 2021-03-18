package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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

    public void initialize(){
        ArrayList<Notification> notifications = NotificationService.getNotificationsByUser();

        notifications.forEach(notification -> {
            Pane pane = null;
            try {
                pane = FXMLLoader.load(getClass().getResource("/fxml/notification.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Text title = (Text) pane.lookup("#title");
            title.setText(notification.getTitle());

            Text description = (Text) pane.lookup("#description");
            description.setText(notification.getDescription());

            notificationsVBox.getChildren().add(0, pane);
        });
    }

    public void newNotification(ActionEvent event) throws IOException {
        Random random = new Random();
        NotificationService.newNotification("Notification " + random.nextInt(), "this is some description");

        MainController.getInstance().setMainContent("notificationHistory");
    }

}
