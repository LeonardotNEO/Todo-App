package ntnu.idatt1002.dao;

import javax.swing.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads helpPage data from storage
 */
public class HelpPageDAO {
    public static void getData() {

    }

    /**
     * A method that returns all the sections in the helpPage json file
     */
    public static void getSections() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader("src/main/resources/helpData/format.json"));
        for(Object o : a) {
            JSONObject person = (JSONObject) o;
            System.out.println(person.get("section"));
        }
    }
}
