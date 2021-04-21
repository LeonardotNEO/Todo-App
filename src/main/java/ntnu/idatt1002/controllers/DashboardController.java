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
    private String normalCategory;
    private String projectCategory;
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
        // load tasks
        // we differentiate between loading normal categories and project categories
        normalCategory = UserStateService.getCurrentUser().getCurrentlySelectedCategory();
        projectCategory = UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory();
        project = UserStateService.getCurrentUser().getCurrentlySelectedProject();

        loadTasksPage(null);

        // load category buttons to categories VBox
        loadNormalCategoryButtons();

        // load project buttons to projects VBox
        loadProjectButtons();

        // load categoryBar and taskBar if currentCategory exists in UserState
        updateCategoryEditDeleteBar();

        // initialize searchbar
        initializeSearchbar();

        // update MenuButton sort with newest arraylists<Task>
        updateSortingOptions();
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
            if(UserStateService.getCurrentUser().getCurrentlySelectedProject().equals(project)){
                loadProjectCategoryButtons(project, projectMenuButtonController);
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
        if(!UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory().isEmpty()){
            projectName.setText(UserStateService.getCurrentUser().getCurrentlySelectedProject());
            projectHBox.setVisible(true);
            projectHBox.setManaged(true);

            categoryName.setText(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory());
            categoryHBox.setVisible(true);
            categoryHBox.setManaged(true);
        } else if(!UserStateService.getCurrentUser().getCurrentlySelectedCategory().isEmpty()){
            categoryName.setText(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
            categoryHBox.setVisible(true);
            categoryHBox.setManaged(true);
            taskHBox.setVisible(true);

            projectHBox.setVisible(false);
            projectHBox.setManaged(false);

            // If any of the premade categories is selected, we wont show edit/delete button
            if(CategoryService.getPremadeCategories().contains(UserStateService.getCurrentUser().getCurrentlySelectedCategory())){
                buttonEditCategory.setVisible(false);
                buttonDeleteCategory.setVisible(false);
            } else {
                buttonEditCategory.setVisible(true);
                buttonDeleteCategory.setVisible(true);
            }
        } else if(!UserStateService.getCurrentUser().getCurrentlySelectedProject().isEmpty() && UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory().isEmpty()){
            projectName.setText(UserStateService.getCurrentUser().getCurrentlySelectedProject());
            projectHBox.setVisible(true);
            projectHBox.setManaged(true);
            categoryHBox.setVisible(false);
            categoryHBox.setManaged(false);
        } else {
            categoryHBox.setVisible(false);
            categoryHBox.setManaged(false);
            projectHBox.setVisible(false);
            projectHBox.setManaged(false);
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
        // we differentiate between when project category and ordinary category is selected
        if(UserStateService.getCurrentUser().getCurrentlySelectedCategory().isEmpty()){
            newEditCategoryController.intializeEditCategory(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory(), UserStateService.getCurrentUser().getCurrentlySelectedProject());
        } else {
            newEditCategoryController.intializeEditCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory(), UserStateService.getCurrentUser().getCurrentlySelectedProject());
        }

        // set dashboard content to editMenu
        setCenterContent(node);
    }

    /**
     * Delete currently visited category.
     * We differentiate between a normal category and a category under a project
     */
    public void buttonDeleteCategory() throws IOException {
        if(UserStateService.getCurrentUser().getCurrentlySelectedCategory().isEmpty()){
            CategoryService.deleteCategoryCurrentUser(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory(), UserStateService.getCurrentUser().getCurrentlySelectedProject());
        } else {
            CategoryService.deleteCategoryCurrentUser(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
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
        ProjectService.deleteProjectCurrentUser(UserStateService.getCurrentUser().getCurrentlySelectedProject());

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
        if(normalCategory.isEmpty() && !projectCategory.isEmpty()){
            sort.getItems().add(createSortingMenuItem("Priority", TaskService.getTasksSortedByPriority(TaskService.getTasksByCategory(projectCategory, project))));
            sort.getItems().add(createSortingMenuItem("Date", TaskService.getTasksSortedByDate(TaskService.getTasksByCategory(projectCategory, project))));
            sort.getItems().add(createSortingMenuItem("Alphabetically", TaskService.getTasksSortedAlphabetically(TaskService.getTasksByCategory(projectCategory, project))));
        } else if(!normalCategory.isEmpty()) {
            sort.getItems().add(createSortingMenuItem("Priority", TaskService.getTasksSortedByPriority(TaskService.getTasksByCategory(normalCategory))));
            sort.getItems().add(createSortingMenuItem("Date", TaskService.getTasksSortedByDate(TaskService.getTasksByCategory(normalCategory))));
            sort.getItems().add(createSortingMenuItem("Alphabetically", TaskService.getTasksSortedAlphabetically(TaskService.getTasksByCategory(normalCategory))));
        }
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
    public void loadTasksPage(ArrayList<Task> taskArrayList) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
        BorderPane borderPane = loader.load();
        TasksController tasksController = loader.getController();

        ArrayList<Task> tasks = new ArrayList<>();
        if(taskArrayList != null){
            tasks = taskArrayList;
        } else if(!projectCategory.isEmpty()) {
            tasks = TaskService.getTasksByCategory(projectCategory, project);
        } else if(!normalCategory.isEmpty()){
            tasks = TaskService.getTasksByCategory(normalCategory);
        }

        if(tasks == null || tasks.contains(null) || tasks.isEmpty()){
            tasksController.tasksIsEmpty();
        } else {
            // add tasks to generated taskspage
            tasksController.addTasks(tasks);

            // show no message when loaded tasks are not equals 0
            tasksController.showMessage(null);
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
                loadTasksPage(TaskService.containsDesiredNameInTitle(newValue));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }
}
