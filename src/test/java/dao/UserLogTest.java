package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.dao.UserLog;
import ntnu.idatt1002.dao.UserLogDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserLogTest {
    private final static String username = "olanormann";
    private final static User userA = new User(username);
    private static UserLog userLog;

    @BeforeAll
    public static void setup(){
        UserDAO.serializeUser(userA);

        UserLogDAO.setUserRegistration(username);
        UserLogDAO.setCategoryAdded(username, "Home");
        UserLogDAO.setTaskAdded(username, "Clean bedroom");
        UserLogDAO.setTaskAdded(username, "Do dishes");
        UserLogDAO.setTaskDone(username, "Do dishes");
        UserLogDAO.setCategoryRemoved(username, "Home");
        UserLogDAO.setTaskRemoved(username, "Clean bedroom");

        userLog = UserLogDAO.getUserLog(username);
    }

    @Test
    public void _getUserCreation(){
        String userCreation = userLog.getUserCreation();
        assertTrue(userCreation.contains("User created"));
    }

    @Nested
    public class array_results {
        @Test
        public void _getCategoryAdded() {
            String[] categoryAdded = userLog.getCategoryAdded();
            assertTrue(Arrays.stream(categoryAdded).allMatch(str -> str.contains("Category created")));
        }

        @Test
        public void _getCategoryRemoved(){
            String[] categoryRemoved = userLog.getCategoryRemoved();
            assertTrue(Arrays.stream(categoryRemoved).allMatch(str -> str.contains("Category removed")));
        }

        @Test
        public void _getTaskAdded(){
            String[] taskAdded = userLog.getTaskAdded();
            assertTrue(Arrays.stream(taskAdded).allMatch(str -> str.contains("Task created")));
        }
    }

    @AfterAll
    public static void cleanup(){
        UserDAO.deleteUser(username);
    }
}
