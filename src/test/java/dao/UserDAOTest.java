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
    public static void test_data(){
        UserDAO.serialize(userA);
    }

    @Nested
    public class serializing_and_deserializing{
        @Test
        public void deserializing_user(){
            User userB = UserDAO.deserialize("olanormann");

            assertEquals(userA, userB);
        }

        @Test
        public void get_user(){
            ArrayList<User> users = UserDAO.list();

            assertFalse(users.isEmpty());
            assertTrue(users.contains(userA));
        }

        @Test
        public void user_not_existing_handled(){
            User userB = UserDAO.deserialize("joseph");

            assertNull(userB);
        }

        @Nested
        public class wrong_arguments{
            @Test
            public void deserializing_none_existing_user(){
                assertNull(UserDAO.deserialize("joseph"));
            }

            @Test
            public void delete_none_existing_user(){
                assertFalse(UserDAO.delete("joseph"));
            }
        }
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
    public static void delete_test_data(){
        boolean result = UserDAO.delete("olanormann");
    }
}
