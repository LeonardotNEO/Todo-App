package ntnu.idatt1002.dao;

import org.junit.jupiter.api.Test;

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
