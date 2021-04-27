package ntnu.idatt1002.dao;

import ntnu.idatt1002.model.HelpSection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HelpPageDAOTest {

    @Test
    public void getDataTest() {
        HelpSection[] helpSections = HelpPageDAO.getData();
        assertEquals(helpSections[0].getSection(), "Register");
    }


    @Test
    public void getSectionsTest() {
        assertEquals(HelpPageDAO.getSections().get(0), "Register");
    }
}
