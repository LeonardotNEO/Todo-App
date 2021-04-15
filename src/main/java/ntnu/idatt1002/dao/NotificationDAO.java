package ntnu.idatt1002.dao;

import ntnu.idatt1002.Notification;

import java.io.*;
import java.util.ArrayList;

/**
 * Access notifications objects in storage
 */
public final class NotificationDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String PREFIX = "notif";
    private static final String FILETYPE = ".ser";

    /**
     * Get all notifications for a user
     */
    public static ArrayList<Notification> list(String username){
        File notifDir = new File(notifDir(username));
        String[] filepaths = notifDir.list();
        ArrayList<Notification> notifs = new ArrayList<>();

        if(filepaths != null){
            for(String file : filepaths) {
                notifs.add(deserialize(notifDir.getPath() + "/" + file));
            }
        }

        return notifs;
    }

    /**
     * Save a list of notifications to storage
     */
    public static void serialize(ArrayList<Notification> notifications){
        for(Notification notif : notifications){
            serialize(notif);
        }
    }

    /**
     * Save a single notification to storage
     */
    public static void serialize(Notification notification){
        GenericDAO.serialize(notification, filepath(notification.getUsername(), notification.getNotifId()));
    }

    /**
     * Get a notification object from storage
     * @return {@code null} if file could not be found
     */
    public static Notification deserialize(String username, int id){
        return deserialize(filepath(username, id));
    }

    /**
     * Get notification object by it's filepath
     * @return {@code null} if file could not be found
     */
    private static Notification deserialize(String filepath){
        return (Notification) GenericDAO.deserialize(filepath);
    }

    /**
     * Delete all notifications for a user
     * @return {@code false} if some files could not be deleted
     */
    public static boolean deleteByUser(String username){
        if(!UserDAO.exists(username)){ return false; }

        ArrayList<Notification> notifs = list(username);
        boolean result = true;

        if(notifs == null){ return false; }
        for(Notification notif : notifs){
            if(!delete(notif)){ result = false; }
        }

        return result;
    }

    /**
     * Delete a notification
     * @return {@code false} if file could not be deleted
     */
    public static boolean delete(Notification notification){
        return delete(notification.getUsername(), notification.getNotifId());
    }

    /**
     * Delete a notification given by its user and its hashCode
     * @return {@code false} if file could not be deleted
     */
    public static boolean delete(String username, int id){
        File file = new File(filepath(username, id));
        return file.delete();
    }

    //Get paths
    private static String notifDir(String username){
        return (SAVEPATH + "/Notifications/");
    }

    private static String filepath(String username, int id){
        return (notifDir(username) + PREFIX + id + FILETYPE);
    }
}
