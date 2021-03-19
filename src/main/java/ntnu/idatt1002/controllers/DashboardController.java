package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

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

    /**
     * When dashboard page is loaded, the center-content will automatically show tasks.fxml
     * @throws IOException
     */
    public void initialize() throws IOException {
        // loads tasks page
        setCenterContent("tasks");

        // set currentCategoryName
        //categoryName.setText();

        // categoryName.setText() should be equals currently selected dashboards name

    }

    /**
     * Updates center-content of dashboard to newTask page
     * @throws IOException
     */
    public void buttonNewTask() throws IOException {
        setCenterContent("newTask");
    }

    /**
     * Updates center-content of dashboard to editCategory page
     * @throws IOException
     */
    public void buttonEditCategory() throws IOException {
        setCenterContent("editCategory");
    }

    /**
     * Updates center-content of dashboard to newCategory page
     * @throws IOException
     */
    public void buttonNewCategory() throws IOException {
        setCenterContent("newCategory");
    }

    /**
     * Updates center-content of dashboard to new fxml page
     * @param page name of fxml page in resources/fxml/--pageName--
     * @throws IOException
     */
    public AnchorPane setCenterContent(String page) throws IOException {
        AnchorPane newContent =  FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
        borderPane.setCenter(newContent);
        return newContent;
    }

    /**
     * Returns an instance of this controller. Makes it possible to edit dashboard page outside of the dashboardController class
     * @return
     */
    public static DashboardController getInstance(){
        return instance;
    }
}
