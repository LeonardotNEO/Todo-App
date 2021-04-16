package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.ColorUtil;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class which contains the buttons related to a singular task
 */
public class TaskController {

    private boolean fullDisplayed;
    private long taskId;
    @FXML private Text taskName;
    @FXML private Text taskDescription;
    @FXML private Text category;
    @FXML private Text startdate;
    @FXML private Text duedate;
    @FXML private Text taskLocation;
    @FXML private Text color;
    @FXML private Text notification1hour;
    @FXML private Text notification24hours;
    @FXML private Text notification7days;
    @FXML private Text tags;
    @FXML private Label taskDate;
    @FXML private Label taskPriority;
    @FXML private Label taskRepeat;
    @FXML private Pane background;
    @FXML private HBox toolsHBox;
    @FXML private AnchorPane spacer;


    /**
     * At initializing of this UI, we display the minimized version
     */

    public void initialize(){
        displayMinimizedTask();
        addClickTaskListener();
    }

    /**
     * Method for displaying this task UI
     * @param task
     */
    public void display(Task task){
        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
        category.setText("Category: " + task.getCategory());
        startdate.setText("Start date: " + DateUtils.getFormattedFullDate(task.getStartDate()));
        duedate.setText("Due date: " + DateUtils.getFormattedFullDate(task.getDeadline()));
        taskLocation.setText("Location: " + task.getLocation());
        color.setText("Color: " + task.getColor());
        notification1hour.setText("Notification 1 hour before duedate: " + task.isNotification1Hour());
        notification24hours.setText("Notification 24 hours before duedate: " + task.isNotification24Hours());
        notification24hours.setText("Notification 7 days before duedate: " + task.isNotification7Days());
        setRepeatTime(TaskService.convertTimeRepeatToString(task));
        // tags
        String tagsString = "";
        ArrayList<String> tagsList = task.getTags();
        if(tagsList!=null) { // added this because tagList was null, null pointer error
            for (String tag : tagsList) {
                tagsString += tag + ", ";
            }
        }
        tags.setText("Tags: " + tagsString);
        taskDate.setText("This task is due: " + DateUtils.getFormattedFullDate(task.getDeadline()));
        setTaskPriority(task.getPriority());
        taskId = task.getId();
        setTaskColor(task.getColor());
    }

    /**
     * Method for displaying this task with minimized UI
     */
    public void displayMinimizedTask(){
        category.setVisible(false);
        category.setManaged(false);
        startdate.setVisible(false);
        startdate.setManaged(false);
        duedate.setVisible(false);
        duedate.setManaged(false);
        taskLocation.setVisible(false);
        taskLocation.setManaged(false);
        color.setVisible(false);
        color.setManaged(false);
        notification1hour.setVisible(false);
        notification1hour.setManaged(false);
        notification24hours.setVisible(false);
        notification24hours.setManaged(false);
        notification7days.setVisible(false);
        notification7days.setManaged(false);
        tags.setVisible(false);
        tags.setManaged(false);
        spacer.setVisible(false);
        spacer.setManaged(false);

        fullDisplayed = false;
    }

    /**
     * Method for displaying this task with full UI
     */
    public void displayFullTask(){
        category.setVisible(true);
        category.setManaged(true);
        startdate.setVisible(true);
        startdate.setManaged(true);
        duedate.setVisible(true);
        duedate.setManaged(true);
        taskLocation.setVisible(true);
        taskLocation.setManaged(true);
        color.setVisible(true);
        color.setManaged(true);
        notification1hour.setVisible(true);
        notification1hour.setManaged(true);
        notification24hours.setVisible(true);
        notification24hours.setManaged(true);
        notification7days.setVisible(true);
        notification7days.setManaged(true);
        tags.setVisible(true);
        tags.setManaged(true);
        spacer.setVisible(true);
        spacer.setManaged(true);

        fullDisplayed = true;
    }

    /**
     * Method for alternating between displaying full and minimized task ui
     */
    public void clickTask(){
        if(fullDisplayed){
            displayMinimizedTask();
        } else {
            displayFullTask();
        }
    }

    /**
     * When finishTaskButton is clicked, task is moved to finished tasks folder
     * @param event
     * @throws IOException
     */
    public void buttonFinishTask(ActionEvent event) throws IOException{
        if(TaskService.getTaskByCurrentUser(taskId).isRepeatable()){
            TaskService.nextRepeatableTask(taskId);
        }
        TaskService.editCategoryOfTask(TaskService.getTaskByCurrentUser(taskId), "Finished tasks");
        DashboardController.getInstance().initialize();
    }

    /**
     * Get the id of this task (from tasks AnchorPane), then we delete the task with this id with TaskService
     * @throws IOException
     */
    public void buttonDeleteTask(ActionEvent event) throws IOException {
        if (UserStateService.getCurrentUser().isDeleteTaskDontShowAgainCheckbox()) {
            if(TaskService.getTaskByCurrentUser(taskId).isRepeatable()){
                TaskService.nextRepeatableTask(taskId);
            }
            // update category of task to trash bin
            TaskService.editCategoryOfTask(TaskService.getTaskByCurrentUser(taskId), "Trash bin");
            // update dashboard
            DashboardController.getInstance().initialize();
        } else {
            ConfirmationController.display(this);
        }
    }

    /**
     * Updates center-content of dashboard to editTask.fxml and adds prompt attributes from task selected
     * @param event
     * @throws IOException
     */
    public void buttonEditTask(ActionEvent event) throws IOException{
        editTask();
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

    public void addClickTaskListener(){
        background.setOnMouseClicked(mouseEvent -> {
            clickTask();
        });
    }

    public void editTask() throws IOException {
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
}
