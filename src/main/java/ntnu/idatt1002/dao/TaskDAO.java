package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;

import java.io.*;
import java.util.ArrayList;

/**
 * Access task objects in storage
 */
public final class TaskDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String PREFIX = "task";
    private static final String FILETYPE = ".ser";

    /**
     * Get all tasks stored in user folder
     * @return {@code null} if user could not be found
     */
    public static ArrayList<Task> getTasksByUser(String username){
        ArrayList<Task> tasks = new ArrayList<>();
        File directory = new File(categoriesPath(username));
        String[] categories = directory.list();

        if(categories != null){
            for(String category : categories){
                tasks.addAll(getTasksByCategory(username, category));
            }
        }

        return tasks;
    }

    /**
     * Get all tasks in a category
     * @return {@code null} if user or category could not be found
     */
    public static ArrayList<Task> getTasksByCategory(String username, String category){
        ArrayList<Task> tasks = new ArrayList<>();
        File directory = new File(categoryPath(username, category));
        String[] pathnames = directory.list();

        if(pathnames != null){
            for(String path : pathnames){
                tasks.add(deserializeTask(directory.getPath() + "/" + path));
            }
        }

        return tasks;
    }

    /**
     * Save an {@code ArrayList} of tasks to their respective folders
     */
    public static void saveTasks(ArrayList<Task> tasks){
        for(Task task : tasks) {
            serializeTask(task);
        }
    }

    /**
     * Save a single task to storage
     */
    public static void serializeTask(Task task){
        String username = task.getUserName();
        String category = task.getCategory();
        int taskID = task.hashCode();
        File file = new File(filePath(username, category, taskID));
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(task);

            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Get a single task given by owner, category and ID
     * @param username which user that owns the task
     * @param category which category the task belogns to
     * @param taskID tasks hashcode
     * @return {@code null} if task could not be found
     */
    public static Task deserializeTask(String username, String category, int taskID){
        return deserializeTask(filePath(username, category, taskID));
    }

    /**
     * Get a single task only by owner and ID, less effective than giving the category as well
     * @param username which user that owns the task
     * @param taskID tasks hashcode
     * @return {@code null} if task could not be found
     */
    public static Task deserializeTask(String username, int taskID){
        File directory = new File(categoriesPath(username));
        String[] categories = directory.list();

        //Scans all categories and all their tasks, looking for a match on the taskID
        if(categories != null){
            for(String category : categories){
                File catDir = new File(directory.getPath() + "/" + category);
                String[] pathnames = catDir.list();

                if(pathnames != null){
                    for(String path : pathnames){
                        if((PREFIX + taskID + FILETYPE).equals(path)){
                            return deserializeTask(directory.getPath() + "/" + category + "/" + path);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get a single task by complete filepath
     * @return {@code null} if task could not be found
     */
    public static Task deserializeTask(String filepath){
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

    /**
     * Delete all tasks for a user
     * @return {@code false} if one or more could not be deleted
     */
    public static boolean deleteTasksByUser(String username){
        return deleteTasks(getTasksByUser(username));
    }

    /**
     * Delete all tasks in a category
     * @return {@code false} if one or more could not be deleted
     */
    public static boolean deleteTasksByCategory(String username, String category){
        return deleteTasks(getTasksByCategory(username, category));
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
        String username = task.getUserName();
        String category = task.getCategory();
        int taskID = task.hashCode();
        return deleteTask(filePath(username, category, taskID));
    }

    /**
     * Delete a single task by filepath
     * @return {@code false} if task could not be deleted
     */
    public static boolean deleteTask(String filepath){
        File file = new File(filepath);
        return file.delete();
    }

    //PRIVATE STRING FUNCTIONS
    /**
     * Get categories directory
     */
    private static String categoriesPath(String username){
        return (SAVEPATH + "/" + username + "/Categories/");
    }

    /**
     * Get category directory
     */
    private static String categoryPath(String username, String category){
        return (categoriesPath(username) + category + "/");
    }

    /**
     * Get file path
     */
    private static String filePath(String username, String category, int taskID){
        return (categoryPath(username, category) + PREFIX + taskID + FILETYPE);
    }
}
