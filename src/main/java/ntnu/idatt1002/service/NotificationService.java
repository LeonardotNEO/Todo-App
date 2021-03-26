package ntnu.idatt1002.service;
import ntnu.idatt1002.Notification;
import ntnu.idatt1002.dao.NotificationDAO;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;

/**
 * A class which provides some necessary features which utilises notification-data
 */
public class NotificationService {

    /**
     * Communicates with NotificationDAO to fetch notifications for current user
     * @return
     */
    public static ArrayList<Notification> getNotificationsByUser(){
        return NotificationDAO.getNotifsByUser(UserStateService.getCurrentUserUsername());
    }

    /**
     * Get all checked notifications from current user
     * @return
     */
    public static ArrayList<Notification> getNotCheckedNotifications(){
        ArrayList<Notification> notificationsChecked = new ArrayList<>();

        getNotificationsByUser().forEach(notification -> {
            if(!notification.getChecked()){
                notificationsChecked.add(notification);
            }
        });

        return notificationsChecked;
    }

    /**
     * Communicates with NotificationDAO to add new notification to current user
     * @param title
     * @param description
     * @return
     */
    public static boolean newNotification(String title, String description){
        Notification notification = new Notification(title, UserStateService.getCurrentUserUsername(), description, Clock.systemDefaultZone());
        NotificationDAO.serializeNotif(notification);
        return true;
    }

    /**
     * Setting the checked variable in notification specified by id to true
     * @param id
     * @return
     * @throws IOException
     */
    public static boolean checkNotification(int id) throws IOException {
        Notification notification = NotificationDAO.deserializeNotif(UserStateService.getCurrentUserUsername(), id);
        notification.setChecked(true);
        NotificationDAO.serializeNotif(notification);

        return true;
    }

}
