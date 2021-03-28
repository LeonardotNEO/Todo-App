package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDAOTest {
    private final static User userA = new User("olanormann");
    private final static String categoryA = "Work";

    @BeforeAll
    public static void setup() {
        UserDAO.serializeUser(userA);
        boolean success = CategoryDAO.addCategory("olanormann", categoryA);
        if(!success){ System.out.println("Error occured"); }
    }

    @Test
    public void _getCategoriesByUser(){
        String[] categories = CategoryDAO.getCategoriesByUser("olanormann");

        assertTrue(Arrays.asList(categories).contains(categoryA));
    }

    @Nested
    public class file_not_existing{
        @Test
        public void username(){
            assertNull(CategoryDAO.getCategoriesByUser("josephjoestar"));
        }

        @Test
        public void category(){
            assertFalse(CategoryDAO.deleteCategory("olanormann","Home"));
        }
    }

    @AfterAll
    public static void cleanup(){
        UserDAO.deleteUser("olanormann");
    }
}
