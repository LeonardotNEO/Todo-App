package ntnu.idatt1002.service;

import ntnu.idatt1002.model.Notification;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class which provides some necessary features which utilises notification-data.
 */
public class NotificationService {

    /**
     * Communicates with NotificationDAO to fetch notifications for current user.
     * @return
     */
    public static ArrayList<Notification> getNotificationsByCurrentUser(){
        return NotificationDAO.list(UserStateService.getCurrentUser().getUsername());
    }

    /**
     * Method for getting the active notifications by current user.
     * @return
     */
    public static ArrayList<Notification> getActiveNotificationsByCurrentUser(){
        ArrayList<Notification> notifications = new ArrayList<>();

        getNotificationsByCurrentUser().forEach(notification -> {
            if(notification.getActive()){
                notifications.add(notification);
            }
        });

        return notifications;
    }

    /**
     * Get all not checked and active notifications from current user.
     * @return
     */
    public static ArrayList<Notification> getActiveAndNotCheckedNotifications(){
        ArrayList<Notification> notificationsChecked = new ArrayList<>();

        getNotificationsByCurrentUser().forEach(notification -> {
            if(!notification.getChecked() && notification.getActive()){
                notificationsChecked.add(notification);
            }
        });

        return notificationsChecked;
    }

    /**
     * Communicates with NotificationDAO to add new notification to current user.
     * @param title
     * @param description
     */
    public static void newNotification(String title, String description, LocalDateTime issueDate){
        Notification notification = new Notification(title, UserStateService.getCurrentUserUsername(), description, issueDate);
        NotificationDAO.serialize(notification);
    }

    /**
     * When using a setter method on a notification, we need to user this editNotification method on that notification in order to serialize it to storage.
     * @param notification
     */
    public static void editNotification(Notification notification){
        NotificationDAO.serialize(notification);
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
