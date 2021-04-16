package dao;

import ntnu.idatt1002.dao.HelpPageDAO;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HelpPageDAOTest {

    @Test
    public void kappa() {

    }
    @Test
    public void test() {
        try {
            HelpPageDAO.getSections();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
