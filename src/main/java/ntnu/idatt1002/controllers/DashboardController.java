package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class DashboardController {
    //VARIABLES
    private static DashboardController instance;

    //FXML
    @FXML private Text categoryName;
    @FXML private VBox categories;
    @FXML private BorderPane borderPane;

    public DashboardController(){
        instance = this;
    }

    public void initialize() throws IOException {
        setCenterContent("tasks");
    }

    public void buttonNewTask() throws IOException {
        setCenterContent("newTask");
    }

    public void buttonEditCategory() throws IOException {
        setCenterContent("editCategory");
    }

    public void buttonNewCategory() throws IOException {
        setCenterContent("newCategory");
    }

    public void setCenterContent(String page) throws IOException {
        AnchorPane newContent =  FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
        borderPane.setCenter(newContent);
    }

    public static DashboardController getInstance(){
        return instance;
    }
}
