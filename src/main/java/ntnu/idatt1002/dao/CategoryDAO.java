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
    public static String[] getCategoriesByUser(String username){
        File directory = new File(categoriesPath(username));
        return directory.list();
    }

    /**
     * Add new category to user
     * @return {@code false} if folder could not be created
     */
    public static boolean addCategory(String username, String category){
        File directory = new File(categoriesPath(username) + category);
        return directory.mkdir();
    }

    /**
     * Delete all categories for a user
     * @return {@code false} if any tasks or some the categories could not be deleted
     */
    public static boolean deleteCategoriesByUser(String username){
        boolean success = true;
        String[] categories = getCategoriesByUser(username);

        for(String category : categories){
            if(!deleteCategory(username, category)){
                success = false;
            }
        }

        return success;
    }

    /**
     * Delete a category and all its tasks
     * @return {@code false} if any tasks or the category could not be deleted
     */
    public static boolean deleteCategory(String username, String category){
        File directory = new File(categoriesPath(username) + category);
        boolean success = TaskDAO.deleteTasksByCategory(username, category);

        if(!directory.delete()){ success = false; }

        return success;
    }

    /**
     * Check if given username and category inside it exists
     * @param username non case-sensitive username
     * @param category category in user storage
     * @return true or false
     */
    static boolean catExists(String username, String category){
        if(UserDAO.userExists(username)){
            File catDir = new File(categoriesPath(username) + category);
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
}
