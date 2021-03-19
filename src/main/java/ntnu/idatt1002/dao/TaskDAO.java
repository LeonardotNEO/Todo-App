package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;

import java.util.ArrayList;

/**
 * Acces task objects in storage
 */
public final class TaskDAO {
    private static final GenericDAO<Task> genericDAO = new GenericDAO<>();
    private static final String PREFIX = "task";

    /**
     * Get all tasks stored in user folder
     * @return {@code null} if user could not be found
     */
    public static ArrayList<Task> getTasksByUser(String username){
        ArrayList<Task> tasks = new ArrayList<>();
        for(Object obj : genericDAO.getElementsByUser(username, PREFIX)){
            tasks.add((Task) obj);
        }
        return tasks;
    }

    /**
     * Save an {@code ArrayList} of tasks to their owner folder
     */
    public static void saveTasksToUser(ArrayList<Task> tasks){
        for(Task task : tasks) {
            serializeTask(task);
        }
    }

    /**
     * Save a single task to storage
     */
    public static void serializeTask(Task task){
        genericDAO.serializeElement(task, PREFIX, task.getUserName(), task.hashCode());
    }

    /**
     * Get a single task given by ID and owner
     * @param username which user that owns the task
     * @param taskID tasks hashcode
     * @return {@code null} if user or task could not be found
     */
    public static Task deserializeTask(String username, int taskID){
        return (Task) genericDAO.deserializeElement(username, PREFIX, taskID);
    }

    /**
     * Delete all task for a user
     * @return {@code false} if one or more could not be deleted
     */
    public static boolean deleteTasksByUser(String username){
        return deleteTasks(getTasksByUser(username));
    }

    /**
     * Delete an array of tasks
     * @return {@code false} if one or more could not be deleted
     */
    public static boolean deleteTasks(ArrayList<Task> tasks){
        boolean success = true;
        for(Task task : tasks){
            if(!deleteTask(task)){
                success = false;
            }
        }
        return success;
    }

    /**
     * Delete a single task
     * @return {@code false} if task could not be deleted
     */
    public static boolean deleteTask(Task task){
        return genericDAO.deleteElement(task, PREFIX, task.getUserName(), task.hashCode());
    }
}
