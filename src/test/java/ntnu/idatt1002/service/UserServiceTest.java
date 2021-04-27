package ntnu.idatt1002.service;

import ntnu.idatt1002.model.User;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void deleteUserThatIsAlreadyLoggedInTest() {
        // Create a new user, save and serialize it
        User user = new User("Test");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername(user.getUsername());

        // Delete the new user created
        assertTrue(UserService.deleteUser());
    }

    @Test
    public void DeleteUserWithoutAUserInStateTest() {
        assertThrows(NullPointerException.class,()->UserService.deleteUser());
    }
}
