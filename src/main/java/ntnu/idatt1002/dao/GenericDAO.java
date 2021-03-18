package ntnu.idatt1002.dao;

import java.io.*;
import java.util.ArrayList;

/**
 * Generic class for {@code TaskDAO} and {@code NotificationDAO}
 */
class GenericDAO<T> {
    private final String SAVEPATH = "src/main/resources/saves";
    private final String FILETYPE = ".ser";

    /**
     * Get all objects of a Type stored in user folder
     * @param prefix "task" or "notif"
     * @return {@code null} if user could not be found
     */
     ArrayList<Object> getElementsByUser(String username, String prefix){
         //Get all files in a user folder
         ArrayList<Object> objects = new ArrayList<>();
         File directory = new File(SAVEPATH + "/" + username);

         //Only get element files
         FilenameFilter filter = (directory1, name) -> name.startsWith(prefix);
         String[] pathnames = directory.list(filter);

         //List through all element files and add to ArrayList
         if(pathnames != null){
             for(String path : pathnames){
                 objects.add(deserializeElement(directory.getPath() + "/" + path));
             }
             return objects;
         }else{
             return null;
         }
     }

    /**
     * Save a single element to storage
     * @param prefix "task" or "notif"
     * @param uniqueID hash code
     */
    void serializeElement(T element, String prefix, String username, int uniqueID){
        File file = new File(SAVEPATH + "/" + username + "/" + prefix + uniqueID + FILETYPE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(element);

            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Get a single object given by ID and owner
     * @param prefix "task" or "notif"
     * @param uniqueID hash code
     * @return {@code null} if user or element could not be found
     */
    Object deserializeElement(String username, String prefix, int uniqueID){
        String filepath = SAVEPATH + "/" + username + "/" + prefix + uniqueID + FILETYPE;

        return deserializeElement(filepath);
    }

    /**
     * Get a single object given by filename
     * @return {@code null} if task could not be found
     */
    private Object deserializeElement(String filepath){
        File file = new File(filepath);
        Object obj = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            obj = ois.readObject();

            ois.close();
            fis.close();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return obj;
    }
}
