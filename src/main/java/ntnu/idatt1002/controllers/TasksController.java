package ntnu.idatt1002.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TasksController {

    @FXML private VBox tasksVBox;

    public void initialize() throws IOException {
        // this initalize method should show tasks by currently selected category
        // get taskList from dao and loop through each taskObject and send it to addTask()
    }

    /**
     *
     * @param taskObject to create the task page thats added to tasks, we need to taskObject with values
     * @throws IOException
     */
    public void addTask(Task taskObject) throws IOException {
        // Loads task page and get the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"));
        AnchorPane task = loader.load();
        TaskController taskController = loader.getController();

        // use controller to change content of task before adding to to tasks
        taskController.setTaskName("example text");
        taskController.setTaskDescription("example text 2");

        // adding the task to tasks
        tasksVBox.getChildren().add(task);
    }
}
