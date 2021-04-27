package ntnu.idatt1002.dao;

import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private final static User userA = new User("olanormann");

    @BeforeAll
    public static void testData(){
        UserDAO.serialize(userA);
    }

    @Nested
    public class SerializingAndDeserializing{
        @Test
        public void deserializingUserTest(){
            User userB = UserDAO.deserialize("olanormann");

            assertEquals(userA, userB);
        }

        @Test
        public void getUserTest(){
            ArrayList<User> users = UserDAO.list();

            assertFalse(users.isEmpty());
            assertTrue(users.contains(userA));
        }

        @Test
        public void userNotExistingHandledTest(){
            User userB = UserDAO.deserialize("joseph");

            assertNull(userB);
        }

        @Nested
        public class wrong_arguments{
            @Test
            public void deserializingNoneExistingUserTest(){
                assertNull(UserDAO.deserialize("joseph"));
            }

            @Test
            public void deleteNoneExistingUserTest(){
                assertFalse(UserDAO.delete("joseph"));
            }
        }
    }

    @Nested
    public class salt_and_hashing{
        @Test
        public void twoSaltsAreNotEqualTest(){
            byte[] saltA = UserDAO.generateSalt();
            byte[] saltB = UserDAO.generateSalt();

            assertNotEquals(saltA, saltB);
        }

        @Test
        public void equalStringEqualSaltMakesSameHashTest(){
            byte[] salt = UserDAO.generateSalt();
            String password = "test123";

            String hashA = UserDAO.hashPassword(password, salt);
            String hashB = UserDAO.hashPassword(password, salt);

            assertEquals(hashA, hashB);
        }

        @Nested
        public class MakesNotSameHash{
            @Test
            public void equalStringDifferentSaltTest(){
                byte[] saltA = UserDAO.generateSalt();
                byte[] saltB = UserDAO.generateSalt();
                String password = "test123";

                String hashA = UserDAO.hashPassword(password, saltA);
                String hashB = UserDAO.hashPassword(password, saltB);

                assertNotEquals(hashA, hashB);
            }

            @Test
            public void differentStringEqualSaltTest(){
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
    public static void deleteTestData(){
        boolean result = UserDAO.delete("olanormann");
    }
}
