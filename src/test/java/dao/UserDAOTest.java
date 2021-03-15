package dao;

import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserDAOTest {
    @Test
    public void two_salts_are_not_equal(){
        byte[] saltA = UserDAO.generateSalt();
        byte[] saltB = UserDAO.generateSalt();

        assertNotEquals(saltA, saltB);
    }

    @Nested
    class _hashPassword{
        byte[] saltA = UserDAO.generateSalt();
        byte[] saltB = UserDAO.generateSalt();
        String passwordA = "test123";
        String passwordB = "password99";

        @Nested
        class not_same_hash{
            @Test
            public void equal_salt_and_different_password(){
                String hashA = UserDAO.hashPassword(passwordA, saltA);
                String hashB = UserDAO.hashPassword(passwordB, saltA);

                assertNotEquals(hashA, hashB);
            }

            @Test
            public void different_salt_and_equal_password(){
                String hashA = UserDAO.hashPassword(passwordA, saltA);
                String hashB = UserDAO.hashPassword(passwordA, saltB);

                assertNotEquals(hashA, hashB);
            }
        }

        @Test
        public void equal_salt_and_equal_password_produces_same_hash(){
            String hashA = UserDAO.hashPassword(passwordA, saltA);
            String hashB = UserDAO.hashPassword(passwordA, saltA);

            assertEquals(hashA, hashB);
        }
    }
}
