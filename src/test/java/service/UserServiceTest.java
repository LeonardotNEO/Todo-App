package service;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.Storage;
import ntnu.idatt1002.service.UserService;
import ntnu.idatt1002.service.UserStateService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public static void delete_user_that_is_already_logged_in() {
        // Create a new user, save and serialize it
        User user = new User("Test");
        Storage.newUser(user);
        UserStateService.setCurrentUserUsername(user.getUsername());

        // Delete the new user created
        assertTrue(UserService.deleteUser());
    }

    @Test
    public static void DeleteUserWithoutAUserInState() {
        assertFalse(UserService.deleteUser());
    }
}
