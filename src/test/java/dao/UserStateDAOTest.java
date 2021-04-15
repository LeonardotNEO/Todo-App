package dao;

import ntnu.idatt1002.dao.UserStateDAO;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UserStateDAOTest {
    @Test
    public void values_store_properly(){
        UserStateDAO.setUserState("olanormann", "Home", "Date", false);
        String username = UserStateDAO.getUsername();
        String category = UserStateDAO.getSelectedCategory();
        String sort = UserStateDAO.getSelectedSort();
        boolean rememberMe = UserStateDAO.getRememberMe();

        assertTrue(username.equals("olanormann") && category.equals("Home") && sort.equals("Date") &&
                !rememberMe);
    }

    @Test
    public void handle_file_not_existing(){
        File file = new File("src/main/resources/saves/userstate.ser");
        boolean result = file.delete();

        assertNull(UserStateDAO.getUsername());

        UserStateDAO.setUserState(null, null, null, false);
    }
}
