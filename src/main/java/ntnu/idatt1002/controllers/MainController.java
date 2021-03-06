package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import ntnu.idatt1002.App;

import java.io.IOException;

/**
 * A class which contains the basic methods related to the javafx layout for the application
 */
public class MainController {

    private static MainController instance;
    @FXML private BorderPane pane;

    public MainController(){
        instance = this;
    }

    /**
     * When mainpage is loaded, we automatically populate it with navbar at the top, and the maincontent = dashboard
     * @throws IOException
     */
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
        Node node =  FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
        pane.setCenter(node);
    }

    /**
     * Loads an fxml page to navbar section of main.fxml
     * @param page which fxml page to load (the name)
     * @throws IOException
     */
    public void setNavbar(String page) throws IOException {
        Node node =  FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
        pane.setTop(node);
    }

    /**
     * Returns an instance of MainController, which can be used by the others controllers to edit the content of navbar and content
     * @return
     */
    public static MainController getInstance(){
        return instance;
    }
}
