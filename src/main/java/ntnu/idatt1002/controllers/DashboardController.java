package ntnu.idatt1002.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.CategoryService;
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
    @FXML private HBox categoryHBox;
    @FXML private HBox taskHBox;

    public DashboardController(){
        instance = this;
    }

    /**
     * When dashboard page is loaded, the center-content will automatically show tasks.fxml with ALL the users tasks.
     *
     * @throws IOException
     */
    public void initialize() throws IOException {
        // loads tasks page
        loadTasksPage(TaskService.getTasksByCurrentUser());

        // load categoryBar and taskBar if currentCategory exists in UserState Todo
        if(CategoryService.getCategoriesCurrentUser().length > 0){
            categoryName.setText(CategoryService.getCategoriesCurrentUser()[0]);
            categoryHBox.setVisible(true);
            taskHBox.setVisible(true);
        } else {
            categoryHBox.setVisible(false);
            taskHBox.setVisible(false);
        }

        // load categoryButton to categories VBox Todo set currently selected in UserState to hovercolor
        loadCategoryButtons(CategoryService.getCategoriesCurrentUser());

    }

    /**
     * Load categoryButtons to categories VBox
     */
    public void loadCategoryButtons(String[] categoriesList){
        for (String category : categoriesList) {
            Button button = new Button();

            button.setText(category);
            button.styleProperty().bind(Bindings
                    .when(button.hoverProperty())
                    .then(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: orange;"))
                    .otherwise(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: white;")));
            button.cursorProperty().setValue(Cursor.HAND);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        loadTasksPage(TaskService.getCategoryWithTasks(category));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            categories.getChildren().add(categories.getChildren().size() - 1, button);
        }
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
     * Delete currently visied category
     */
    public void buttonDeleteCategory(){
        //CategoryService.deleteCategoryCurrentUser(); todo user UserStateService to get currentlySelectedCategory
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
        //sort.getItems().add(createSortingMenuItem("Date", TaskService.TasksSortedByDate()));
        sort.getItems().add(createSortingMenuItem("Priority", TaskService.TaskSortedByPriority()));
    }

    /**
     * Delete all sortings options from sortButton
     */
    public void deleteSortingOptions(){
        sort.getItems().removeAll(sort.getItems());
    }

    /**
     * Delete all sortings options and add new sortingsOptions
     */
    public void updateSortingOptions(){
        deleteSortingOptions();
        addSortingOptions();
    }

    /**
     * Loads an empty Tasks UI elements, adds task UI elements to it. Then we we set centercontent of dashboard to tasks.fxml
     * Use this one instead of setCenterContent directly when showing tasksUI
     * @param tasks
     * @throws IOException
     */
    public void loadTasksPage(ArrayList<Task> tasks) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
        BorderPane borderPane = loader.load();
        TasksController tasksController = loader.getController();

        // add tasks to generated taskspage
        tasksController.addTasks(tasks);

        // update MenuButton sort with newest arraylists<Task>
        updateSortingOptions();

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
