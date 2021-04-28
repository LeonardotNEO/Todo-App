package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {

    @Test
    public void checkIfLoginSyntaxValidTest() {
        assertFalse(LoginService.checkIfLoginSyntaxValid(""));
        assertTrue(LoginService.checkIfLoginSyntaxValid("ABC"));
    }

    @Test
    public void checkIfLoginValidTest() {
        User test = new User("Abc");
        UserStateService.setCurrentUserUsername("Abc");

        // Setting password
        test.setSalt(UserDAO.generateSalt());
        test.setPassword("ABC");

        // Serializing user
        UserDAO.serialize(test);
        // Wrong password
        assertFalse(LoginService.checkIfLoginValid("Abc", "djsakdjsajdasj"));

        // Correct password
        assertTrue(LoginService.checkIfLoginValid("Abc", "ABC"));
    }

    @AfterAll
    public static void clean() {
        UserDAO.delete("Abc");
        UserStateService.setCurrentUserUsername(null);
    }
}
