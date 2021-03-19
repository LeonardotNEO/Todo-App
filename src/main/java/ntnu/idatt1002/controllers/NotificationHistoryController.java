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

public class NotificationHistoryController {

    @FXML private VBox notificationsVBox;
    @FXML private Pane notificationPane;
    @FXML private Text title;
    @FXML private Text description;

    public void initialize(){
        ArrayList<Notification> notifications = NotificationDAO.getNotifsByUser(UserStateDAO.getUserState());

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

            notificationsVBox.getChildren().add(pane);
        });
    }

    public void newNotification(ActionEvent event) throws IOException {
        NotificationService.newNotification("Notification 1", "this is some description");
        NotificationService.newNotification("Notification 2", "this is some description");

    }
}
