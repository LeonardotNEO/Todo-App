package ntnu.idatt1002.dao;

import ntnu.idatt1002.Notification;

import java.util.ArrayList;

/**
 * Acces notifications objects in storage
 */
public final class NotificationDAO {
    private static final GenericDAO<Notification> genericDAO = new GenericDAO<>();
    private static final String PREFIX = "notif";

    /**
     * Get all notifications stored in user folder
     * @return {@code null} if user could not be found
     */
    public static ArrayList<Notification> getNotifsByUser(String username){
        ArrayList<Notification> notifications = new ArrayList<>();
        for(Object obj : genericDAO.getElementsByUser(username, PREFIX)){
            notifications.add((Notification) obj);
        }
        return notifications;
    }

    /**
     * Save an {@code ArrayList} of notifications to their owner folder
     */
    public static void saveNotifsToUser(ArrayList<Notification> notifications){
        for(Notification notification : notifications) {
            serializeNotif(notification);
        }
    }

    /**
     * Save notification to storage
     */
    public static void serializeNotif(Notification notification){
        genericDAO.serializeElement(notification, PREFIX, notification.getUsername(),
                notification.hashCode());
    }

    /**
     * Get a single notification given by ID and owner
     * @param username which user that owns the notification
     * @param notifID notification hashcode
     * @return {@code null} if user or notification could not be found
     */
    public static Notification deserializeNotif(String username, int notifID){
        return (Notification) genericDAO.deserializeElement(username, PREFIX, notifID);
    }

    /**
     * Delete all notifications for a user
     * @return {@code false} if one or more could not be deleted
     */
    public static boolean deleteNotifsByUser(String username){
        return deleteNotifs(getNotifsByUser(username));
    }

    /**
     * Delete an array of notifications
     * @return {@code false} if one or more could not be deleted
     */
    public static boolean deleteNotifs(ArrayList<Notification> notifications){
        boolean success = true;
        for(Notification notification : notifications){
            if(!deleteNotif(notification)){
                success = false;
            }
        }
        return success;
    }

    /**
     * Delete a single notification
     * @return {@code false} if notification could not be deleted
     */
    public static boolean deleteNotif(Notification notification){
        return genericDAO.deleteElement(notification, PREFIX, notification.getUsername(),
                notification.hashCode());
    }
}
