package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.dao.UserStateDAOTest;
import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.UserDataHandler;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {

    @BeforeAll
    public static void setup() {
        User test = new User("Abc", "ABC", null);
    }

    @Test
    public void checkIfLoginSyntaxValidTest() {
        assertFalse(LoginService.checkIfLoginSyntaxValid(""));
        assertTrue(LoginService.checkIfLoginSyntaxValid("ABC"));
    }

    @Test
    public void checkIfLoginValidTest() {
        User test = new User("Abc", "ABC", null);
        UserStateService.setCurrentUserUsername("Abc");
        test.setSalt(UserDAO.generateSalt());
        UserDAO.serialize(test);
        byte[] salt = test.getSalt();
        // Wrong password
        assertFalse(LoginService.checkIfLoginValid("Abc", "djsakdjsajdasj"));

        // Correct password
        assertTrue(LoginService.checkIfLoginValid("Abc", "ABC"));
    }
}
