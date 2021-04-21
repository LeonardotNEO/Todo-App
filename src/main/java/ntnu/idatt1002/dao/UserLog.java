package ntnu.idatt1002.dao;

/**
 * Class containing user log with functions for presenting statistics.
 * This is only for getting access to values stored, and should be created upon
 * the user opening the Statistics menu.
 * To store new values use UserLogDAO's functions to add new elements to it's log.
 */
public class UserLog {
    private final String username;
    private final String userCreation;
    private final String[] categoryAdded;
    private final String[] categoryRemoved;
    private final String[] taskAdded;
    private final String[] taskRemoved;
    private final String[] taskDone;

    /**
     * Create a new instance of UserLog. Only available for DAO-classes.
     */
    UserLog(String username, String userCreation, String[] categoryAdded, String[] categoryRemoved,
            String[] taskAdded, String[] taskRemoved, String[] taskDone){
        this.username = username;
        this.userCreation = userCreation;
        this.categoryAdded = categoryAdded;
        this.categoryRemoved = categoryRemoved;
        this.taskAdded = taskAdded;
        this.taskRemoved = taskRemoved;
        this.taskDone = taskDone;
    }

    //GET
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
}
