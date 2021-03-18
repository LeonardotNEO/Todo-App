package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ntnu.idatt1002.Notification;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.dao.UserStateDAO;

import java.util.ArrayList;

public class NotificationHistoryController {

    @FXML private VBox notificationsVBox;
    @FXML private Pane notificationPane;

    public void initialize(){
        ArrayList<Notification> notifications = NotificationDAO.getNotifsByUser(UserStateDAO.getUserState());

        notifications.forEach(notification -> {

            notificationsVBox.getChildren().set(0, notificationPane);
        });
    }
}
