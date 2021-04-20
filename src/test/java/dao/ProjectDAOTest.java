package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.ProjectDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectDAOTest {
    @BeforeAll
    public static void test_data(){
        UserDAO.serialize(new User("olanormann"));
        ProjectDAO.add("olanormann", "Build house");
        ProjectDAO.add("olanormann", "Start business");
    }

    @Test
    public void list_of_projects(){
        String[] projects = ProjectDAO.list("olanormann");

        assertTrue(projects.length == 2 && Arrays.asList(projects).contains("Build house"));
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void list_of_projects_by_non_existing_user(){
            assertNull(ProjectDAO.list("joseph"));
        }

        @Test
        public void add_project_to_none_existing_user(){
            assertFalse(ProjectDAO.add("joseph", "Renovate kitchen"));
        }

        @Test
        public void delete_project_by_none_existing_user(){
            assertFalse(ProjectDAO.deleteByUser("joseph"));
        }

        @Test
        public void delete_project_with_wrong_arguments(){
            assertFalse(ProjectDAO.delete("joseph", "Renovate kitchen"));

            assertFalse(ProjectDAO.delete("olanormann", "Renovate kitchen"));
        }
    }

    @AfterAll
    public static void delete_test_data(){
        UserDAO.delete("olanormann");
    }
}
