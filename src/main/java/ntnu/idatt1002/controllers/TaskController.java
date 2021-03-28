package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateConverter;

import java.io.IOException;

/**
 * A class which contains the buttons related to a singular task
 */
public class TaskController {

    private int taskId;
    @FXML private Text taskName;
    @FXML private Text taskDescription;
    @FXML private Label taskDate;
    @FXML private Label taskPriority;

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
     * @param event
     * @throws IOException
     */
    public void deleteTask(ActionEvent event) throws IOException {
        // this task object
        Task task = TaskService.getTaskByCurrentUser(taskId);

        TaskService.deleteTask(task);

        // loads a tasks-page with this users tasks into dashboard
        DashboardController.getInstance().loadTasksPage(TaskService.getCategoryWithTasks(UserStateService.getCurrentUserCategory()));
    }

    /**
     * Updates center-content of dashboard to editTask.fxml and adds prompt attributes from task selected
     * @param event
     * @throws IOException
     */
    public void editTask(ActionEvent event) throws IOException{
        // Load editTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editTask.fxml"));
        Node editMenu = loader.load();
        EditTaskController editTaskController = loader.getController();

        // set id of editMenu AnchorPane, so we can fetch id when editing
        editTaskController.setId(taskId);

        // this task object
        Task task = TaskService.getTaskByCurrentUser(taskId);

        // set title prompt
        editTaskController.setTitleTextField(task.getName());
        // set description prompt
        editTaskController.setDescriptionTextArea(task.getDescription());
        // set location prompt
        editTaskController.setLocation(task.getLocation());
        // set categories in menuButton
        editTaskController.setCategoryMenu(CategoryService.getCategoriesCurrentUserWithoutPremades());
        // set category prompt
        editTaskController.setCategoryMenu(task.getCategory());
        // set datepicker prompt and DateConverter
        editTaskController.setDatePicker(TaskService.transformDeadline(task.getDeadline()));
        editTaskController.setDatePicker(new DateConverter());
        // set timePicker
        // Todo set timepicker
        editTaskController.setTimePicker24Hour(true);
        // set priority prompt
        editTaskController.setPriorityMenu(Integer.toString(task.getPriority()));
        // set notification boolean
        editTaskController.setNotification(task.getNotification());
        // set color
        editTaskController.setColor(task.getColor());
        // set tags
        editTaskController.setTags(task.getTags());

        // set dashboard content to editMenu
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

    /**
     * A method to set the id of the task
     * @param id
     */
    public void setTaskId(int id){
        this.taskId = id;
    }
}
