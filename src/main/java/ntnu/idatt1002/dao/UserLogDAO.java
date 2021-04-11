package ntnu.idatt1002.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Static class to store actions in a user log file
 * Will only be used for showing statistics, will also be deleted upon user deletion
 */
public final class UserLogDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILENAME = "userlog.txt";

    //String constants
    private static final String strUserCreated = "User created";
    private static final String strCatAdded = "Category created: ";
    private static final String strCatRemoved = "Category removed: ";
    private static final String strTaskAdded = "Task created: ";
    private static final String strTaskRemoved = "Task removed: ";
    private static final String strTaskMoved = "Task moved to: ";

    //ADD ENTRY
    public static void setUserRegistration(String username){
        writeEntry(username, strUserCreated);
    }

    public static void setCategoryAdded(String username, String category){
        writeEntry(username, strCatAdded + category);
    }

    public static void setCategoryRemoved(String username, String category){
        writeEntry(username, strCatRemoved + category);
    }

    public static void setTaskAdded(String username, String title){
        writeEntry(username, strTaskAdded + title);
    }

    public static void setTaskRemoved(String username, String title){
        writeEntry(username, strTaskRemoved + title);
    }

    public static void setTaskMoved(String username, String category){
        writeEntry(username, strTaskMoved + category);
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

                bufferedReader.close();
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
            String[] categoryAdded = filterArray(fullLog, strCatAdded);
            String[] categoryRemoved = filterArray(fullLog, strCatRemoved);
            String[] taskAdded = filterArray(fullLog, strTaskAdded);
            String[] taskRemoved = filterArray(fullLog, strTaskRemoved);
            String[] taskMoved = filterArray(fullLog, strTaskMoved);

            return new UserLog(username, userCreation, categoryAdded, categoryRemoved, taskAdded,
                    taskRemoved, taskMoved);
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
                FileWriter fileWriter = new FileWriter(filePath(username), true);
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
