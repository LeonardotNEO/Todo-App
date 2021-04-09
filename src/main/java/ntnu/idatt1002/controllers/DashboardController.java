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
import javafx.scene.control.*;
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

/**
 * A class which contains the buttons related to the dashboard page of the application
 */
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
    @FXML private Button buttonEditCategory;
    @FXML private Button buttonDeleteCategory;
    @FXML private TextField searchField;

    public DashboardController(){
        instance = this;
    }

    /**
     * The initialize method used to load dashboard.
     *
     * @throws IOException
     */
    public void initialize() throws IOException {
        // loads tasks page
        loadTasksPage(TaskService.getCategoryWithTasks(UserStateService.getCurrentUser().getCurrentlySelectedCategory()));

        // load category buttons to categories VBox
        loadCategoryButtons();

        // load categoryBar and taskBar if currentCategory exists in UserState
        updateCategoryEditDeleteBar();

        // initialize searchbar
        initializeSearchbar();
    }

    /**
     * Load categoryButtons to categories VBox
     */
    public void loadCategoryButtons(){
        // delete old buttons before adding new ones
        deleteCategoryButtons();

        // add categoryButtons for each category to VBox. Set properties of button. and icon.
        for (String category : CategoryService.getArrayListCategoriesOrganized()) {
            Button button = new Button();
            MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.FOLDER_OPEN);
            if(category.equals("Trash bin")){
                icon = new MaterialDesignIconView(MaterialDesignIcon.DELETE);
            }
            if(category.equals("Finished tasks")){
                icon = new MaterialDesignIconView(MaterialDesignIcon.CHECK);
            }

            button.setText(category);
            icon.fillProperty().setValue(Paint.valueOf("White"));
            icon.setGlyphSize(25);
            button.setGraphic(icon);
            button.styleProperty().bind(Bindings
                    .when(button.hoverProperty())
                    .then(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: orange;"))
                    .otherwise(new SimpleStringProperty("-fx-background-color:  transparent; -fx-font-size: 18; -fx-text-fill: white;")));
            button.cursorProperty().setValue(Cursor.HAND);
            MaterialDesignIconView finalIcon = icon;
            button.setOnMouseEntered(e -> {
                finalIcon.setFill(Paint.valueOf("orange"));
            });
            button.setOnMouseExited(e -> {
                if(!category.equals(UserStateService.getCurrentUser().getCurrentlySelectedCategory())){
                    finalIcon.setFill(Paint.valueOf("white"));
                }
            });
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        // Set currently saved category to this category
                        UserStateService.getCurrentUser().setCurrentlySelectedCategory(category);

                        // When this category button is clicked, we loadtaskspage with this category
                        loadTasksPage(TaskService.getCategoryWithTasks(category));

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
     * Method for updating UI of categoryButtons. Currently selected category is showed as orange at all times.
     */
    public void updateCategoryButtons(){
        categories.getChildren().forEach(node -> {
            Button button = (Button) node;
            if(button.getText().equals(UserStateService.getCurrentUser().getCurrentlySelectedCategory())){
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
        if(UserStateService.getCurrentUser().getCurrentlySelectedCategory() != null){
            // set categorytitle to category in savefile
            categoryName.setText(UserStateService.getCurrentUser().getCurrentlySelectedCategory());

            // show category and task HBox
            categoryHBox.setVisible(true);
            taskHBox.setVisible(true);

            // if trashbin or finished task category is selected, we wont show edit/delete button and taskBar
            if(UserStateService.getCurrentUser().getCurrentlySelectedCategory().equals("Trash bin") || UserStateService.getCurrentUser().getCurrentlySelectedCategory().equals("Finished tasks")){
                buttonEditCategory.setVisible(false);
                buttonDeleteCategory.setVisible(false);
                taskHBox.setVisible(false);
            } else {
                buttonEditCategory.setVisible(true);
                buttonDeleteCategory.setVisible(true);
                taskHBox.setVisible(true);
            }
        } else {
            categoryHBox.setVisible(false);
            taskHBox.setVisible(false);
        }
    }

    /**
     * Updates center-content of dashboard to newTask page
     * @throws IOException
     */
    public void buttonNewTask() throws IOException {
        // Load newEditTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newEditTask.fxml"));
        Node node = loader.load();
        NewEditTaskController newEditTaskController = loader.getController();

        // load the task part of newEditTaskController
        newEditTaskController.initializeNewTask();

        // set dashboard content to editMenu
        setCenterContent(node);
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
        CategoryService.deleteCategoryCurrentUser(UserStateService.getCurrentUser().getCurrentlySelectedCategory());

        if(CategoryService.getCategoriesCurrentUser().length >= 1){
            UserStateService.getCurrentUser().setCurrentlySelectedCategory(CategoryService.getCategoriesCurrentUser()[0]);
        } else {
            UserStateService.getCurrentUser().setCurrentlySelectedCategory(null);
        }

        // update content of tasks, catalogVBox and catalog/task-HBox
        loadTasksPage(TaskService.getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory()));
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
        sort.getItems().add(createSortingMenuItem("Priority", TaskService.TaskSortedByPriority()));
        sort.getItems().add(createSortingMenuItem("Date", TaskService.TasksSortedByDate()));
        sort.getItems().add(createSortingMenuItem("Alphabet", TaskService.TasksSortedByAlphabet()));
    }

    /**
     * Delete all sorting options from sortButton
     */
    public void deleteSortingOptions(){
        sort.getItems().removeAll(sort.getItems());
    }

    /**
     * Delete all sorting options and add new sorting options
     */
    public void updateSortingOptions(){
        deleteSortingOptions();
        addSortingOptions();
    }

    /**
     * Loads an empty Tasks UI elements, adds task UI elements to it. Then we set center content of dashboard to tasks.fxml
     * @param tasks
     * @throws IOException
     */
    public void loadTasksPage(ArrayList<Task> tasks) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
        BorderPane borderPane = loader.load();
        TasksController tasksController = loader.getController();

        if(tasks == null || tasks.isEmpty()){
            // we differentiate between getting empty task arraylist when loading normally og using searchbar
            if(!searchField.getText().isEmpty()){
                tasksController.tasksIsEmptySearch();
            } else {
                tasksController.tasksIsEmpty();
            }
        } else {
            // add tasks to generated taskspage
            tasksController.addTasks(tasks);
        }

        // update MenuButton sort with newest arraylists<Task>
        updateSortingOptions();

        setCenterContent((Node) borderPane);
    }

    /**
     * Method for creating MenuItem element, and adding an action event to it
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

    /**
     * Method for initializing searchbar with listener (searchbar is updating everytime something is typed)
     */
    public void initializeSearchbar(){
        searchField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            try {
                loadTasksPage(TaskService.TasksFoundWithSearchBox(newValue));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
