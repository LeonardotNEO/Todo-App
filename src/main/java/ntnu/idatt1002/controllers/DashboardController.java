package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.App;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.ProjectService;
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
    private String category;
    private String project;

    //FXML
    @FXML private Label categoryName;
    @FXML private Label projectName;
    @FXML private VBox categories;
    @FXML private VBox projects;
    @FXML private BorderPane borderPane;
    @FXML private MenuButton sort;
    @FXML private HBox categoryHBox;
    @FXML private HBox projectHBox;
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
        // we differentiate between loading normal categories and project categories
        project = UserStateService.getCurrentUser().getCurrentlySelectedProject();
        if(project == null){
            category = UserStateService.getCurrentUser().getCurrentlySelectedCategory();
        } else {
            category = UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory();
        }

        // load tasks
        loadTasksPage(category, project);

        // load category buttons to categories VBox
        loadNormalCategoryButtons();

        // load project buttons to projects VBox
        loadProjectButtons();

        // load categoryBar and taskBar if currentCategory exists in UserState
        updateCategoryEditDeleteBar();

        // initialize searchbar
        initializeSearchbar();
    }

    /**
     * Load categoryButtons to categories VBox
     */
    public void loadNormalCategoryButtons() throws IOException {
        // get the users normal categories organized
        ArrayList<String> categoriesList = CategoryService.getArrayListCategoriesOrganized();

        // delete old buttons before adding new ones
        deleteNormalCategoryButtons();

        // add categoryButtons for each category to VBox. Set properties of button. and icon.
        for (String category : categoriesList) {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/categoryMenuButton.fxml"));
            AnchorPane anchorPane = loader.load();
            CategoryMenuButtonController categoryMenuButtonController = loader.getController();

            // initialize controller
            categoryMenuButtonController.initializeNormalCategory(category);

            // add anchorpane to vbox
            categories.getChildren().add(categories.getChildren().size(), anchorPane);
        }
    }

    /**
     * Delete UI elements of categories VBox
     */
    public void deleteNormalCategoryButtons(){
        categories.getChildren().removeAll(categories.getChildren());
    }

    public void loadProjectButtons() throws IOException {
        // delete old buttons before loading new ones
        deleteProjectButtons();

        for(String project : ProjectService.getProjectsCurrentUser()){
            // create project button
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/projectMenuButton.fxml"));
            AnchorPane anchorPane = loader.load();
            ProjectMenuButtonController projectMenuButtonController = loader.getController();

            // initialize project menu button
            projectMenuButtonController.initializeProject(project);

            // load each categorybutton into projectMenuButton
            if(this.project != null){
                if(this.project.equals(project)){
                    loadProjectCategoryButtons(project, projectMenuButtonController);
                }
            }

            // add project button to vbox
            projects.getChildren().add(projects.getChildren().size(), anchorPane);
        }
    }

    public void loadProjectCategoryButtons(String projectName, ProjectMenuButtonController projectMenuButtonController) throws IOException {
        // iterate through each category under this project, and add them to projectMenuButton
        for(String category : CategoryService.getCategoriesByProjectCurrentUser(projectName)){
            // create category button
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/categoryMenuButton.fxml"));
            AnchorPane anchorPane = loader.load();
            CategoryMenuButtonController categoryMenuButtonController = loader.getController();

            // set categoryname
            categoryMenuButtonController.setCategoryName(category);

            // intialize controller
            categoryMenuButtonController.initializeProjectCategory(category, projectName);

            projectMenuButtonController.addCategoryUI(anchorPane);
        }
    }

    public void deleteProjectButtons(){
        projects.getChildren().removeAll(projects.getChildren());
    }

    /**
     * Update update category/task edit/delete bar according to UserStateService values
     */
    public void updateCategoryEditDeleteBar(){
        projectName.setText(project);
        categoryName.setText(category);

        if(project == null && category != null){
            categoryHBox.setVisible(true);
            categoryHBox.setManaged(true);
            projectHBox.setVisible(false);
            projectHBox.setManaged(false);

            // If any of the premade categories is selected, we wont show edit/delete button
            if(CategoryService.getPremadeCategories().contains(category)){
                buttonEditCategory.setVisible(false);
                buttonDeleteCategory.setVisible(false);
            }
        } else if(project != null && category == null){
            projectHBox.setVisible(true);
            projectHBox.setManaged(true);
            categoryHBox.setVisible(false);
            categoryHBox.setManaged(false);
        } else if(project != null && category != null){
            projectHBox.setVisible(true);
            projectHBox.setManaged(true);
            categoryHBox.setVisible(true);
            categoryHBox.setManaged(true);
        } else {
            projectHBox.setVisible(false);
            projectHBox.setManaged(false);
            categoryHBox.setVisible(false);
            categoryHBox.setManaged(false);
        }
    }

    /**
     * Updates center-content of dashboard to newCategory page
     * @throws IOException
     */
    public void buttonNewCategory() throws IOException {
        // Load newEditTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newEditCategory.fxml"));
        Node node = loader.load();
        NewEditCategoryController newEditCategoryController = loader.getController();

        // since we clicked the new category button on the dashboard, that means that we dont want to add the category to a project, we set project to null in user settings
        UserStateService.getCurrentUser().setCurrentlySelectedProject(null);

        // load the newCategory part of newEditCategoryController
        newEditCategoryController.intializeNewCategory();

        // set dashboard content to editMenu
        setCenterContent(node);
    }

    /**
     * Updates center-content of dashboard to editCategory page
     * @throws IOException
     */
    public void buttonEditCategory() throws IOException {
        // Load newEditTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newEditCategory.fxml"));
        Node node = loader.load();
        NewEditCategoryController newEditCategoryController = loader.getController();

        // load the editCategory part of newEditCategoryController
        newEditCategoryController.intializeEditCategory();

        // set dashboard content to editMenu
        setCenterContent(node);
    }

    /**
     * Delete currently visited category.
     * We differentiate between a normal category and a category under a project
     */
    public void buttonDeleteCategory() throws IOException {
        if(project == null){
            CategoryService.deleteCategoryCurrentUser(category);
        } else {
            CategoryService.deleteCategoryCurrentUser(category, project);
        }

        // reload dashboard
        initialize();
    }

    /**
     * Updates center-content of dashboard to newProject page
     * @throws IOException
     */
    public void buttonNewProject() throws IOException {
        // Load newEditTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newEditProject.fxml"));
        Node node = loader.load();
        NewEditProjectController newEditProjectController = loader.getController();

        // load the newCategory part of newEditCategoryController
        newEditProjectController.initializeNew();

        // set dashboard content to editMenu
        setCenterContent(node);
    }

    public void buttonEditProject() throws IOException {
        // Load newEditTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newEditProject.fxml"));
        Node node = loader.load();
        NewEditProjectController newEditProjectController = loader.getController();

        // load the newCategory part of newEditCategoryController
        newEditProjectController.initializeEdit();

        // set dashboard content to editMenu
        setCenterContent(node);
    }

    public void buttonDeleteProject() throws IOException {
        ProjectService.deleteProjectCurrentUser(project);

        // reload dashboard
        initialize();
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
        sort.getItems().add(createSortingMenuItem("Priority", TaskService.getTasksSortedByPriority(TaskService.getTasksByCategory(category, project))));
        sort.getItems().add(createSortingMenuItem("Date", TaskService.getTasksSortedByDate(TaskService.getTasksByCategory(category, project))));
        sort.getItems().add(createSortingMenuItem("Alphabet", TaskService.getTasksSortedAlphabetically(TaskService.getTasksByCategory(category, project))));
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
     * @throws IOException
     */
    public void loadTasksPage(String category, String project) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
        BorderPane borderPane = loader.load();
        TasksController tasksController = loader.getController();

        // get tasks
        ArrayList<Task> tasks = TaskService.getTasksByCategory(category, project);

        // add tasks to generated taskspage
        tasksController.addTasks(tasks);

        // show no message when loaded tasks are not equals 0
        tasksController.showMessage(null);

        // update MenuButton sort with newest arraylists<Task>
        updateSortingOptions();

        // add tasksController UI to dashboard center
        setCenterContent((Node) borderPane);
    }

    /**
     * Loads an empty Tasks UI elements, adds task UI elements to it. Then we set center content of dashboard to tasks.fxml
     * @throws IOException
     */
    public void loadTasksPage(ArrayList<Task> tasks) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
        BorderPane borderPane = loader.load();
        TasksController tasksController = loader.getController();

        if(tasks == null || tasks.contains(null) || tasks.isEmpty()){
            tasksController.tasksIsEmptySearch();
        } else {
            // add tasks to generated taskspage
            tasksController.addTasks(tasks);

            // show no message when loaded tasks are not equals 0
            tasksController.showMessage(null);

            // update MenuButton sort with newest arraylists<Task>
            updateSortingOptions();
        }

        setCenterContent((Node) borderPane);
    }

    /**
     * Method for creating MenuItem element, and adding an action event to it
     * @param name
     * @return
     */
    public MenuItem createSortingMenuItem(String name, ArrayList<Task> tasks){
        MenuItem menuItem = new MenuItem();
        menuItem.setText(name);
        menuItem.setOnAction(event -> {
            try {
                loadTasksPage(tasks);
            } catch (IOException e) {
                e.printStackTrace();
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
                loadTasksPage(TaskService.containsDesiredNameInTitle(newValue));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
