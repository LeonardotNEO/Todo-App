package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

public class NewCategoryController {

    @FXML private TextField titleTextField;

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelNewCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().initialize();
    }


    public void buttonNewCategory(ActionEvent event) throws IOException {
        CategoryService.addCategoryToCurrentUser(titleTextField.getText());
        UserStateService.setCurrentUserCategory(titleTextField.getText());
        MainController.getInstance().setMainContent("dashboard");
    }
}
