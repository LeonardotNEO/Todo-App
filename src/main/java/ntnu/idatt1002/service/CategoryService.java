package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.UserStateDAO;

import java.util.ArrayList;

public class CategoryService {

    /**
     * Get categories by current user
     * @return
     */
    public static String[] getCategoriesCurrentUser(){
        String[] categories = CategoryDAO.getCategoriesByUser(UserStateDAO.getUserState());
        return categories;
    }

    /**
     * Delete categories by current user
     * @param categoryName
     */
    public static void deleteCategoryCurrentUser(String categoryName){
        CategoryDAO.deleteCategory(UserStateDAO.getUserState(), categoryName);
    }

    /**
     * Add new category to current user
     * @param categoryName
     */
    public static void addCategoryToCurrentUser(String categoryName){
        CategoryDAO.addCategory(UserStateDAO.getUserState(), categoryName);
    }
}
