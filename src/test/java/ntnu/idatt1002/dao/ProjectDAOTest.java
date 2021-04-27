package ntnu.idatt1002.dao;

import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectDAOTest {
    @BeforeAll
    public static void testData(){
        UserDAO.serialize(new User("olanormann"));
        ProjectDAO.add("olanormann", "Build house");
        ProjectDAO.add("olanormann", "Start business");
    }

    @Test
    public void listOfProjectsTest(){
        String[] projects = ProjectDAO.list("olanormann");

        assertTrue(projects.length == 2 && Arrays.asList(projects).contains("Build house"));
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void listOfProjectsByNonExistingUserTest(){
            assertNull(ProjectDAO.list("joseph"));
        }

        @Test
        public void addProjectToNoneExistingUserTest(){
            assertFalse(ProjectDAO.add("joseph", "Renovate kitchen"));
        }

        @Test
        public void deleteProjectByNoneExistingUserTest(){
            assertFalse(ProjectDAO.deleteByUser("joseph"));
        }

        @Test
        public void deleteProjectWithWrongArgumentsTest(){
            assertFalse(ProjectDAO.delete("joseph", "Renovate kitchen"));

            assertFalse(ProjectDAO.delete("olanormann", "Renovate kitchen"));
        }
    }

    @AfterAll
    public static void deleteTestData(){
        UserDAO.delete("olanormann");
    }
}
