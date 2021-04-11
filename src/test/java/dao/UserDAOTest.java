package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDAOTest {
    private final static User userA = new User("olanormann");

    @BeforeAll
    public static void setup(){
        UserDAO.serialize(userA);
    }

    @Nested
    public class serializing_and_deserializing{
        @Test
        public void _deserialize(){
            User userB = UserDAO.deserialize("olanormann");

            assertEquals(userA, userB);
        }
    }

    @AfterAll
    public static void cleanup(){
        boolean result = UserDAO.deleteUser("olanormann");
    }
}
