package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

public class TaskController {

    private int taskId;
    @FXML private Text taskName;
    @FXML private Text taskDescription;
    @FXML private Label taskDate;
    @FXML private Label taskPriority;

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
     * Updates center-content of dashboard to editTask.fxml and adds prompt attributes
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

        // set id of editMenu AnchorPane, so we can fetch id when editing
        editTaskController.setId(id);

        // set title prompt
        editTaskController.setTitleTextField(task.getName());

        // set description prompt
        editTaskController.setDescriptionTextArea(task.getDescription());

        // set categories in menuButton
        editTaskController.setCategoryMenu(CategoryService.getCategoriesCurrentUser());

        // set category prompt
        editTaskController.setCategoryMenu(task.getCategory());

        // set datepicker prompt
        editTaskController.setDatePicker(TaskService.transformDeadline(task.getDeadline()));

        // set priority prompt
        editTaskController.setPriorityMenu(Integer.toString(task.getPriority()));

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

    public void setTaskId(int id){
        this.taskId = id;
    }
}
