package ntnu.idatt1002;

/**
 * Class containing user log with functions for presenting statistics.
 * This is only for getting access to values stored, and should be created upon
 * the user opening the Statistics menu.
 * To store new values use UserLogDAO's functions to add new elements to it's log.
 */
public class UserLog {
    private String username;
    private String userCreation;
    private String[] categoryAdded;
    private String[] categoryRemoved;
    private String[] taskAdded;
    private String[] taskRemoved;
    private String[] taskDone;

    //GENERIC GET-FUNCTIONS
    public String getUsername() {
        return username;
    }
    public String getUserCreation() {
        return userCreation;
    }
    public String[] getCategoryAdded() {
        return categoryAdded;
    }
    public String[] getCategoryRemoved() {
        return categoryRemoved;
    }
    public String[] getTaskAdded() {
        return taskAdded;
    }
    public String[] getTaskRemoved() {
        return taskRemoved;
    }
    public String[] getTaskDone() {
        return taskDone;
    }

    /**
     * Get full user log in chronological order
     * @return a {@code String[]} where each element is a log entry
     */
    public String[] getLog(){
        return null;
    }
}
