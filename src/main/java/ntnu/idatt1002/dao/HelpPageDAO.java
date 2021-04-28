package ntnu.idatt1002.dao;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import ntnu.idatt1002.model.HelpSection;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Reads helpPage data from storage
 */
public class HelpPageDAO {

    public static HelpSection[] getData() {
        Gson gson = new Gson();
        FileReader fileReader = null;

        try {
            fileReader = new FileReader("src/main/resources/helpData/format.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(fileReader == null) return null;

        JsonReader reader = gson.newJsonReader(fileReader);
        return gson.fromJson(reader, HelpSection[].class);
    }

    /**
     * A method that returns all the sections in the helpPage json file
     */
    public static ArrayList<String> getSections()  {
        HelpSection[] helpSections = getData();
        if(helpSections == null) return null;
        return Arrays.stream(helpSections).map(HelpSection::getSection).collect(Collectors.toCollection(ArrayList::new));
    }
}
