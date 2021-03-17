package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public final class TaskDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILETYPE = ".ser";

    /**
     * Get all tasks stored in user folder
     * @return {@code null} if user could not be found
     */
    public static ArrayList<Task> getTasksByUser(String username){
        ArrayList<Task> tasks = new ArrayList<>();
        File directory = new File(SAVEPATH + "/" + username);
        String[] pathnames = directory.list();
        if(pathnames != null){
            for(String path : pathnames){
                tasks.add(deserializeTask(directory.getPath() + "/" + path));
            }
            return tasks;
        }else{
            return null;
        }
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
        String filepath = SAVEPATH + "/" + username + "/" + "task" + taskID + FILETYPE;
        return deserializeTask(filepath);
    }

    /**
     * Get a single task given by filename
     * @return {@code null} if task could not be found
     */
    private static Task deserializeTask(String filepath){
        File file = new File(filepath);
        Task task = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            task = (Task) ois.readObject();

            ois.close();
            fis.close();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return task;
    }
}
