package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

public class EditCategoryController {

    @FXML private TextField titleTextField;

    /**
     * Cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelEditCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().loadTasksPage(TaskService.getCategoryWithTasks(UserStateService.getCurrentUserCategory()));
    }

    public void buttonEditCategory(ActionEvent event) throws IOException {
        // Make new category
        CategoryService.addCategoryToCurrentUser(titleTextField.getText());

        // Move tasks in old category to new category
        TaskService.editCategoryOfTasks(TaskService.getTasksByCategory(UserStateService.getCurrentUserCategory()), titleTextField.getText());

        // Delete old category
        CategoryService.deleteCategoryCurrentUser(UserStateService.getCurrentUserCategory());

        // Set current category to new one
        UserStateService.setCurrentUserCategory(titleTextField.getText());

        // Load dashboard into maincontent
        DashboardController.getInstance().initialize();
    }
}
