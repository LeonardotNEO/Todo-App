package ntnu.idatt1002.dao;

import ntnu.idatt1002.Notification;
import ntnu.idatt1002.User;

import java.io.*;
import java.util.ArrayList;

/**
 * The class {@code NotificationDAO} provides static methods for handling {@link Notification} objects.
 * In the storage system the objects gets stored under the 'Notification' folder under their respective
 * {@link User} folders.
 */
public final class NotificationDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String PREFIX = "notif";
    private static final String FILETYPE = ".ser";

    /**
     * Returns a {@link ArrayList} of all {@link Notification} objects under a {@link User}.
     * @param username the {@code username} variable in a {@link User} corresponding to the
     *                 {@code username} variable in each {@link Notification}.
     * @return an {@link ArrayList}, will be empty if no objects are stored or if the user doesn't exist.
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
     * Serialize the given {@link ArrayList} of {@link Notification} objects. The list can contain
     * objects belonging to different users, they will be stored in their respective folders.
     * @param notifications an {@link ArrayList} of {@link Notification} objects.
     */
    public static void serialize(ArrayList<Notification> notifications){
        for(Notification notif : notifications){
            serialize(notif);
        }
    }

    /**
     * Serialize the given {@link Notification} object to the storage system. Will be stored under the
     * 'Notifications' folder for a {@link User}.
     * @param notification a {@link Notification} object.
     */
    public static void serialize(Notification notification){
        GenericDAO.serialize(notification, filepath(notification.getUsername(), notification.getNotifId()));
    }

    /**
     * Deserialize a {@link Notification} object from storage that matches the given arguments.
     * @param username the {@code username} variable in a {@link User}.
     * @param id the {@code notifId} of the {@link Notification}.
     * @return a {@link Notification} object, or {@code null} if file could not be found,
     * either the user or a notification with the given {@code id} doesn't exist.
     */
    public static Notification deserialize(String username, int id){
        return deserialize(filepath(username, id));
    }

    /**
     * Deserialize a {@link Notification} object from the given filepath.
     * @param filepath the {@link Notification}'s filepath
     * @return a {@link Notification} object, or {@code null} if file could not be found,
     * either the user or a notification with the given {@code id} doesn't exist.
     */
    private static Notification deserialize(String filepath){
        return (Notification) GenericDAO.deserialize(filepath);
    }

    /**
     * Delete all {@link Notification} objects stored under the given {@link User} folder.
     * @param username the {@code username} variable in a {@link User}.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete,
     * normally if the user doesn't exist.
     */
    public static boolean deleteByUser(String username){
        if(!UserDAO.exists(username)){ return false; }

        ArrayList<Notification> notifs = list(username);
        boolean result = true;

        for(Notification notif : notifs){
            if(!delete(notif)){ result = false; }
        }

        return result;
    }

    /**
     * Delete a {@link Notification} file from storage.
     * @param notification a {@link Notification} object that's stored.
     * @return {@code True} if succesfull. {@code False} if the file couldn't be deleted,
     * normally since the file isn't stored.
     */
    public static boolean delete(Notification notification){
        return delete(notification.getUsername(), notification.getNotifId());
    }

    /**
     * Delete a {@link Notification} file from storage that matches the given arguments.
     * @param username the {@code username} variable in a {@link User}.
     * @param id the {@code notifId} of the {@link Notification}.
     * @return {@code True} if succesfull. {@code False} if the file couldn't be deleted,
     * either the user doesn't exist or a file with the given {@code id} doesn't exist.
     */
    public static boolean delete(String username, int id){
        File file = new File(filepath(username, id));
        return file.delete();
    }

    //Get paths
    private static String notifDir(String username){
        return (SAVEPATH + "/" + username + "/Notifications/");
    }

    private static String filepath(String username, int id){
        return (notifDir(username) + PREFIX + id + FILETYPE);
    }
}
