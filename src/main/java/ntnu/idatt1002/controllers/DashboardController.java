package ntnu.idatt1002.controllers;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
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
     * Intialize method used to load dashboard.
     *
     * @throws IOException
     */
    public void initialize() throws IOException {
        // loads tasks page
        loadTasksPage(TaskService.getCategoryWithTasks(UserStateService.getCurrentUserCategory()));

        // load category buttons to categories VBox
        loadCategoryButtons();

        // load categoryBar and taskBar if currentCategory exists in UserState
        updateCategoryEditDeleteBar();
    }

    /**
     * Load categoryButtons to categories VBox
     */
    public void loadCategoryButtons(){
        deleteCategoryButtons();

        String[] categoriesList = CategoryService.getCategoriesCurrentUser();
        for (String category : categoriesList) {
            Button button = new Button();

            button.setText(category);

            button.styleProperty().bind(Bindings
                    .when(button.hoverProperty())
                    .then(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: orange;"))
                    .otherwise(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: white;")));
            button.cursorProperty().setValue(Cursor.HAND);

            MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.FOLDER_OPEN);
            icon.fillProperty().setValue(Paint.valueOf("White"));
            icon.setGlyphSize(25);
            button.setGraphic(icon);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        // When this category button is clicked, we loadtaskspage with this category
                        loadTasksPage(TaskService.getCategoryWithTasks(category));

                        // Set currently saved category to this category
                        UserStateService.setCurrentUserCategory(category);

                        // update categorybutton UI
                        updateCategoryButtons();

                        // update categoryEditDeleteBar UI
                        updateCategoryEditDeleteBar();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            categories.getChildren().add(categories.getChildren().size(), button);
        }

        updateCategoryButtons();
    }

    /**
     * Method for updating UI of categoryButtons. Currently selected catergory is showed as orange at all times.
     */
    public void updateCategoryButtons(){
        categories.getChildren().forEach(node -> {
            Button button = (Button) node;
            if(button.getText().equals(UserStateService.getCurrentUserCategory())){
                // selected category as defined in UserStateService in set to show color orange
                button.styleProperty().bind(Bindings
                        .when(button.hoverProperty())
                        .then(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: orange;"))
                        .otherwise(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: orange;")));
                MaterialDesignIconView icon = (MaterialDesignIconView) button.getGraphic();
                icon.setFill(Paint.valueOf("orange"));
            } else {
                button.styleProperty().bind(Bindings
                        .when(button.hoverProperty())
                        .then(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: orange;"))
                        .otherwise(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: white;")));
                MaterialDesignIconView icon = (MaterialDesignIconView) button.getGraphic();
                icon.setFill(Paint.valueOf("white"));
            }

        });
    }

    /**
     * Delete UI elements of categories VBox
     */
    public void deleteCategoryButtons(){
        categories.getChildren().removeAll(categories.getChildren());
    }

    /**
     * Update update category/task edit/delete bar according to UserStateService values
     */
    public void updateCategoryEditDeleteBar(){
        if(UserStateService.getCurrentUserCategory() != null){
            // set categorytitle to category in savefile
            categoryName.setText(UserStateService.getCurrentUserCategory());

            // show category and task HBox
            categoryHBox.setVisible(true);
            taskHBox.setVisible(true);
        } else {
            categoryName.setText("");

            categoryHBox.setVisible(false);
            taskHBox.setVisible(false);
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
     * Delete currently visited category
     */
    public void buttonDeleteCategory() throws IOException {
        CategoryService.deleteCategoryCurrentUser(UserStateService.getCurrentUserCategory());

        if(CategoryService.getCategoriesCurrentUser().length >= 1){
            UserStateService.setCurrentUserCategory(CategoryService.getCategoriesCurrentUser()[0]);
        } else {
            UserStateService.setCurrentUserCategory(null);
        }

        // update content of tasks, catalogVBox and catalog/task-HBox
        loadTasksPage(TaskService.getTasksByCategory(UserStateService.getCurrentUserCategory()));
        updateCategoryEditDeleteBar();
        loadCategoryButtons();
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
        sort.getItems().add(createSortingMenuItem("Date", TaskService.TasksSortedByDate()));
        sort.getItems().add(createSortingMenuItem("Alphabet", TaskService.TasksSortedByAlphabet()));
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
     * @param tasks
     * @throws IOException
     */
    public void loadTasksPage(ArrayList<Task> tasks) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
        BorderPane borderPane = loader.load();
        TasksController tasksController = loader.getController();

        if(tasks == null || tasks.isEmpty()){
            // if tasks ArrayList is empty
            tasksController.tasksIsEmpty();
        } else {
            // add tasks to generated taskspage
            tasksController.addTasks(tasks);

            // update MenuButton sort with newest arraylists<Task>
            updateSortingOptions();
        }

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
