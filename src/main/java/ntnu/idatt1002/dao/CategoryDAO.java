package ntnu.idatt1002.dao;

import java.io.File;

/**
 * Access category folders for users in storage
 */
public final class CategoryDAO {
    private static final String SAVEPATH = "src/main/resources/saves";

    /**
     * Get all categories from a user
     * @return {@code String[]} of all categories. {@code null} if empty
     */
    public static String[] list(String username){
        File directory = new File(categoriesPath(username));
        return directory.list();
    }

    /**
     * Get all categories in a users project
     * @return {@code String[]} of all categories. {@code null} if empty
     */
    public static String[] list(String username, String project){
        File directory = new File(projectPath(username, project));
        return directory.list();
    }

    /**
     * Add new category to user
     * @return {@code false} if folder could not be created
     */
    public static boolean add(String username, String category){
        File directory = new File(categoriesPath(username) + category);
        return directory.mkdir();
    }

    /**
     * Add new category to a users project
     * @return {@code false} if folder could not be created
     */
    public static boolean add(String username, String project, String category){
        File directory = new File(projectPath(username, project) + category);
        return directory.mkdir();
    }

    /**
     * Delete all categories for a user
     * @return {@code false} if some the categories could not be deleted
     */
    public static boolean deleteByUser(String username){
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
     * Delete all categories for a users project
     * @return {@code false} if some the categories could not be deleted
     */
    public static boolean deleteByProject(String username, String project){
        boolean success = true;
        String[] categories = list(username, project);

        for(String category : categories){
            if(!delete(username, category)){
                success = false;
            }
        }

        return success;
    }

    /**
     * Delete a category and all its tasks
     * @return {@code false} if the category could not be deleted
     */
    public static boolean delete(String username, String category){
        File directory = new File(categoriesPath(username) + category);
        boolean success = TaskDAO.deleteTasksByCategory(username, category);

        if(!directory.delete()){ success = false; }

        return success;
    }

    /**
     * Delete a project category and all its tasks
     * @return {@code false} if the category could not be deleted
     */
    public static boolean delete(String username, String project, String category){
        File directory = new File(projectPath(username, project) + category);
        boolean success = TaskDAO.deleteTasksByCategory(username, category);

        return directory.delete();
    }

    /**
     * Check if given category exists
     * @return true or false
     */
    static boolean exists(String username, String category){
        if(UserDAO.userExists(username)){
            File catDir = new File(categoriesPath(username) + category);
            return catDir.exists();
        }else{
            return false;
        }
    }

    /**
     * Check if given project category exists
     * @return true or false
     */
    static boolean exists(String username, String project, String category){
        if(UserDAO.userExists(username)){
            File catDir = new File(projectPath(username, project) + category);
            return catDir.exists();
        }else{
            return false;
        }
    }

    /**
     * Get categories directory
     */
    private static String categoriesPath(String username){
        return (SAVEPATH + "/" + username + "/Categories/");
    }

    /**
     * Get category in project directory
     */
    private static String projectPath(String username, String project){
        return (SAVEPATH + "/" + username + "/Projects/" + project + "/");
    }
}
