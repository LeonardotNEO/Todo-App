package ntnu.idatt1002.service;

import ntnu.idatt1002.model.HelpSection;
import ntnu.idatt1002.dao.HelpPageDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * The HelpService Class is a class used in the help page controller to get the diffrent sections.
 */
public class HelpService {
    /**
     * methode for getting section
     * @param section string, which is the name of the section.
     * @return the help section
     */
    public static HelpSection getSection(String section) {
        HelpSection[] helpSections = HelpPageDAO.getData();
        Optional<HelpSection> a = Arrays.stream(helpSections).filter(c -> c.getSection().equals(section)).findFirst();
        if(!a.isPresent()) return null;
        return a.get();
    }

    /**
     * Methode for getting all help sections in the HelpPageDAO class.
     * @return the help sections which are stored in the system.
     */
    public static ArrayList<String> getSections() {
        return HelpPageDAO.getSections();
    }
}
