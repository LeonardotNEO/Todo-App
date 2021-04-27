package ntnu.idatt1002.dao;

import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDAOTest {
    @BeforeAll
    public static void testData(){
        UserDAO.serialize(new User("olanormann"));
        CategoryDAO.add("olanormann", "Home");
        CategoryDAO.add("olanormann", "Work");
        ProjectDAO.add("olanormann", "Build house");
        CategoryDAO.add("olanormann", "Build house", "Foundation");
        CategoryDAO.add("olanormann", "Build house", "Carpentry");
    }

    @Test
    public void listElementInCategoryDAOTest(){
        String[] regular = CategoryDAO.list("olanormann");
        String[] project = CategoryDAO.list("olanormann", "Build house");

        assertTrue(regular.length == 2 && Arrays.asList(regular).contains("Home"));
        assertTrue(project.length == 2 && Arrays.asList(project).contains("Foundation"));
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void listWithFalseArgumentsTest(){
            assertNull(CategoryDAO.list("joseph"));
            assertNull(CategoryDAO.list("joseph", "Build house"));
            assertNull(CategoryDAO.list("olanormann", "Start business"));
        }

        @Test
        public void addWithFalseArgumentsTest(){
            assertFalse(CategoryDAO.add("joseph", "Home"));
            assertFalse(CategoryDAO.add("joseph", "Build house", "Foundation"));
            assertFalse(CategoryDAO.add("olanormann", "Start business", "Take loan"));
        }

        @Test
        public void deleteUserWithFalseArgumentTest(){
            assertFalse(CategoryDAO.deleteByUser("joseph"));
        }

        @Test
        public void deleteProjectWithFalseArgumentsTest(){
            assertFalse(CategoryDAO.deleteByProject("joseph", "Build house"));
            assertFalse(CategoryDAO.deleteByProject("olanormann", "Start business"));
        }

        @Test
        public void deleteCategoryWithFalseArgumentsTest(){
            assertFalse(CategoryDAO.delete("joseph", "Home"));
            assertFalse(CategoryDAO.delete("olanormann", "Cabin"));
            assertFalse(CategoryDAO.delete("joseph", "Build house", "Foundation"));
            assertFalse(CategoryDAO.delete("olanormann", "Start business", "Take loan"));
            assertFalse(CategoryDAO.delete("olanormann", "Build house", "Plumbing"));
        }
    }

    @AfterAll
    public static void deleteTestData(){
        UserDAO.delete("olanormann");
    }
}
