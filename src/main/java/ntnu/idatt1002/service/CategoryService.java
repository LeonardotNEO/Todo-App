package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.UserStateDAO;

public class CategoryService {

    /**
     * Get categories by current user
     * @return
     */
    public static String[] getCategoriesCurrentUser(){
        String[] categories = CategoryDAO.getCategoriesByUser(UserStateDAO.getUsername());
        return categories;
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
        if(categoryTitle.length() > 0 && categoryTitle.length() < 30){
            return true;
        } else {
            return false;
        }
    }
}
