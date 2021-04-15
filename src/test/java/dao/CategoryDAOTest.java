package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.ProjectDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDAOTest {
    @BeforeAll
    public static void setup(){
        UserDAO.serialize(new User("olanormann"));
        CategoryDAO.add("olanormann", "Home");
        CategoryDAO.add("olanormann", "Work");
        ProjectDAO.add("olanormann", "Build house");
        CategoryDAO.add("olanormann", "Build house", "Foundation");
        CategoryDAO.add("olanormann", "Build house", "Carpentry");
    }

    @Test
    public void _list(){
        String[] regular = CategoryDAO.list("olanormann");
        String[] project = CategoryDAO.list("olanormann", "Build house");

        assertTrue(regular.length == 2 && Arrays.asList(regular).contains("Home"));
        assertTrue(project.length == 2 && Arrays.asList(project).contains("Foundation"));
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void _list(){
            assertNull(CategoryDAO.list("joseph"));
            assertNull(CategoryDAO.list("joseph", "Build house"));
            assertNull(CategoryDAO.list("olanormann", "Start business"));
        }

        @Test
        public void _add(){
            assertFalse(CategoryDAO.add("joseph", "Home"));
            assertFalse(CategoryDAO.add("joseph", "Build house", "Foundation"));
            assertFalse(CategoryDAO.add("olanormann", "Start business", "Take loan"));
        }

        @Test
        public void _deleteByUser(){
            assertFalse(CategoryDAO.deleteByUser("joseph"));
        }

        @Test
        public void _deleteByProject(){
            assertFalse(CategoryDAO.deleteByProject("joseph", "Build house"));
            assertFalse(CategoryDAO.deleteByProject("olanormann", "Start business"));
        }

        @Test
        public void _delete(){
            assertFalse(CategoryDAO.delete("joseph", "Home"));
            assertFalse(CategoryDAO.delete("olanormann", "Cabin"));
            assertFalse(CategoryDAO.delete("joseph", "Build house", "Foundation"));
            assertFalse(CategoryDAO.delete("olanormann", "Start business", "Take loan"));
            assertFalse(CategoryDAO.delete("olanormann", "Build house", "Plumbing"));
        }
    }

    @AfterAll
    public static void cleanup(){
        UserDAO.delete("olanormann");
    }
}
