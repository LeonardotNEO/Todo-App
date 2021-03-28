package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.dao.UserStateDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserStateDAOTest {
    private final static User userA = new User("olanormann");

    @BeforeAll
    public static void setup(){
        UserDAO.serializeUser(userA);
        UserStateDAO.setUserState("olanormann", "Home", "Alphabetic", false);
    }

    @Test
    public void _getUsername(){
        String username = UserStateDAO.getUsername();

        assertEquals(username, "olanormann");
    }

    @Test
    public void _getSelectedCategory(){
        String selectedCategory = UserStateDAO.getSelectedCategory();

        assertEquals(selectedCategory, "Home");
    }

    @Test
    public void _getSelectedSort(){
        String selectedSort = UserStateDAO.getSelectedSort();

        assertEquals(selectedSort, "Alphabetic");
    }

    @AfterAll
    public static void cleanup(){
        UserDAO.deleteUser("olanormann");
        UserStateDAO.setUserState(null, null, null, false);
    }
}
