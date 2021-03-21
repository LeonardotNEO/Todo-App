package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;

import java.io.IOException;

public class TaskController {

    private int taskId;
    @FXML private Text taskName;
    @FXML private Text taskDescription;
    @FXML private Label taskDate;
    @FXML private Label taskPriority;

    /**
     * Get the id of this task (from tasks anchorpane), then we delete the task with this id with TaskService
     * @param event
     * @throws IOException
     */
    public void deleteTask(ActionEvent event) throws IOException {
        // this task object
        Task task = TaskService.getTaskByCurrentUser(taskId);

        TaskService.deleteTask(task);

        // loads a tasks-page with this users tasks into dashboard
        DashboardController.getInstance().loadTasksPage(TaskService.getTasksByCurrentUser());
    }

    /**
     * updates center-content of dashboard to editTask.fxml and adds prompt attributes
     * @param event
     * @throws IOException
     */
    public void editTask(ActionEvent event) throws IOException{
        // this tasks id
        int id = Integer.parseInt(taskName.getParent().getParent().getId());

        // this task object
        Task task = TaskService.getTaskByCurrentUser(id);

        // Load editTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editTask.fxml"));
        AnchorPane editMenu = loader.load();
        EditTaskController editTaskController = loader.getController();

        // set id of editmenu Anchorpane, so we can fetch id when editing
        editTaskController.setId(id);

        // set title prompt
        editTaskController.setTitleTextField(task.getName());

        // set description prompt
        editTaskController.setDescriptionTextArea(task.getDescription());

        // set categories in menuButton
        editTaskController.setCategoryMenu(TaskService.getCategoryNames());

        // set datepicker prompt
        editTaskController.setDatePicker(task.getDeadline());

        // set dashboard content to editMenu
        DashboardController.getInstance().setCenterContent(editMenu);
    }

    public void setTaskName(String name){
        taskName.setText(name);
    }

    public void setTaskDescription(String description){
        taskDescription.setText(description);
    }

    public void setTaskDate(String date){
        taskDate.setText("This task is due: " + date);
    }

    public void setTaskPriority(int priority) {
        taskPriority.setText("Priority: " + priority);
    }

    public void setTaskId(int id){
        this.taskId = id;
    }
}
