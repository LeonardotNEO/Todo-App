package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import ntnu.idatt1002.App;

import java.io.IOException;
import java.security.cert.PolicyNode;

public class MainController {


    @FXML private AnchorPane navbar;
    @FXML private AnchorPane content;

    public void initialize() throws IOException {
        setNavbar("navbar");
    }

    public void setMainContent(String fxmlfileName) throws IOException {
        AnchorPane newContent =  FXMLLoader.load(getClass().getResource("/fxml/" + fxmlfileName + ".fxml"));
        content.getChildren().setAll(newContent);
        System.out.println("hello");
        System.out.println(content.getChildren());
    }

    public void setNavbar(String fxmlfileName) throws IOException {
        AnchorPane newContent =  FXMLLoader.load(getClass().getResource("/fxml/" + fxmlfileName + ".fxml"));
        navbar.getChildren().setAll(newContent);
    }
}
