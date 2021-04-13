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
     * Get all notifications stored in user folder
     * @param username non case-sensitive username
     * @return {@code ArrayList} of all notifications, if user could not be found the list will be empty
     */
    public static ArrayList<Notification> getNotifsByUser(String username){
        ArrayList<Notification> notifs = new ArrayList<>();
        File directory = new File(notifsPath(username));
        String[] pathnames = directory.list();

        if(pathnames != null && UserDAO.exists(username)){
            for(String path : pathnames){
                notifs.add(deserializeNotif(directory.getPath() + "/" + path));
            }
        }

        return notifs;
    }

    /**
     * Save a list of noticiations to their respective folders
     * @param notifs an {@code ArrayList} of notifications
     */
    public static void saveNotifs(ArrayList<Notification> notifs){
        for(Notification notif : notifs) {
            serializeNotif(notif);
        }
    }

    /**
     * Save a single notification to storage
     * @param notif a Notification object
     */
    public static void serializeNotif(Notification notif){
        String username = notif.getUsername();
        int notifID = notif.getNotifId();
        File file = new File(filePath(username, notifID));
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(notif);

            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Get a single notification given by owner and ID
     * @param username which user that owns the notification
     * @param notifID notifications hashcode
     * @return {@code null} if notification could not be found
     */
    public static Notification deserializeNotif(String username, int notifID){
        return deserializeNotif(filePath(username, notifID));
    }

    /**
     * Get a single notification by complete filepath
     * @param filepath String of the filepath starting with "src/"
     * @return {@code null} if notification could not be found
     */
    public static Notification deserializeNotif(String filepath){
        File file = new File(filepath);
        Notification notif = null;

        if(!file.exists()){ return null; }

        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            notif = (Notification) ois.readObject();

            ois.close();
            fis.close();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return notif;
    }

    /**
     * Delete all notifications for a user
     * @param username which user to wipe all notifications from
     * @return {@code false} if one or more could not be deleted
     */
    public static boolean deleteNotifsByUser(String username){
        return deleteNotifs(getNotifsByUser(username));
    }

    /**
     * Delete a list of notifications
     * @param notifs an {@code ArrayList} of Notifications
     * @return {@code false} if one or more could not be deleted
     */
    public static boolean deleteNotifs(ArrayList<Notification> notifs){
        boolean success = true;
        for(Notification notif : notifs){
            if(!deleteNotif(notif)){
                success = false;
            }
        }
        return success;
    }

    /**
     * Delete a single notification
     * @param notif a Notification object
     * @return {@code false} if file could not be deleted
     */
    public static boolean deleteNotif(Notification notif){
        String username = notif.getUsername();
        int notifID = notif.hashCode();
        return deleteNotif(filePath(username, notifID));
    }

    /**
     * Delete a single notification by filepath
     * @param filepath String of filepath starting with "src/"
     * @return {@code false} if file could not be deleted
     */
    public static boolean deleteNotif(String filepath){
        File file = new File(filepath);
        return file.delete();
    }

    //PRIVATE STRING FUNCTIONS
    /**
     * Get notifications directory
     */
    private static String notifsPath(String username){
        return (SAVEPATH + "/" + username + "/Notifications/");
    }

    /**
     * Get file path
     */
    private static String filePath(String username, int notifID){
        return (notifsPath(username) + PREFIX + notifID + FILETYPE);
    }
}
