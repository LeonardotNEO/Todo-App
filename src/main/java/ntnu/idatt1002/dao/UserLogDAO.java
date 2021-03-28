package ntnu.idatt1002.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Static class to store actions in a user log file
 * Will only be used for showing statistics, will also be deleted upon user deletion
 */
public final class UserLogDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILENAME = "userlog.txt";

    public static void setUserRegistration(String username){
        writeEntry(username, "User created");
    }

    public static void setCategoryAdded(String username, String category){
        writeEntry(username, "Category created: " + category);
    }

    public static void setCategoryRemoved(String username, String category){
        writeEntry(username, "Category removed: " + category);
    }

    public static void setTaskAdded(String username, String title){
        writeEntry(username, "Task created: " + title);
    }

    public static void setTaskRemoved(String username, String title){
        writeEntry(username, "Task removed: " + title);
    }

    public static void setTaskDone(String username, String title){
        writeEntry(username, "Task marked as done: " + title);
    }

    /**
     * Write an entry to the users log file
     * @param username user to store log entry in
     * @param entry String containing datetime, entry type and entry message
     */
    private static void writeEntry(String username, String entry){
        //Get current datetime and format
        LocalDateTime datetime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formatDatetime = datetime.format(format);

        try {
            FileWriter fileWriter = new FileWriter(filePath(username));
            fileWriter.write(formatDatetime + " - " + entry + ".\n");
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
