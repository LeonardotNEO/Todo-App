package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;
import ntnu.idatt1002.dao.UserStateDAO;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;
import java.util.ArrayList;

public class TasksController {

    @FXML private VBox tasksVBox;

    /**
     *
     * @param taskObject to create the task page thats added to tasks, we need to taskObject with values
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
        taskController.setTaskDate(taskObject.getDeadline());
        taskController.setTaskPriority(taskObject.getPriority());

        // adding the task to tasks
        tasksVBox.getChildren().add(task);
    }

    public void addTasks(ArrayList<Task> tasks){
        tasks.forEach(t -> {
            try {
                addTask(t);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
