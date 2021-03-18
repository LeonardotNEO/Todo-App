package ntnu.idatt1002.dao;

import ntnu.idatt1002.Notification;

import java.io.*;
import java.util.ArrayList;

/**
 * Acces notifications in storage
 */
public final class NotificationDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILETYPE = ".ser";

    /**
     * Get all notifications stored in user folder
     * @return {@code null} if user could not be found
     */
    public static ArrayList<Notification> getNotifsByUser(String username){
        //Get all files in a user folder
        ArrayList<Notification> notifications = new ArrayList<>();
        File directory = new File(SAVEPATH + "/" + username);

        //Only get notification files
        FilenameFilter filter = (directory1, name) -> name.startsWith("notif");
        String[] pathnames = directory.list(filter);

        //List through all notification files and add to ArrayList
        if(pathnames != null){
            for(String path : pathnames){
                notifications.add(deserializeNotif(directory.getPath() + "/" + path));
            }
            return notifications;
        }else{
            return null;
        }
    }

    /**
     * Save an {@code ArrayList} of notifications to their owner folder
     */
    public static void saveNotifsToUser(ArrayList<Notification> notifications){
        for(Notification notification : notifications){
            serializeNotif(notification);
        }
    }

    /**
     * Save notification to storage
     */
    public static void serializeNotif(Notification notification){
        String username = notification.getUsername();
        int notifID = notification.hashCode();
        File file = new File(SAVEPATH + "/" + username + "/notif" + notifID + FILETYPE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(notification);

            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Get a single notification given by ID and owner
     * @param username which user that owns the notification
     * @param notifID notification hashcode
     * @return {@code null} if user or notification could not be found
     */
    public static Notification deserializeNotif(String username, int notifID){
        String filepath = SAVEPATH + "/" + username + "/notif" + notifID + FILETYPE;
        return deserializeNotif(filepath);
    }

    /**
     * Get a single notification given by filename
     * @return {@code null} if notification could not be found
     */
    private static Notification deserializeNotif(String filepath){
        File file = new File(filepath);
        Notification notification = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            notification = (Notification) ois.readObject();

            ois.close();
            fis.close();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return notification;
    }
}
