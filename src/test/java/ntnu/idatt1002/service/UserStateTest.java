package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserStateTest {

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
    public void checkIfUserStateTest() {
        //No userState
        assertFalse(UserStateService.checkIfUserState());

        //User in state
        User user = new User("Test");
        UserDAO.serialize(user);
        assertTrue(UserStateService.checkIfUserState());
    }


    @Test
    public void setCurrentUserUsername() {
        User user = new User("Test");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername("Test");

        assertEquals("Test", UserStateService.getCurrentUserUsername());
    }


    @Test
    public void getCurrentUserUsernameTest() {
        // No user
        assertThrows(NullPointerException.class, UserStateService::getCurrentUserUsername);

        //User in state
        User user = new User("Test");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername("Test");

        assertEquals("Test", UserStateService.getCurrentUserUsername());
    }
}
