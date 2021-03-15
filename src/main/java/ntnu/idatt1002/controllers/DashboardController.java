package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class DashboardController {

    @FXML private Text categoryName;
    @FXML private VBox categories;
    @FXML private BorderPane borderPane;

    public void initialize() throws IOException {
        updateCenterContent("tasks");
    }

    public void buttonNewTask() throws IOException {
        updateCenterContent("newTask");
    }

    public void buttonEditCategory() throws IOException {
        updateCenterContent("editCategory");
    }

    public void buttonNewCategory() throws IOException {
        updateCenterContent("newCategory");
    }

    public void updateCenterContent(String page) throws IOException {
        AnchorPane newContent =  FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
        borderPane.setCenter(newContent);
    }
}
