package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;

import java.io.File;
import java.util.ArrayList;

/**
 * Access task objects in storage
 */
public final class TaskDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String PREFIX = "task";
    private static final String FILETYPE = ".ser";

    /**
     * Get all tasks under a user
     * @return {@code ArrayList} of Tasks, {@code null} if none could be found
     */
    public static ArrayList<Task> list(String username){
        if(!UserDAO.exists(username)){ return null; }

        ArrayList<Task> allTasks = new ArrayList<>();
        String[] categories = CategoryDAO.list(username);

        //Add tasks in regular categories
        for(String category : categories){
            allTasks.addAll(list(username, category));
        }

        //Add tasks in project categories
        String[] projects = ProjectDAO.list(username);
        for(String project : projects){
            String[] projCats = CategoryDAO.list(username, project);
            for(String projCat : projCats){
                allTasks.addAll(list(username, project, projCat));
            }
        }

        return allTasks;
    }

    /**
     * Get all tasks under a category
     * @return {@code ArrayList} of Tasks, {@code null} if none could be found
     */
    public static ArrayList<Task> list(String username, String category){
        File categoryDiv = new File(categoryDiv(username, category));
        return list(categoryDiv);
    }

    /**
     * Get all tasks under a project category
     * @return {@code ArrayList} of Tasks, {@code null} if none could be found
     */
    public static ArrayList<Task> list(String username, String project, String category){
        File projectDiv = new File(projectCategoryDiv(username, project, category));
        return list(projectDiv);
    }

    /**
     * Get all tasks in a given directory
     * @param div File object of directory
     * @return {@code null} if empty
     */
    private static ArrayList<Task> list(File div){
        String[] filepaths = div.list();
        ArrayList<Task> tasks = new ArrayList<>();

        if(filepaths != null){
            for(String file : filepaths){
                tasks.add(deserialize(div.getPath() + "/" + file));
            }
        }else{
            return null;
        }
        return tasks;
    }

    /**
     * Save a list of tasks to storage
     */
    public static void serialize(ArrayList<Task> tasks){
        for(Task task : tasks){
            serialize(task);
        }
    }

    /**
     * Save a single task to storage
     */
    public static void serialize(Task task){
        GenericDAO.serialize(task, filepath(task));
    }

    /**
     * Search entire user for matching task id and return the Task object
     * @return {@code null} if task could not be found
     */
    public static Task deserialize(String username, long id){
        ArrayList<Task> tasks = list(username);
        if(tasks != null){
            for(Task task : tasks){
                if(task.getId() == id){ return task;}
            }
        }

        return null;
    }

    /**
     * Get task under user folder
     * @return {@code null} if task could not be found
     */
    public static Task deserialize(String username, String category, long id){
        return deserialize(categoryDiv(username, category) + filename(id));
    }

    /**
     * Get task under project folder
     * @return {@code null} if task could not be found
     */
    public static Task deserialize(String username, String project, String category, long id){
        return deserialize(projectCategoryDiv(username, project, category) + filename(id));
    }

    /**
     * Get task from storage from a filepath
     * @param filepath filepath starting with "src/"
     * @return {@code null} if task could not be found
     */
    private static Task deserialize(String filepath){
        return (Task) GenericDAO.deserialize(filepath);
    }

    /**
     * Delete all tasks in a user folder
     * @return {@code false} if a task could not be deleted
     */
    public static boolean deleteByUser(String username){
        return deleteByList(list(username));
    }

    /**
     * Delete all tasks in a given category
     * @return {@code false} if a task could not be deleted
     */
    public static boolean deleteByCategory(String username, String category){
        return deleteByList(list(username, category));
    }

    /**
     * Delete all tasks in a given project category
     * @return {@code false} if a task could not be deleted
     */
    public static boolean deleteByProjectCategory(String username, String project, String category){
        return deleteByList(list(username, project, category));
    }

    /**
     * Delete all tasks in a given list
     * @return {@code false} if a task could not be deleted
     */
    private static boolean deleteByList(ArrayList<Task> tasks){
        boolean result = true;

        if(tasks != null){
            for(Task task : tasks){
                if(!delete(task)){ result = false; }
            }
        }else{ return false; }

        return result;
    }

    public static boolean delete(Task task){
        return delete(filepath(task));
    }

    public static boolean delete(String username, String category, long id){
        return delete(categoryDiv(username, category) + filename(id));
    }

    public static boolean delete(String username, String project, String category, long id){
        return delete(projectCategoryDiv(username, project, category) + filename(id));
    }

    private static boolean delete(String filepath){
        File file = new File(filepath);
        return file.delete();
    }

    //Get paths
    private static String userDir(String username){
        return (SAVEPATH + "/" + username + "/");
    }

    private static String categoryDiv(String username, String category){
        return (userDir(username) + "/Categories/" + category + "/");
    }

    private static String projectCategoryDiv(String username, String project, String category){
        return (userDir(username) + "/Projects/" + project + "/" + category + "/");
    }

    private static String filename(long taskID){
        return (PREFIX + taskID + FILETYPE);
    }

    private static String filepath(Task task){
        String username = task.getUserName();
        String category = task.getCategory();
        String project = task.getProject();
        long id = task.getId();

        if(project == null){
            return categoryDiv(username, category) + filename(id);
        }else{
            return projectCategoryDiv(username, project, category) + filename(id);
        }
    }
}
