package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.ColorUtil;
import ntnu.idatt1002.utils.DateConverter;
import ntnu.idatt1002.utils.DateUtils;
import ntnu.idatt1002.utils.TimeConverter;

import java.io.IOException;
import java.time.LocalTime;

/**
 * A class which contains the buttons related to a singular task
 */
public class TaskController {

    private long taskId;
    @FXML private Text taskName;
    @FXML private Text taskDescription;
    @FXML private Label taskDate;
    @FXML private Label taskPriority;
    @FXML private Label taskRepeat;
    @FXML private Pane background;
    @FXML private HBox toolsHBox;

    /**
     * When finishTaskButton is clicked, task is moved to finished tasks folder
     * @param event
     * @throws IOException
     */
    public void buttonFinishTask(ActionEvent event) throws IOException{
        TaskService.nextRepeatableTask(taskId);

        TaskService.editCategoryOfTask(TaskService.getTaskByCurrentUser(taskId), "Finished tasks");

        DashboardController.getInstance().initialize();
    }

    /**
     * Get the id of this task (from tasks AnchorPane), then we delete the task with this id with TaskService
     * @param event
     * @throws IOException
     */
    public void deleteTask(ActionEvent event) throws IOException {
        // creates a new task, if it is repeatable
        TaskService.nextRepeatableTask(taskId);

        // update category of task to trash bin
        TaskService.editCategoryOfTask(TaskService.getTaskByCurrentUser(taskId), "Trash bin");

        // update dashboard
        DashboardController.getInstance().initialize();
    }

    /**
     * Updates center-content of dashboard to editTask.fxml and adds prompt attributes from task selected
     * @param event
     * @throws IOException
     */
    public void editTask(ActionEvent event) throws IOException{
        // Load editTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newEditTask.fxml"));
        Node editMenu = loader.load();
        NewEditTaskController newEditTaskController = loader.getController();

        // load the task part of newEditTaskController
        newEditTaskController.initializeEditTask(TaskService.getTaskByCurrentUser(taskId));

        // set mainControllers maincontent to dashboard and set dashboard to editpage
        MainController.getInstance().setMainContent("dashboard");
        DashboardController.getInstance().setCenterContent(editMenu);
    }

    /**
     * A method to set the name of the task
     * @param name
     */
    public void setTaskName(String name){
        taskName.setText(name);
    }

    /**
     * A method to set the description for the task
     * @param description
     */
    public void setTaskDescription(String description){
        taskDescription.setText(description);
    }

    /**
     * A method to set the due date for the task
     * @param date
     */
    public void setTaskDate(String date){
        taskDate.setText("This task is due: " + date);
    }

    /**
     * A method to set the priority of the task
     * @param priority The priority of task
     */
    public void setTaskPriority(int priority) {
        taskPriority.setText("Priority: " + priority);

        switch(priority){
            case 0:
                taskPriority.setTextFill(Paint.valueOf("white"));
                break;
            case 1:
                taskPriority.setTextFill(Paint.valueOf("yellow"));
                break;
            case 2:
                taskPriority.setTextFill(Paint.valueOf("orange"));
                break;
            case 3:
                taskPriority.setTextFill(Paint.valueOf("red"));
                break;
            default:
        }

    }

    public void setRepeatTime(String repeatTime){
        if(repeatTime==null){
            repeatTime = "";
        }
        switch(repeatTime){
            case "Repeat Daily":
                taskRepeat.setText("Daily");
                break;
            case "Repeat Weekly":
                taskRepeat.setText("Weekly");
                break;
            default:
                taskRepeat.setText("");
                break;
        }
    }

    public void setTaskId(long id){
        this.taskId = id;
    }

    public void setTaskColor(String backgroundColor){
        background.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius:  5 20 5 5;");

        if(ColorUtil.isVisibilityRatingOverThreshold(backgroundColor)){
            taskDescription.setFill(Paint.valueOf("white"));
            taskDate.setTextFill(Paint.valueOf("white"));
            taskPriority.setTextFill(Paint.valueOf("white"));
            taskRepeat.setTextFill(Paint.valueOf("white"));
            taskName.setFill(Paint.valueOf("white"));
            toolsHBox.setStyle("-fx-background-color: #f7f7f7; -fx-background-radius:  0 15 0 15;");
        } else {
            taskDescription.setFill(Paint.valueOf("black"));
            taskDate.setTextFill(Paint.valueOf("black"));
            taskPriority.setTextFill(Paint.valueOf("black"));
            taskRepeat.setTextFill(Paint.valueOf("black"));
            taskName.setFill(Paint.valueOf("black"));
            toolsHBox.setStyle("-fx-background-color: #f7f7f7; -fx-background-radius:  0 15 0 15;");
        }
    }
}
