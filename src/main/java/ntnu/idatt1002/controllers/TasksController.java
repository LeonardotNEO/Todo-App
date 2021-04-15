package ntnu.idatt1002.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
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


    /**
     * Method that runs when this controller is initialized
     */
    public void initialize(){
        addScrollpaneListener();
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
        task.setId(Integer.toString(taskObject.hashCode()));

        // use controller to change content of task before adding to to tasks
        taskController.setTaskName(taskObject.getName());
        taskController.setTaskDescription(taskObject.getDescription());
        taskController.setTaskDate(DateUtils.getFormattedFullDate(taskObject.getDeadline()));
        taskController.setTaskPriority(taskObject.getPriority());

        String storedTimeRepeat;
        if((taskObject.isRepeatable()) && (taskObject.getTimeRepeat().equals(1000*60*60*24L)))
        {storedTimeRepeat = "Repeats Daily";}
        else if((taskObject.isRepeatable()) && (taskObject.getTimeRepeat().equals(1000*60*60*24*7L)))
        {storedTimeRepeat = "Repeats Weekly";}
        else{storedTimeRepeat = "";}

        taskController.setRepeatTime(storedTimeRepeat);
        taskController.setTaskId(taskObject.getId());
        taskController.setTaskColor(taskObject.getColor());

        // adding the task to tasks
        tasksVBox.getChildren().add(tasksVBox.getChildren().size(), task);
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
        Text text = new Text();

        if(UserStateService.getCurrentUser().getCurrentlySelectedCategory() != null) {
            switch (UserStateService.getCurrentUser().getCurrentlySelectedCategory()){
                case "Trash bin":
                    text.setText("There are no tasks in trash bin!");
                    break;
                case "Finished tasks":
                    text.setText("There are no finished tasks!");
                    break;
                default:
                    text.setText("Theres no tasks in this category...\nClick on New-Task-button to add a new task!");
            }

            text.setStyle("-fx-font-size: 25; -fx-text-fill: white;");
            tasksVBox.getChildren().add(text);
        } else {
            text.setText("No category is created yet...\nYou need to add a category first, before adding an task!");
            text.setStyle("-fx-font-size: 25; -fx-text-fill: white;");

            tasksVBox.getChildren().add(text);
        }
    }

    /**
     * Method for displaying UI in tasks when we are using searchbar
     */
    public void tasksIsEmptySearch(){
        Text text = new Text();
        text.setText("No task matching your search!");
        text.setStyle("-fx-font-size: 25; -fx-text-fill: white;");
        tasksVBox.getChildren().add(text);
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
}
