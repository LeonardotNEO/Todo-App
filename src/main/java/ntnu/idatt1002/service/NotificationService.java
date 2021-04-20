package ntnu.idatt1002.service;
import ntnu.idatt1002.Notification;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * A class which provides some necessary features which utilises notification-data
 */
public class NotificationService {

    /**
     * Communicates with NotificationDAO to fetch notifications for current user
     * @return
     */
    public static ArrayList<Notification> getNotificationsByUser(){
        return NotificationDAO.list(UserStateService.getCurrentUserUsername());
    }

    /**
     * Get all not checked and active notifications from current user
     * @return
     */
    public static ArrayList<Notification> getActiveAndNotCheckedNotifications(){
        ArrayList<Notification> notificationsChecked = new ArrayList<>();

        getNotificationsByUser().forEach(notification -> {
            if(!notification.getChecked() && notification.getActive()){
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
    public static boolean newNotification(String title, String description, LocalDateTime issueDate){
        Notification notification = new Notification(title, UserStateService.getCurrentUserUsername(), description, issueDate);
        NotificationDAO.serialize(notification);
        return true;
    }

    /**
     * When using a setter method on a notification, we need to user this editNotification method on that notification in order to serialize it to storage
     * @param notification
     */
    public static void editNotification(Notification notification){
        NotificationDAO.serialize(notification);
    }

    /**
     * Setting the checked variable in notification specified by id to true
     * @param id
     * @return boolean if the process was successful
     * @throws IOException
     */
    public static boolean checkNotification(int id) throws IOException {
        Notification notification = NotificationDAO.deserialize(UserStateService.getCurrentUserUsername(), id);
        notification.setChecked(true);
        NotificationDAO.serialize(notification);

        return true;
    }

    /**
     * check if a notification has become active
     * @param notifications
     */
    public static void checkIfNotificationHasBecomeActive(ArrayList<Notification> notifications){
        notifications.forEach(notification -> {
            if(DateUtils.getAsMs(LocalDateTime.now()) > notification.getDateIssued() && !notification.getActive()){
                notification.setActive(true);
                editNotification(notification);
            }
        });
    }

}
