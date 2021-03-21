package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ntnu.idatt1002.service.TaskService;

import java.io.IOException;

public class NewCategoryController {

    @FXML private TextField titleTextField;

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelNewCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().loadTasksPage(TaskService.getTasksByCurrentUser());
    }


    public void buttonNewCategory(ActionEvent event) throws IOException {
        boolean newCategory = true; // communicates with dao. Method for adding new category. Parameters are titleTextField.getText();

        if(newCategory){
            DashboardController.getInstance().setCenterContent("tasks");
        } else {
            // errormessage
        }
    }
}
