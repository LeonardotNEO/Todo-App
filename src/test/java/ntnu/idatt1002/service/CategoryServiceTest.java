package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {
    @BeforeAll
    public static void setup() {
        User user = new User("Test");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername("Test");
    }

    @AfterAll
    public static void clean() {
        UserService.deleteUser();
    }

    @Test
    public void newAndDeleteCategoryCurrentUserTest() {
        // New Category
        CategoryService.addCategoryToCurrentUser("Category");
        assertEquals("Category", CategoryService.getCategoriesCurrentUser()[0]);

        // Delete the category
        CategoryService.deleteCategoryCurrentUser("Category");
        assertTrue(CategoryService.getCategoriesCurrentUser().length == 0);
    }

    @Test
    public void getCategoriesCurrentUserTest() {
        // No categories
        assertEquals(0, CategoryService.getCategoriesCurrentUser().length);

        // With category
        CategoryService.addCategoryToCurrentUser("Category");
        assertEquals(1, CategoryService.getCategoriesCurrentUser().length);

        // Delete the new category
        CategoryService.deleteCategoryCurrentUser("Category");
    }

    @Test
    public void validateCategoryTitleSyntaxTest() {
        // Invalid
        assertFalse(CategoryService.validateCategoryTitleSyntax(""));
        assertFalse(CategoryService.validateCategoryTitleSyntax("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));

        // Valid
        assertTrue(CategoryService.validateCategoryTitleSyntax("a"));
    }

    @Test
    public void getCategoriesCurrentUserWithoutPremadesTest() {
        assertEquals(0, CategoryService.getCategoriesCurrentUserWithoutPremades().size());
    }

    @Test
    public void getArrayListCategoriesOrganizedTest() {
        assertEquals(3, CategoryService.getArrayListCategoriesOrganized().size());
    }




}
