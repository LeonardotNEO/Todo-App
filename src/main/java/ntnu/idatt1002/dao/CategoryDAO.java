package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;

import java.io.File;

/**
 * The class {@code CategoryDAO} provides static methods for handling categories for a {@link User}.
 * In the storage system categories are folders either in the 'Categories' directory, or inside
 * a project directory created by {@link ProjectDAO}. Under each category folder all
 * {@link Task} objects with the corresponding {@code category} and {@code project} variables
 * are stored.
 */
public final class CategoryDAO {
    private static final String SAVEPATH = "src/main/resources/saves";

    /**
     * Returns a {@link String}[] of all normal categories under a {@link User} folder.
     * Categories inside projects are not included in this method.
     * @param username the {@code username} variable in a {@link User}.
     * @return an array of categories. Returns {@code null} if folder is empty.
     */
    public static String[] list(String username){
        File directory = new File(categoriesPath(username));
        return directory.list();
    }

    /**
     * Returns a {@link String}[] of all categories under a project folder.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the project folder under a {@link User}.
     * @return an array of categories. Returns {@code null} if folder is empty.
     */
    public static String[] list(String username, String project){
        File directory = new File(projectPath(username, project));
        return directory.list();
    }

    /**
     * Add a new category to a {@link User}. This folder will be created under the
     * 'Categories' folder.
     * @param username the {@code username} variable in a {@link User}.
     * @param category the name of the new category.
     * @return {@code True} if succesfull. {@code False} if directory could not be created.
     * Either the user doesn't exist or a folder with the given name already exists.
     */
    public static boolean add(String username, String category){
        File directory = new File(categoriesPath(username) + category);
        return directory.mkdir();
    }

    /**
     * Add a new category to a project. This folder will be created under the
     * project folder which again is stored under 'Projects'.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project.
     * @param category the name of the new category.
     * @return {@code True} if succesfull. {@code False} if directory could not be created.
     * Either the user doesn't exist, the project doesn't exist, or a folder with the given
     * name already exists.
     */
    public static boolean add(String username, String project, String category){
        File directory = new File(projectPath(username, project) + category);
        return directory.mkdir();
    }

    /**
     * Delete all regular categories inside a {@link User}. This will empty the 'Categories' folder
     * and all {@link Task} files within. Categories inside projects will not be deleted.
     * @param username the {@code username} variable in a {@link User}.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete,
     * normally if the user doesn't exist.
     */
    public static boolean deleteByUser(String username){
        if(!UserDAO.exists(username)){ return false; }

        boolean success = true;
        String[] categories = list(username);

        for(String category : categories){
            if(!delete(username, category)){
                success = false;
            }
        }

        return success;
    }

    /**
     * Delete all cateogies in a project folder. This will also delete all {@link Task} files within.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete.
     * Either the user or the project doesn't exist.
     */
    public static boolean deleteByProject(String username, String project){
        if(!ProjectDAO.exists(username, project)){ return false; }

        boolean success = true;
        String[] categories = list(username, project);

        for(String category : categories){
            if(!delete(username, project, category)){
                success = false;
            }
        }

        return success;
    }

    /**
     * Delete a single category in a {@link User} folder with all {@link Task} files within.
     * @param username the {@code username} variable in a {@link User}.
     * @param category the name of the category to delete.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete.
     * Either the user or the category doesn't exist.
     */
    public static boolean delete(String username, String category){
        File directory = new File(categoriesPath(username) + category);
        boolean success = TaskDAO.deleteByCategory(username, category);

        if(!directory.delete()){ success = false; }

        return success;
    }

    /**
     * Delete a single category in a project folder with all {@link Task} files within.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project.
     * @param category the name of the category to delete.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete.
     * Either the user, the project, or the category doesn't exist.
     */
    public static boolean delete(String username, String project, String category){
        File directory = new File(projectPath(username, project) + category);
        boolean success = TaskDAO.deleteByProjectCategory(username, project, category);

        return directory.delete();
    }

    /**
     * Check if the category exists.
     * @param username the {@code username} variable in a {@link User}.
     * @param category the name of the category.
     * @return {@code True} or {@code False}.
     */
    static boolean exists(String username, String category){
        if(UserDAO.exists(username)){
            File catDir = new File(categoriesPath(username) + category);
            return catDir.exists();
        }else{
            return false;
        }
    }

    /**
     * Check if the category exists.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project.
     * @param category the name of the category.
     * @return {@code True} or {@code False}.
     */
    static boolean exists(String username, String project, String category){
        if(UserDAO.exists(username)){
            File catDir = new File(projectPath(username, project) + category);
            return catDir.exists();
        }else{
            return false;
        }
    }

    //Get paths
    private static String categoriesPath(String username){
        return (SAVEPATH + "/" + username + "/Categories/");
    }

    private static String projectPath(String username, String project){
        return (SAVEPATH + "/" + username + "/Projects/" + project + "/");
    }
}
