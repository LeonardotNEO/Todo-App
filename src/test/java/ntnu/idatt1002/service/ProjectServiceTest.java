package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectServiceTest {

    @BeforeAll
    public static void setup() {
        User user = new User("test");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername("test");
    }

    @AfterAll
    public static void clean() {
        UserService.deleteUser();
    }

    @Test
    public void addAndGetNewProjectTest() {
        ProjectService.addNewProjectCurrentUser("My Project");
        assertEquals("My Project", ProjectService.getProjectsCurrentUser()[0]);
    }

    @Test
    public void deleteProjectCurrentUserTest() {
        ProjectService.addNewProjectCurrentUser("My Project");
        assertEquals("My Project", ProjectService.getProjectsCurrentUser()[0]);
        ProjectService.deleteProjectCurrentUser("My Project");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            String name = ProjectService.getProjectsCurrentUser()[0];
        });
    }

    @Test
    public void validateTitleTest() {
        //Invalid Title
        assertFalse(ProjectService.validateTitle(""));
        assertFalse(ProjectService.validateTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); // Over 30 characters
        // Valid Title
        assertTrue(ProjectService.validateTitle("My project"));
    }

    @Test
    public void editProjectTest() {
        ProjectService.addNewProjectCurrentUser("My Project");
        assertEquals("My Project", ProjectService.getProjectsCurrentUser()[0]);

        ProjectService.editProject("My Project", "My Project 2");
        assertEquals("My Project 2", ProjectService.getProjectsCurrentUser()[0]);
    }
}
