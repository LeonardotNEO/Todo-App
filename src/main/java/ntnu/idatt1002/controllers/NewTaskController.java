package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;

import java.io.IOException;

public class NewTaskController {

    @FXML private TextField titleTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private MenuButton categoryMenu;
    @FXML private DatePicker datePicker;

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelNewTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void buttonNewTask(ActionEvent event) throws  IOException {
        boolean addTaskSuccessful = TaskService.newTask(
                titleTextField.getText(),
                datePicker.getAccessibleText(),
                descriptionTextArea.getText(),
                1,
                null,
                categoryMenu.getText()
        ); // method that communicates with DAO to att new task (parameters are FXML parameters). If successful the method return true

        if(addTaskSuccessful){
            TaskService.getCategoriesWithTasks();
            DashboardController.getInstance().setCenterContent("tasks"); // redirects back to tasks if successful
        } else {
            //errormessage to textfield?
        }

    }
}
