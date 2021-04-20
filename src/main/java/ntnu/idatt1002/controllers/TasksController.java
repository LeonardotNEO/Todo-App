package ntnu.idatt1002.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class which contains the methods related to the task page
 */
public class TasksController {

    @FXML private VBox tasksVBox;
    @FXML private ScrollPane scrollpane;
    @FXML private Button buttonAddTask;
    @FXML private Text messageText;
    @FXML private VBox background;

    public void initialize(){
        // make vbox inside scrollpanes resizeable
        addScrollpaneListener();

        // decide if we show the addTaskButton
        showAddTaskButton();

        // set background
        background.setStyle(UserStateService.getCurrentUser().getCurrentlySelectedBackground());
    }

    /**
     * Method for adding a Task UI element to tasksVBox
     * @param taskObject A task object is turned into a UI element
     * @throws IOException
     */
    public void addTask(ntnu.idatt1002.Task taskObject) throws IOException {
        // Loads task page and get the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"));
        AnchorPane task = loader.load();
        TaskController taskController = loader.getController();

        // add id to task anchorpane. A task is identified in TaskDAO as the taskobject's hashcode
        task.setId(Long.toString(taskObject.getId()));

        // use controller to display task
        taskController.display(taskObject);

        // adding the task to tasks
        tasksVBox.getChildren().add(tasksVBox.getChildren().size(), task);
    }

    public void buttonAddTask(){
        // Load newEditTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newEditTask.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NewEditTaskController newEditTaskController = loader.getController();

        // load the task part of newEditTaskController
        newEditTaskController.initializeNewTask();

        // set dashboard content to editMenu
        try {
            DashboardController.getInstance().setCenterContent(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddTaskButton(){
        // set button to hidden as default
        buttonAddTask.setVisible(false);
        buttonAddTask.setManaged(false);

        if(!UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory().isEmpty()){
            buttonAddTask.setVisible(true);
            buttonAddTask.setManaged(true);
        }
        if(!UserStateService.getCurrentUser().getCurrentlySelectedCategory().isEmpty() && !CategoryService.getPremadeCategories().contains(UserStateService.getCurrentUser().getCurrentlySelectedCategory())){
            buttonAddTask.setVisible(true);
            buttonAddTask.setManaged(true);
        }

    }

    /**
     * Uses helper-method addTask to add an arraylist of tasks
     * @param tasks
     */
    public void addTasks(ArrayList<Task> tasks){
        if(tasks != null){
            tasks.forEach(t -> {
                try {
                    addTask(t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Method for setting content of tasks to empty.
     * If currently selected category is not set to null, we show some text.
     * If currently selected category is set to null, we show guide to add new category.
     */
    public void tasksIsEmpty(){
        if(UserStateService.getCurrentUser().getCurrentlySelectedCategory() != null) {
            switch (UserStateService.getCurrentUser().getCurrentlySelectedCategory()){
                case "Trash bin":
                    showMessage("There are no tasks in trash bin!");
                    break;
                case "Finished tasks":
                    showMessage("There are no finished tasks!");
                    break;
                case "All tasks":
                    showMessage("You have no tasks!");
                    break;
                default:
                    showMessage(null);
                    break;
            }
        } else {
            showMessage("No category is created yet...\nYou need to add a category first, before adding an task!");
        }
    }

    /**
     * Method for displaying UI in tasks when we are using searchbar
     */
    public void tasksIsEmptySearch(){
        showMessage("No task matching your search!");
    }

    /**
     * Adds a listener to scrollpane that makes the width of tasksVBox change when scrollpane size changes
     */
    public void addScrollpaneListener(){
        scrollpane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                tasksVBox.setPrefWidth(scrollpane.getWidth() - 15);
            }
        });
    }

    public void showMessage(String message){
        if(message == null){
            messageText.setVisible(false);
            messageText.setManaged(false);
        } else {
            messageText.setText(message);
        }
    }
}
