package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;

import java.util.ArrayList;

public final class TaskDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILETYPE = ".ser";

    /**
     * Get all tasks stored in user folder
     * @return {@code null} if user could not be found
     */
    public static ArrayList<Task> getTasksByUser(String username){
        return null;
    }

    /**
     * Save an {@code ArrayList} of tasks to their owner folder
     * @param tasks all need to have the same owner
     */
    public static void saveTasksToUser(ArrayList<Task> tasks){
        //empty
    }

    /**
     * Save a single task to storage
     */
    public static void serializeTask(Task task){
        //empty
    }

    /**
     * Get a single task given by ID and owner
     * @param username which user that owns the task
     * @param taskID unique task ID
     * @return {@code null} if user or task could not be found
     */
    public static Task deserializeTask(String username, int taskID){
        return null;
    }
}
