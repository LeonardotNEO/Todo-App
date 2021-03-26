package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.UserStateDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CategoryService {

    private static String[] premadeCategories = {"Trash bin", "Finished tasks"};

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
        CategoryDAO.deleteCategory(UserStateDAO.getUsername(), categoryName);
    }

    /**
     * Add new category to current user
     * @param categoryName
     */
    public static void addCategoryToCurrentUser(String categoryName){
        CategoryDAO.addCategory(UserStateDAO.getUsername(), categoryName);
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
}
