package dao;

import ntnu.idatt1002.dao.HelpPageDAO;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HelpPageDAOTest {

    @Test
    public void kappa() {
        HelpPageDAO.getData();
    }
    @Test
    public void test() {
        HelpPageDAO.getSections().forEach(System.out::println);
    }
}
