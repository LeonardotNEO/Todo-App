package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.UserLogDAO;
import ntnu.idatt1002.dao.UserStateDAO;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class which provides some necessary features which utilises category-data
 */
public class CategoryService {

    private static ArrayList<String> premadeCategories = new ArrayList<>() {
        {
            add("All tasks");
            add("Trash bin");
            add("Finished tasks");
        }
    };

    /**
     * Get categories by current user
     * @return String[] of all the categories that the user has
     */
    public static String[] getCategoriesCurrentUser(){
        String[] categories = CategoryDAO.list(UserStateDAO.getUsername());
        return categories;
    }

    /**
     * get the categories in a project, by the current user
     * @param projectName name of the project
     * @return String[] of all the categories within the project
     */
    public static String[] getCategoriesByProjectCurrentUser(String projectName){
        return CategoryDAO.list(UserStateService.getCurrentUser().getUsername(), projectName);
    }

    /**
     * get the categories in a project, by the current user
     * @param projectName name of the project
     * @return ArrayList <String> of all the categories within the project
     */
    public static ArrayList<String> getCategoriesByProjectCurrentUserArraylist(String projectName){
        ArrayList<String> categories = new ArrayList<>();

        for(String category : getCategoriesByProjectCurrentUser(projectName)){
            categories.add(category);
        }
        return categories;
    }

    /**
     * Method for getting all categories without the pre-made one defined in pre-made Categories object variable
     * @return all categories excluding pre-mades
     */
    public static ArrayList<String> getCategoriesCurrentUserWithoutPremades(){
        ArrayList<String> categoriesWithoutPremades = new ArrayList<>();

        for (String s : getCategoriesCurrentUser()) {
            boolean result = premadeCategories.stream().anyMatch(s::contains);

            if(!result){
                categoriesWithoutPremades.add(s);
            }
        }

        return categoriesWithoutPremades;
    }

    /**
     * Delete categories by current user
     * @param categoryName the name of the category that is being deleted
     */
    public static void deleteCategoryCurrentUser(String categoryName){
        String username = UserStateDAO.getUsername();
        CategoryDAO.delete(username, categoryName);
        UserLogDAO.setCategoryRemoved(username, categoryName);
        UserStateService.getCurrentUser().setCurrentlySelectedCategory(null);
    }

    /**
     * Delete categories by current user inside project
     * @param categoryName name of category
     * @param projectName name of project
     */
    public static void deleteCategoryCurrentUser(String categoryName, String projectName){
        String username = UserStateService.getCurrentUser().getUsername();
        CategoryDAO.delete(username, projectName, categoryName);
        UserLogDAO.setCategoryRemoved(username, categoryName);
        UserStateService.getCurrentUser().setCurrentlySelectedProjectCategory(null);
    }

    /**
     * Add new category to current user
     * @param categoryName name of category
     */
    public static void addCategoryToCurrentUser(String categoryName){
        CategoryDAO.add(UserStateService.getCurrentUser().getUsername(), categoryName);

        UserLogDAO.setCategoryAdded(UserStateService.getCurrentUser().getUsername(), categoryName);
    }

    /**
     * Add new category to current user under project
     * @param projectName project name
     * @param categoryName the new category name
     */
    public static void addCategoryToCurrentUser(String projectName, String categoryName){
        CategoryDAO.add(UserStateService.getCurrentUser().getUsername(), projectName, categoryName);
    }

    /**
     * Validate syntax of categoryTitle. Must be a length of more than 0 and less than 24.
     * @param categoryTitle the name of the category
     * @return boolean according to if the Category title is less that 24 letters, and more than 0.
     */
    public static boolean validateCategoryTitleSyntax(String categoryTitle){
        if(categoryTitle.length() > 0 && categoryTitle.length() < 24){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that takes an Array of category-strings, turns it into an array and adds "Trash bin" and "Finished tasks" the the bottom
     * @return
     */
    public static ArrayList<String> getArrayListCategoriesOrganized(){
        ArrayList<String> categoriesList = new ArrayList<>();
        for (String s : getCategoriesCurrentUser()) {
            if(!premadeCategories.stream().anyMatch(s::equals)){
                categoriesList.add(s);
            }
        }

        // add premades at the bottom
        for (String premadeCategory : premadeCategories) {
            categoriesList.add(premadeCategory);
        }

        return categoriesList;
    }

    public static ArrayList<String> getPremadeCategories(){
        return premadeCategories;
    }
}
