package service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.service.UserService;
import ntnu.idatt1002.service.UserStateService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void DeleteUserTest() {
        // Create a new user, save and serialize it
        User user = new User("Test");
        UserDAO.serializeUser(user);
        UserStateService.setCurrentUser(user.getUsername());

        // Delete the new user created
        assertTrue(UserService.deleteUser());
    }

    @Test
    public void DeleteUserWithoutAUserInState() {
        assertFalse(UserService.deleteUser());
    }
}
