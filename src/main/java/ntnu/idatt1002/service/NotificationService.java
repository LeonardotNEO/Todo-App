package ntnu.idatt1002.service;
import ntnu.idatt1002.Notification;
import ntnu.idatt1002.dao.NotificationDAO;

import java.util.ArrayList;

public class NotificationService {

    public static ArrayList<Notification> getNotificationsByUser(String username){
        return NotificationDAO.getNotifsByUser(username);
    }


}
