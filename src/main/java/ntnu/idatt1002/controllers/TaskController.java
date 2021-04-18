package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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
    @FXML private Label taskDescription;
    private double taskDescriptionHeight;
    @FXML private Text project;
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
    @FXML private Pane background;
    @FXML private HBox toolsHBox;


    /**
     * At initializing of this UI, we display the minimized version
     */
    public void initialize(){
        addClickTaskListener();
        displayMinimizedTask();
        // We set the height for taskDescription to its initial value (the height when Task UI is loaded), then we save this value to use it later for when we maximize task view.
        // But after we have set the height value for opening the task maximized in the future, we can then display the minimized task.
        /*taskDescription.heightProperty().addListener((ob, oldValue, newValue) -> {
            taskDescriptionHeight = newValue.doubleValue();
            displayMinimizedTask();
        });*/

    }

    /**
     * Method for displaying this task UI
     * @param task
     */
    public void display(Task task){
        taskDescription.setText(task.getDescription());
        taskName.setText(task.getName());
        category.setText("Category: " + task.getCategory());
        project.setText("Project: " + task.getProject());
        startdate.setText("Start date: " + DateUtils.getFormattedFullDate(task.getStartDate()));
        duedate.setText("Due date: " + DateUtils.getFormattedFullDate(task.getDeadline()));
        taskLocation.setText("Location: " + task.getLocation());
        color.setText("Color: " + task.getColor());

        String yesNo;
        if(task.isNotification1Hour()){
            yesNo = "Yes";
        } else {
            yesNo = "No";
        }
        notification1hour.setText("Notification 1 hour: " + yesNo);

        if(task.isNotification24Hours()){
            yesNo = "Yes";
        } else {
            yesNo = "No";
        }
        notification24hours.setText("Notification 24 hours: " + yesNo);

        if(task.isNotification7Days()){
            yesNo = "Yes";
        } else {
            yesNo = "No";
        }
        notification7days.setText("Notification 7 days: " + yesNo);
        // tags
        String tagsString = "";
        ArrayList<String> tagsList = task.getTags();
        for(String tag : tagsList){
            tagsString += tag + ", ";
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
        taskDescription.setPrefHeight(50);
        project.setVisible(false);
        project.setManaged(false);
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

        fullDisplayed = false;
    }

    /**
     * Method for displaying this task with full UI
     */
    public void displayFullTask(){
        setHeightOfTaskDescription();
        project.setVisible(true);
        project.setManaged(true);
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
        TaskService.editCategoryOfTask(TaskService.getTaskByCurrentUser(taskId), "Finished tasks");
        DashboardController.getInstance().initialize();
    }

    /**
     * Get the id of this task (from tasks AnchorPane), then we delete the task with this id with TaskService
     * @throws IOException
     */
    public void buttonDeleteTask(ActionEvent event) throws IOException {
        if (UserStateService.getCurrentUser().isDeleteTaskDontShowAgainCheckbox()) {
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
     * @param priority
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

    public void setTaskColor(String backgroundColor){
        background.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius:  5 20 5 5;");

        if(ColorUtil.isVisibilityRatingOverThreshold(backgroundColor)){
            taskDescription.setTextFill(Paint.valueOf("white"));
            taskDate.setTextFill(Paint.valueOf("white"));
            taskPriority.setTextFill(Paint.valueOf("white"));
            taskName.setFill(Paint.valueOf("white"));
            toolsHBox.setStyle("-fx-background-color: #f7f7f7; -fx-background-radius:  0 15 0 15;");
        } else {
            taskDescription.setTextFill(Paint.valueOf("black"));
            taskDate.setTextFill(Paint.valueOf("black"));
            taskPriority.setTextFill(Paint.valueOf("black"));
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

    /**
     * In order to get the proper height for the description when we load the maximized view, we recreate the label and put font size and wrapping.
     * Then we set the width of the label in order to simulate what height the label will end up width after wrapping around that width (Background of Task UI is the width).
     * We have to add a listener to the label, in order to execute the code when the height property has been fully set. When that is done we can set the height of our
     * taskDescription label. We delete the sample label since we dont need it anymore.
     */
    public void setHeightOfTaskDescription(){
        Label label = new Label(taskDescription.getText());
        label.setWrapText(true);
        background.getChildren().add(label);
        label.setPrefWidth(background.getWidth());
        label.setFont(new Font(16));
        label.heightProperty().addListener((obj, oldValue, newValue) -> {
            taskDescription.setPrefHeight(label.getHeight());
            background.getChildren().removeAll(label);
        });
    }

}
