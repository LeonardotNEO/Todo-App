package ntnu.idatt1002.dao;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Static class to store actions in a user log file
 * Will only be used for showing statistics, will also be deleted upon user deletion
 */
public final class UserLogDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILENAME = "userlog.txt";

    public static void setUserRegistration(String username){

    }

    /**
     * Write an entry to the users log file
     * @param username user to store log entry in
     * @param entry String containing datetime, entry type and entry message
     */
    private static void writeEntry(String username, String entry){
        try {
            FileWriter fileWriter = new FileWriter(filePath(username));
            fileWriter.write(entry);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get complete filepath to the user log
     * @param username the user to store log in
     * @return complete filepath ready to be used in File object
     */
    private static String filePath(String username){
        return (SAVEPATH + "/" + username + "/" + FILENAME);
    }
}
