package dao;

import ntnu.idatt1002.dao.HelpPageDAO;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HelpPageDAOTest {

    @Test
    public void kappa() {
        try {
            HelpPageDAO.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test() {
        try {
            HelpPageDAO.getSections().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
