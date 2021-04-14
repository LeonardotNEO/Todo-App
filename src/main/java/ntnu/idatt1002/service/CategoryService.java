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

    private static String[] premadeCategories = {"All tasks", "Trash bin", "Finished tasks"};

    /**
     * Get categories by current user
     * @return
     */
    public static String[] getCategoriesCurrentUser(){
        String[] categories = CategoryDAO.getCategoriesByUser(UserStateDAO.getUsername());
        return categories;
    }

    /**
     * Method for getting all categories without the premades one defined in premadeCategories objectvariable
     * @return
     */
    public static ArrayList<String> getCategoriesCurrentUserWithoutPremades(){
        ArrayList<String> categoriesWithoutPremades = new ArrayList<>();

        for (String s : getCategoriesCurrentUser()) {
            boolean result = Arrays.stream(premadeCategories).anyMatch(s::contains);

            if(!result){
                categoriesWithoutPremades.add(s);
            }
        }

        return categoriesWithoutPremades;
    }

    /**
     * Delete categories by current user
     * @param categoryName
     */
    public static void deleteCategoryCurrentUser(String categoryName){
        String username = UserStateDAO.getUsername();
        CategoryDAO.deleteCategory(username, categoryName);
        UserLogDAO.setCategoryRemoved(username, categoryName);
    }

    /**
     * Add new category to current user
     * @param categoryName
     */
    public static void addCategoryToCurrentUser(String categoryName){
        String username = UserStateDAO.getUsername();
        CategoryDAO.addCategory(username, categoryName);
        UserLogDAO.setCategoryAdded(username, categoryName);
    }

    /**
     * Validate syntax of categoryTitle. Must be a length of more than 0 and less than 30.
     * @param categoryTitle
     * @return
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
            if(!s.equals("Trash bin") && !s.equals("Finished tasks")){
                categoriesList.add(s);
            }
        }

        // add premades at the bottom
        for (String premadeCategory : premadeCategories) {
            categoriesList.add(premadeCategory);
        }

        return categoriesList;
    }

    public static String[] getPremadeCategories(){
        return premadeCategories;
    }
}
