package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;

import java.io.*;
import java.util.ArrayList;

/**
 * Acces task objects in storage
 */
public final class TaskDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILETYPE = ".ser";

    /**
     * Get all tasks stored in user folder
     * @return {@code null} if user could not be found
     */
    public static ArrayList<Task> getTasksByUser(String username){
        //Get all files in a user folder
        ArrayList<Task> tasks = new ArrayList<>();
        File directory = new File(SAVEPATH + "/" + username);

        //Only get task files
        FilenameFilter filter = (directory1, name) -> name.startsWith("task");
        String[] pathnames = directory.list(filter);

        //List through all task files and add to ArrayList
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
     */
    public static void saveTasksToUser(ArrayList<Task> tasks){
        for(Task task : tasks){
            serializeTask(task);
        }
    }

    /**
     * Save a single task to storage
     */
    public static void serializeTask(Task task){
        String username = task.getUserName();
        int taskID = task.hashCode();
        File file = new File(SAVEPATH + "/" + username + "/task" + taskID + FILETYPE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(task);

            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Get a single task given by ID and owner
     * @param username which user that owns the task
     * @param taskID tasks hashcode
     * @return {@code null} if user or task could not be found
     */
    public static Task deserializeTask(String username, int taskID){
        String filepath = SAVEPATH + "/" + username + "/task" + taskID + FILETYPE;
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
