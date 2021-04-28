package ntnu.idatt1002.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HelpServiceTest {

    @Test
    public void getSectionsTest() {
        assertTrue(HelpService.getSections().size() > 1);
    }

    @Test
    public void getSpecificSectionTest() {

        //Existing Section
        String section = HelpService.getSections().get(0);
        assertEquals(section, HelpService.getSection(section).getSection());

        // Not existing
        assertNull(HelpService.getSection("This Section Does Not Exist"));
    }
}
