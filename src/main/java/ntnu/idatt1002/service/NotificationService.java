package ntnu.idatt1002.service;
import ntnu.idatt1002.Notification;
import ntnu.idatt1002.controllers.MainController;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.dao.UserStateDAO;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;

public class NotificationService {

    public static ArrayList<Notification> getNotificationsByUser(){
        return NotificationDAO.getNotifsByUser(UserStateService.getCurrentUser().getUsername());
    }

    /**public static ArrayList<Notification> getNotificationsSortedDate(){

    }*/

    public static ArrayList<Notification> getNotCheckedNotifications(){
        ArrayList<Notification> notificationsChecked = new ArrayList<>();

        getNotificationsByUser().forEach(notification -> {
            if(!notification.getChecked()){
                notificationsChecked.add(notification);
            }
        });

        return notificationsChecked;
    }

    public static void newNotification(String title, String description){
        Notification notification = new Notification(title, UserStateService.getCurrentUser().getUsername(), description, Clock.systemDefaultZone());

        NotificationDAO.serializeNotif(notification);
    }

    public static boolean checkNotification(int id) throws IOException {
        Notification notification = NotificationDAO.deserializeNotif(UserStateService.getCurrentUser().getUsername(), id);
        notification.setChecked(true);
        NotificationDAO.serializeNotif(notification);

        return true;
    }

}
