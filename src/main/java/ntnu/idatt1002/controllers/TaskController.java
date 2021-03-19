package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import ntnu.idatt1002.service.TaskService;

import java.io.IOException;

public class TaskController {

    @FXML private Text taskName;
    @FXML private Text taskDescription;

    public void setTaskName(String name){
        taskName.setText(name);
    }

    public void setTaskDescription(String description){
        taskDescription.setText(description);
    }

    /**
     * delete task from this user completely (or set task category to trashbin?)
     * @param event
     * @throws IOException
     */
    public void deleteTask(ActionEvent event) throws IOException {

    }

    /**
     * updates center-content of dashboard to editTask.fxml and add promp attributes
     * @param event
     * @throws IOException
     */
    public void editTask(ActionEvent event) throws IOException{
        // this tasks id
        int id = Integer.parseInt(taskName.getParent().getParent().getId());

        // set id of editmenu Anchorpane, so we can fetch id when editing
        AnchorPane editMenu = DashboardController.getInstance().setCenterContent("editTask");
        editMenu.setId(Integer.toString(id));

        // set title prompt
        TextField title = (TextField) editMenu.lookup("#titleTextField");
        title.setPromptText(TaskService.getTaskByCurrentUser(id).getName());

        // set description prompt
        TextArea description = (TextArea) editMenu.lookup("#descriptionTextArea");
        description.setPromptText(TaskService.getTaskByCurrentUser(id).getDescription());

        // set datepicker prompt
        DatePicker datePicker = (DatePicker) editMenu.lookup("#datePicker");
        datePicker.setPromptText(TaskService.getTaskByCurrentUser(id).getDeadline());

    }
}
