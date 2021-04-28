package ntnu.idatt1002.service;

import ntnu.idatt1002.model.User;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOError;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void deleteUserWithoutAUserTest() {
        User user = new User("Test");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername("Test");
        assertTrue(UserService.deleteUser());

        // No user
        assertThrows(NullPointerException.class, UserService::deleteUser);
    }

    @Test
    public void editUserTest() {
        User user = new User("Testuser1232132");
        UserDAO.serialize(user);

        User newUser = new User("UserTestABCD");
        UserStateService.setCurrentUserUsername("UserTestABCD");

        // Edit with valid username
        UserService.editUser(user, newUser);

        assertEquals("UserTestABCD", UserStateService.getCurrentUser().getUsername());
    }

    @AfterAll
    static public void clean() {
        UserDAO.delete("Test");
        UserDAO.delete("Testuser1232132");
        UserDAO.delete("UserTestABCD");
    }
}
