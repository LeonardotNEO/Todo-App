package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {

    @Test
    public void checkIfPasswordValidTest() {
        // Invalid password
        assertFalse(RegisterService.checkIfPasswordValidSyntax("123", "12347"));
        assertFalse(RegisterService.checkIfPasswordValid("123", "1234567"));

        // Valid password
        assertTrue(RegisterService.checkIfPasswordValidSyntax("1234567", "1234567"));
        assertTrue(RegisterService.checkIfPasswordValid("1234567", "1234567"));
    }

    @Test
    public void checkIfUserNameValidTest() {
        User user = new User("Test");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername("Test");

        // Username already taken
        assertFalse(RegisterService.checkIfUsernameValid("Test"));

        // Invalid syntax
        assertFalse(RegisterService.checkIfUsernameValidSyntax(""));

        // Valid username and syntax
        assertTrue(RegisterService.checkIfUsernameValid("Test123"));
        assertTrue(RegisterService.checkIfUsernameValidSyntax("Test123"));

        // Delete the user
        UserService.deleteUser();
    }

    @Test
    public void registerNewUserTest() {
        // Create new user.
        RegisterService.registerNewUser("Test", "1234567", false);
        // Check if user is in system by checking state.
        assertEquals("Test", UserStateService.getCurrentUser().getUsername());
        // Delete the user after test is complete.
        UserService.deleteUser();
    }
}
