package dao;

import ntnu.idatt1002.dao.UserStateDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserStateDAOTest {
    @Test
    public void set_and_get_user_state(){
        UserStateDAO.setUserState("olanormann");
        String result = UserStateDAO.getUserState();

        assertEquals("olanormann", result);
    }

    @Test
    public void set_user_state_to_null_then_get(){
        UserStateDAO.setUserState(null);
        String result = UserStateDAO.getUserState();

        assertNull(result);
    }
}
