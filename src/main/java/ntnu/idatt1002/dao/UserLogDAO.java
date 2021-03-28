package ntnu.idatt1002.dao;

import ntnu.idatt1002.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Static class to store actions in a user log file
 * Will only be used for showing statistics, will also be deleted upon user deletion
 */
public final class UserLogDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILENAME = "userlog.txt";

    //ADD ENTRY
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
     * Get full user log
     * @param username the user to get the log from
     * @return a {@code String[]} of each log entry, empty array if user could not be found
     */
    public static String[] getFullLog(String username){
        ArrayList<String> lines = new ArrayList<>();

        if(UserDAO.userExists(username)) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath(username)));
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return lines.toArray(new String[0]);
    }

    /**
     * Get a UserLog object containing the full user log split into entry types
     * @param username the user to get the log file from
     * @return a {@code UserLog} object, or {@code null} if user does not exist
     */
    public static UserLog getUserLog(String username){
        if(UserDAO.userExists(username)) {
            String[] fullLog = getFullLog(username);
            String userCreation = fullLog[0];
            String[] categoryAdded = filterArray(fullLog, "Category created");
            String[] categoryRemoved = filterArray(fullLog, "Category removed");
            String[] taskAdded = filterArray(fullLog, "Task created");
            String[] taskRemoved = filterArray(fullLog, "Task removed");
            String[] taskDone = filterArray(fullLog, "Task marked as done");

            return new UserLog(username, userCreation, categoryAdded, categoryRemoved, taskAdded,
                    taskRemoved, taskDone);
        }else{ return null; }
    }

    /**
     * Given a full user log and a string to filter each entry, returns entries that match
     * @param fullLog a String[] from calling getFullLog()
     * @param filter keyword to filter the entries
     * @return a String[] containing all entries with matching filter
     */
    private static String[] filterArray(String[] fullLog, String filter){
        return Arrays.stream(fullLog).filter(str -> str.contains(filter)).toArray(String[]::new);
    }

    /**
     * Write an entry to the users log file
     * @param username user to store log entry in
     * @param entry String containing datetime, entry type and entry message
     */
    private static void writeEntry(String username, String entry){
        if(UserDAO.userExists(username)) {
            //Get current datetime and format
            LocalDateTime datetime = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formatDatetime = datetime.format(format);

            //Write to file
            try {
                FileWriter fileWriter = new FileWriter(filePath(username));
                fileWriter.write(formatDatetime + " - " + entry + ".\n");
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
