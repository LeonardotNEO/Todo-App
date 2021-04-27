package ntnu.idatt1002.dao;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class UserStateDAOTest {
    @Nested
    public class userstate {
        @Test
        public void values_store_properly() {
            UserStateDAO.setUserState("olanormann", "Home", "Date", false);
            String username = UserStateDAO.getUsername();
            String category = UserStateDAO.getSelectedCategory();
            String sort = UserStateDAO.getSelectedSort();
            boolean rememberMe = UserStateDAO.getRememberMe();

            assertTrue(username.equals("olanormann") && category.equals("Home") && sort.equals("Date") &&
                    !rememberMe);
        }

        @Test
        public void handle_file_not_existing() {
            File file = new File("src/main/resources/saves/userstate.ser");
            boolean result = file.delete();

            assertNull(UserStateDAO.getUsername());

            UserStateDAO.setUserState(null, null, null, false);
        }
    }

    @Nested
    public class loggedusers{
        @Test
        public void values_store_properly(){
            String[] users = {"joseph", "maria", "judas", "adam"};
            UserStateDAO.setLoggedInUsers(users);

            assertEquals(UserStateDAO.getLoggedInUsers().length, users.length);
        }

        @Test
        public void add_logged_users(){
            String[] oldUsers = {"joseph", "maria", "judas", "adam"};
            UserStateDAO.setLoggedInUsers(oldUsers);
            UserStateDAO.addLoggedUser("homer");
            String[] newUsers = UserStateDAO.getLoggedInUsers();

            assertTrue(Arrays.asList(newUsers).contains("homer"));
        }

        @Test
        public void remove_from_list(){
            String[] oldUsers = {"joseph", "maria", "judas", "adam"};
            UserStateDAO.setLoggedInUsers(oldUsers);
            UserStateDAO.removeFromList("adam");
            String[] newUsers = UserStateDAO.getLoggedInUsers();

            assertFalse(Arrays.asList(newUsers).contains("adam"));
        }

        @Test
        public void clear_list(){
            String[] oldUsers = {"joseph", "maria", "judas", "adam"};
            UserStateDAO.setLoggedInUsers(oldUsers);
            UserStateDAO.clearList();
            String[] newUsers = UserStateDAO.getLoggedInUsers();

            assertEquals(newUsers.length, 0);
        }
    }
}
