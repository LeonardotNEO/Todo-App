package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController {
    // Variables
    private static MainController instance;

    // FXML
    @FXML private BorderPane pane;

    public MainController(){
        instance = this;
    }

    public void initialize() throws IOException {
        setNavbar("navbar");
        setMainContent("dashboard");
    }

    /**
     * Loads an fxml page to content section of main.fxml
     * @param page
     * @throws IOException
     */
    public void setMainContent(String page) throws IOException {
        AnchorPane newContent =  FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
        pane.setBottom(newContent);
    }

    /**
     * Loads an fxml page to navbar section of main.fxml
     * @param page which fxml page to load (the name)
     * @throws IOException
     */
    public void setNavbar(String page) throws IOException {
        AnchorPane newContent =  FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
        pane.setTop(newContent);
    }

    /**
     * Returnes an istance of MainController, which can be used by the others controllers to edit the content of navbar and content
     * @return
     */
    public static MainController getInstance(){
        return instance;
    }
}
