package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;
import java.util.ArrayList;

public class DashboardController {
    //VARIABLES
    private static DashboardController instance;

    //FXML
    @FXML private Text categoryName;
    @FXML private VBox categories;
    @FXML private BorderPane borderPane;
    @FXML private MenuButton sort;

    public DashboardController(){
        instance = this;
    }

    /**
     * When dashboard page is loaded, the center-content will automatically show tasks.fxml
     * @throws IOException
     */
    public void initialize() throws IOException {
        // loads tasks page
        loadTasksPage(TaskService.getTasksByCurrentUser());

        // set currentCategoryName
        //categoryName.setText();

        // add sorting options to MenuButton sort at initializing of Dashboard
        addSortingOptions();
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
    public Node setCenterContent(String page) throws IOException {
        Node node =  FXMLLoader.load(getClass().getResource("/fxml/" + page + ".fxml"));
        borderPane.setCenter(node);
        return node;
    }

    /**
     * Update center-content of dashboard to Anchorpane pane
     * @param node
     * @throws IOException
     */
    public void setCenterContent(Node node) throws IOException {
        borderPane.setCenter(node);
    }

    /**
     * Returns an instance of this controller. Makes it possible to edit dashboard page outside of the dashboardController class
     * @return
     */
    public static DashboardController getInstance(){
        return instance;
    }

    /**
     * Method that adds sortingOptions to sort MenuButton
     */
    public void addSortingOptions(){
        //sort.getItems().add(createSortingMenuItem("Date", TaskService.TasksSortedByDate())); <- error from this sorting method
        sort.getItems().add(createSortingMenuItem("Priority", TaskService.TaskSortedByPriority()));
        //sort.getItems().add(createSortingMenuItem("Sort by date", TaskService.TasksSortedByDate()));
    }

    /**
     * Loads an empty Tasks UI elements, adds task UI elements to it. Then we we set centercontent of dashboard to tasks.fxml
     * @param tasks
     * @throws IOException
     */
    public void loadTasksPage(ArrayList<Task> tasks) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
        BorderPane borderPane = loader.load();
        TasksController tasksController = loader.getController();

        tasksController.addTasks(tasks);

        setCenterContent((Node) borderPane);
    }

    /**
     * Method for creating MenuItem element, and adding an actionevent to it
     * @param name
     * @param tasks
     * @return
     */
    public MenuItem createSortingMenuItem(String name, ArrayList<Task> tasks){
        MenuItem menuItem = new MenuItem();
        menuItem.setText(name);
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loadTasksPage(tasks);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return menuItem;
    }
}
