package ntnu.idatt1002.dao;

import ntnu.idatt1002.Notification;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;

import java.io.File;
import java.util.ArrayList;

/**
 * The class {@code TaskDAO} provides static methods for handling {@link Task} objects.
 * In the storage system the files get saved in a hierarchy of folder based on the objects variables.
 * Normal tasks get saved under 'Categories/{@code category}/' while project tasks get saved under
 * 'Projects/{@code project}/{@code category}/'.
 */
public final class TaskDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String PREFIX = "task";
    private static final String FILETYPE = ".ser";

    /**
     * Get all {@link Task} objects stored under a given {@link User}. This includes both regular tasks
     * and project tasks.
     * @param username the {@code username} variable in a {@link User}.
     * @return an {@link ArrayList}, or {@code null} if the user doesn't exist.
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
     * Get all {@link Task} objects within a category under a {@link User}.
     * @param username the {@code username} variable in a {@link User}.
     * @param category the name of the category.
     * @return an {@link ArrayList}, or {@code null} if either the user or the category doesn't exist.
     */
    public static ArrayList<Task> list(String username, String category){
        File categoryDiv = new File(categoryDiv(username, category));
        return list(categoryDiv);
    }

    /**
     * Get all {@link Task} objects within a project category for a {@link User}.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project.
     * @param category the name of the category.
     * @return an {@link ArrayList}, or {@code null} if either the user, the project, or the category
     * doesn't exist.
     */
    public static ArrayList<Task> list(String username, String project, String category){
        File projectDiv = new File(projectCategoryDiv(username, project, category));
        return list(projectDiv);
    }

    /**
     * Get all {@link Task} objects within a given directory
     * @param dir a {@link File} object referencing a directory in the storage system. Needs to containt
     *            {@link Task} files, so either a normal category directory or a project category directory.
     * @return an {@link ArrayList}, or {@code null} if the directory doesn't exist.
     */
    private static ArrayList<Task> list(File dir){
        String[] filepaths = dir.list();
        ArrayList<Task> tasks = new ArrayList<>();

        if(filepaths != null){
            for(String file : filepaths){
                tasks.add(deserialize(dir.getPath() + "/" + file));
            }
        }else{
            return null;
        }
        return tasks;
    }

    /**
     * Serialize the given {@link ArrayList} of {@link Task} objects. The list can contain
     * objects belonging to different users, projects and categories. They will be stored in
     * their respective folders.
     * @param tasks an {@link ArrayList} of {@link Task} objects.
     */
    public static void serialize(ArrayList<Task> tasks){
        for(Task task : tasks){
            serialize(task);
        }
    }

    /**
     * Serialize the given {@link Task} object to storage. The location is based on the tasks
     * {@code username}, {@code project} and {@code category} variables.
     * @param task a {@link Task} object.
     */
    public static void serialize(Task task){
        GenericDAO.serialize(task, filepath(task));
    }

    /**
     * Returns a {@link Task} object from storage matching the given arguments. Since neither category
     * or project is provided, this method will search through the entire {@link User} folder. To
     * increase effectiveness, provide more arguments.
     * @param username the {@code username} variable in a {@link User}.
     * @param id the {@code id} variable for the {@link Task}.
     * @return a {@link Task} object, or {@code null} if the file could not be found. Either the user
     * doesn't exist or a task with the given {@code id} exists.
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
     * Returns a {@link Task} object from storage matching the given arguments. This method only
     * retrieves from the 'Categories' folder. For project tasks the project name needs to be passed
     * as an argument as well, or remove the {@code category} and search the entire user folder.
     * @param username the {@code username} variable in a {@link User}.
     * @param category the name of the category.
     * @param id the {@code id} variable for the {@link Task}.
     * @return a {@link Task} object, or {@code null} if the file could not be found. Either the user,
     * the category, or a task with the given {@code id} doesn't exist.
     */
    public static Task deserialize(String username, String category, long id){
        return deserialize(categoryDiv(username, category) + filename(id));
    }

    /**
     * Returns a {@link Task} object from storage matching the given arguments.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project.
     * @param category the name of the category.
     * @param id the {@code id} variable for the {@link Task}.
     * @return a {@link Task} object, or {@code null} if the file could not be found. Either the user,
     * the project, the category, or a task with the given {@code id} doesn't exist.
     */
    public static Task deserialize(String username, String project, String category, long id){
        return deserialize(projectCategoryDiv(username, project, category) + filename(id));
    }

    /**
     * Returns a {@link Task} object from storage given its filepath.
     * @param filepath the {@link Notification}'s filepath
     * @return a {@link Task} object, or {@code null} if the file doesn't exist.
     */
    private static Task deserialize(String filepath){
        return (Task) GenericDAO.deserialize(filepath);
    }

    /**
     * Delete all {@link Task} files under a given {@link User} folder. This includes both
     * regular and project tasks.
     * @param username the {@code username} variable in a {@link User}.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete,
     * normally if the user doesn't exist.
     */
    public static boolean deleteByUser(String username){
        return deleteByList(list(username));
    }

    /**
     * Delete all {@link Task} files under a given category for a {@link User}. To delete all tasks
     * within a project category the project needs to be passed as an argument as well.
     * @param username the {@code username} variable in a {@link User}.
     * @param category the name of the category.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete,
     * either the user or the category doesn't exist.
     */
    public static boolean deleteByCategory(String username, String category){
        return deleteByList(list(username, category));
    }

    /**
     * Delete all {@link Task} files under a given project category for a {@link User}.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project.
     * @param category the name of the category.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete,
     * either the user, the project, or the category doesn't exist.
     */
    public static boolean deleteByProjectCategory(String username, String project, String category){
        return deleteByList(list(username, project, category));
    }

    /**
     * Delete all {@link Task} objects in the given {@link ArrayList} from storage.
     * @param tasks an {@link ArrayList} of {@link Task} objects.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete.
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

    /**
     * Delete a {@link Task} file from storage.
     * @param task the object to be deleted.
     * @return {@code True} if succesfull. {@code False} if the file isn't stored.
     */
    public static boolean delete(Task task){
        return delete(filepath(task));
    }

    /**
     * Delete a {@link Task} file matching the given arguments.
     * @param username the {@code username} variable in a {@link User}.
     * @param category the name of the category.
     * @param id the {@code id} variable for the {@link Task}.
     * @return {@code True} if succesfull. {@code False} if the file couldn't be deleted,
     * either the user, the category, or a task with the given {@code id} doesn't exist.
     */
    public static boolean delete(String username, String category, long id){
        return delete(categoryDiv(username, category) + filename(id));
    }

    /**
     * Delete a {@link Task} file matching the given arguments.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project.
     * @param category the name of the category.
     * @param id the {@code id} variable for the {@link Task}.
     * @return {@code True} if succesfull. {@code False} if the file couldn't be deleted,
     * either the user, the project, the category, or a task with the given {@code id} doesn't exist.
     */
    public static boolean delete(String username, String project, String category, long id){
        return delete(projectCategoryDiv(username, project, category) + filename(id));
    }

    /**
     * Delete a {@link Task} file with the given filepath.
     * @param filepath the {@link Task}'s filepath.
     * @return  {@code True} if succesfull. {@code False} if the file couldn't be deleted,
     * normally because the filepath doesn't exist.
     */
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
