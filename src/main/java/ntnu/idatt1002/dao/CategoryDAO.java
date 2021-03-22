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
        File directory = new File(SAVEPATH + "/" + username + "/Categories");
        return directory.list();
    }

    /**
     * Add new category to user
     * @return {@code false} if folder could not be created
     */
    public static boolean addCategory(String username, String category){
        File directory = new File(SAVEPATH + "/" + username + "/Categories/" + category);
        return directory.mkdir();
    }

    /**
     * Delete a category and all its tasks
     * @return {@code false} if any tasks or the category could not be deleted
     */
    public static boolean deleteCategory(String username, String category){
        boolean success = true;
        File directory = new File(SAVEPATH + "/" + username + "/Categories/" + category);
        String[] pathnames = directory.list();

        //Delete contents
        if(pathnames != null){
            for(String path : pathnames){
                if(!TaskDAO.deleteTask(directory.getPath() + path)){
                    success = false;
                }
            }
        }

        if(!directory.delete()){ success = false; }

        return success;
    }
}
