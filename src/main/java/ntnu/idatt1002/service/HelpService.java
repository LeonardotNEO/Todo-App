package ntnu.idatt1002.service;

import ntnu.idatt1002.model.HelpSection;
import ntnu.idatt1002.dao.HelpPageDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class HelpService {

    public static HelpSection getSection(String section) {
        HelpSection[] helpSections = HelpPageDAO.getData();
        Optional<HelpSection> a = Arrays.stream(helpSections).filter(c -> c.getSection().equals(section)).findFirst();
        if(!a.isPresent()) return null;
        return a.get();
    }

    public static ArrayList<String> getSections() {
        return HelpPageDAO.getSections();
    }
}
