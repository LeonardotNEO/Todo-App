package dao;

import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.CommonDAO;
import ntnu.idatt1002.dao.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonDAOTest {
    @BeforeAll
    public static void setup(){
        Storage.newUser(new User("olanormann"));
        Storage.read("olanormann");
    }
}
