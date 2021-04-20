package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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

import java.awt.*;
import java.io.File;
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
    @FXML private Text project;
    @FXML private Text category;
    @FXML private Text startdate;
    @FXML private Text duedate;
    @FXML private Text taskLocation;
    @FXML private Text color;
    @FXML private Text notification;
    @FXML private Text tags;
    @FXML private Text attachedFiles;
    @FXML private Label taskDate;
    @FXML private Label taskPriority;
    @FXML private Text taskRepeat;
    @FXML private Pane background;
    @FXML private HBox toolsHBox;
    @FXML private FlowPane flowPaneForFiles;

    /**
     * At initializing of this UI, we display the minimized version
     */
    public void initialize(){
        addClickTaskListener();
        displayMinimizedTask();
    }

    /**
     * A method to check if a task has a notification checked.
     * @param task the task to check.
     * @return On if a notification is checked, Off is no notification is checked.
     */
    public String checkNotification(Task task) {
        String notificationString = "";

        if (task.isNotification1Hour()) {
            notificationString += "Notification 1 hour before duedate: yes\n";
        }
        if(task.isNotification24Hours()){
            notificationString += "Notification 24 hours before duedate: yes\n";
        }
        if(task.isNotification7Days()){
            notificationString += "Notification 7 days before duedate: yes\n";
        }
        if(notificationString.isEmpty()){
            notificationString += "No notifications";
        }

        return notificationString;
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
        notification.setText(checkNotification(task));
        // tags
        String tagsString = "";
        ArrayList<String> tagsList = task.getTags();
        if(tagsList!=null) { // null pointer exception when tagsList equals null
            for (String tag : tagsList) {
                tagsString += tag + ", ";
            }
        }
        // files
        ArrayList<String> filesList = task.getFilePaths();
        if(filesList!=null) { // null pointer exception when file list equals null
            for (String file : filesList) {

                //Using regex to split up the filepath-string to the last element, (the file name and type)
                String[] fileName = file.split("\\\\");
                Hyperlink clickFile = new Hyperlink(fileName[fileName.length-1]);

                clickFile.setOnAction(event -> {
                    try {
                        File open = new File(file);

                        //Using the desktop library to open a file with the desktop
                        if (!Desktop.isDesktopSupported()) {

                        }
                        Desktop desktop = Desktop.getDesktop();
                        if(open.exists()) {
                            desktop.open(open);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

                //                Button clickFile = new Button(fileName[fileName.length -1]);
                //
                //                //Specifies what the clickFile-button will do
                //                clickFile.setOnAction(event -> {
                //                    try {
                //                        File open = new File(file);
                //
                //                        //Using the desktop library to open a file with the desktop
                //                        if (!Desktop.isDesktopSupported()) {
                //
                //                        }
                //                        Desktop desktop = Desktop.getDesktop();
                //                        if(open.exists()) {
                //                            desktop.open(open);
                //                        }
                //                    } catch (Exception exception) {
                //                        exception.printStackTrace();
                //                    }
                //                });

                //Adds the button to the vbox
                flowPaneForFiles.getChildren().add(clickFile);
            }
        }
        tags.setText("Tags: " + tagsString);
        attachedFiles.setText(("Attached files: "));
        taskDate.setText("This task is due: " + DateUtils.getFormattedFullDate(task.getDeadline()));
        setTaskPriority(task.getPriority());
        taskId = task.getId();
        setTaskColor(task.getColor());
        taskRepeat.setText("Task repeat: ");
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
        notification.setVisible(false);
        notification.setManaged(false);
        tags.setVisible(false);
        tags.setManaged(false);
        attachedFiles.setVisible(false);
        attachedFiles.setManaged(false);
        taskRepeat.setVisible(false);
        taskRepeat.setManaged(false);
        flowPaneForFiles.setVisible(false);
        flowPaneForFiles.setManaged(false);

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
        notification.setVisible(true);
        notification.setManaged(true);
        tags.setVisible(true);
        tags.setManaged(true);
        attachedFiles.setVisible(true);
        attachedFiles.setManaged(true);
        taskRepeat.setVisible(true);
        taskRepeat.setManaged(true);
        flowPaneForFiles.setVisible(true);
        flowPaneForFiles.setManaged(true);

        fullDisplayed = true;
    }

    /**
     * Method for alternating between displaying full and minimized task ui
     */
    public void clickTask() {
        if (fullDisplayed) {
            displayMinimizedTask();
        } else {
            displayFullTask();
        }
    }

    /**
     * Checks for confirmation popup settings. Calls this.finishTask() if true.
     * Displays confirmation popup if false.
     * @param event
     * @throws IOException
     */
    public void clickFinishTask(ActionEvent event) throws IOException {
        if (UserStateService.getCurrentUser().isFinishTaskDontShowAgainCheckbox()) {
            this.finishTask(event);
        } else {
            ConfirmationController.display(this, "finish");
        }
    }

    /**
     * Moves task to 'Finished tasks' folder.
     * Creates new repeatable task if task is repeatable.
     * @param event
     * @throws IOException
     */
    public void finishTask(ActionEvent event) throws IOException{
        if(TaskService.getTaskByCurrentUser(taskId).isRepeatable()){
            TaskService.nextRepeatableTask(taskId);
        }
        // update category of task to 'Finished tasks'
        TaskService.editCategoryOfTask(TaskService.getTaskByCurrentUser(taskId), "Finished tasks");
        // update dashboard
        DashboardController.getInstance().initialize();
    }

    /**
     * Checks for confirmation popup settings. Calls this.deleteTask() if true.
     * Displays confirmation popup if false.
     * @param event
     * @throws IOException
     */
    public void clickDeleteButton(ActionEvent event) throws IOException {
        if (UserStateService.getCurrentUser().isDeleteTaskDontShowAgainCheckbox()) {
            deleteTask(event);
        } else {
            ConfirmationController.display(this, "delete");
        }
    }

    /**
     * Moves task to 'Trash bin' folder.
     * Creates new repeatable task if task is repeatable.
     * @param event
     * @throws IOException
     */
    public void deleteTask(ActionEvent event) throws IOException {
        if(TaskService.getTaskByCurrentUser(taskId).isRepeatable()){
            TaskService.nextRepeatableTask(taskId);
        }
        // update category of task to 'Trash bin'
        TaskService.editCategoryOfTask(TaskService.getTaskByCurrentUser(taskId), "Trash bin");
        // update dashboard
        DashboardController.getInstance().initialize();
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
            taskDescription.setTextFill(Paint.valueOf("white"));
            taskDate.setTextFill(Paint.valueOf("white"));
            taskPriority.setTextFill(Paint.valueOf("white"));
            taskRepeat.setFill(Paint.valueOf("white"));
            taskName.setFill(Paint.valueOf("white"));
            toolsHBox.setStyle("-fx-background-color: #f7f7f7; -fx-background-radius:  0 15 0 15;");
        } else {
            taskDescription.setTextFill(Paint.valueOf("black"));
            taskDate.setTextFill(Paint.valueOf("black"));
            taskPriority.setTextFill(Paint.valueOf("black"));
            taskRepeat.setFill(Paint.valueOf("black"));
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
