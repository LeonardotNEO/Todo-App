package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private final static User userA = new User("olanormann");

    @BeforeAll
    public static void setup(){
        UserDAO.serializeUser(userA);
    }

    @Test
    public void _getUsers(){
        ArrayList<User> users = UserDAO.getUsers();

        assertTrue(users.contains(userA));
    }

    @Test
    public void _deserializeUser(){
        User userB = UserDAO.deserializeUser("olanormann");

        assertEquals(userA, userB);
    }

    @Test
    public void get_user_that_does_not_exist(){
        assertNull(UserDAO.deserializeUser("josephjoestar"));
    }

    @Nested
    public class salt_and_hashing{
        @Test
        public void two_salts_are_not_equal(){
            byte[] saltA = UserDAO.generateSalt();
            byte[] saltB = UserDAO.generateSalt();

            assertNotEquals(saltA, saltB);
        }

        @Test
        public void equal_string_equal_salt_makes_same_hash(){
            byte[] salt = UserDAO.generateSalt();
            String password = "test123";

            String hashA = UserDAO.hashPassword(password, salt);
            String hashB = UserDAO.hashPassword(password, salt);

            assertEquals(hashA, hashB);
        }

        @Nested
        public class makes_not_same_hash{
            @Test
            public void equal_string_different_salt(){
                byte[] saltA = UserDAO.generateSalt();
                byte[] saltB = UserDAO.generateSalt();
                String password = "test123";

                String hashA = UserDAO.hashPassword(password, saltA);
                String hashB = UserDAO.hashPassword(password, saltB);

                assertNotEquals(hashA, hashB);
            }

            @Test
            public void different_string_equal_salt(){
                byte[] salt = UserDAO.generateSalt();
                String passwordA = "test123";
                String passwordB = "password99";

                String hashA = UserDAO.hashPassword(passwordA, salt);
                String hashB = UserDAO.hashPassword(passwordB, salt);

                assertNotEquals(hashA, hashB);
            }
        }
    }

    @AfterAll
    public static void cleanup(){
        UserDAO.deleteUser("olanormann");
    }
}
