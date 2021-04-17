package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.ExcelDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ExcelDAOTest {
    @BeforeAll
    public static void setup(){
        UserDAO.serialize(new User("olanormann"));
    }

    @Test
    public void export(){
        assertDoesNotThrow(() -> ExcelDAO.export("olanormann"));
    }
}
