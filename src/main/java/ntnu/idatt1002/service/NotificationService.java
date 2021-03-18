package ntnu.idatt1002.service;
import ntnu.idatt1002.Notification;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.dao.UserStateDAO;

import java.util.ArrayList;

public class NotificationService {

    public static ArrayList<Notification> getNotificationsByUser(String username){
        return NotificationDAO.getNotifsByUser(username);
    }

    public static void newNotification(String title, String description){
        Notification notification = new Notification(title, UserStateService.getCurrentUser().getUsername(), description);

        NotificationDAO.serializeNotif(notification);
    }

}
