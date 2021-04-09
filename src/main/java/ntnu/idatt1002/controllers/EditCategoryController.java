package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

/**
 * A class which contains the buttons related to editing a category
 */
public class EditCategoryController {

    @FXML private TextField titleTextField;
    @FXML private Label errorMessage;

    /**
     * Cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelEditCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().loadTasksPage(TaskService.getCategoryWithTasks(UserStateService.getCurrentUserCategory()));
    }

    /**
     * Edit category button allows one to edit a category which is already created, by recreating a new one with the
     * changes in its place
     * @param event
     * @throws IOException
     */
    public void buttonEditCategory(ActionEvent event) throws IOException {
        if(CategoryService.validateCategoryTitleSyntax(titleTextField.getText())){
            // Make new category
            CategoryService.addCategoryToCurrentUser(titleTextField.getText());

            // Move tasks in old category to new category
            TaskService.editCategoryOfTasks(TaskService.getTasksByCategory(UserStateService.getCurrentUserCategory()), titleTextField.getText());

            // Delete old category
            CategoryService.deleteCategoryCurrentUser(UserStateService.getCurrentUserCategory());

            // Set current category to new one
            UserStateService.setCurrentUserCategory(titleTextField.getText());

            // Load dashboard into mainContent
            DashboardController.getInstance().initialize();
        } else {
            errorMessage.setText("Title need to be between 0 and 24 characters");
        }

    }

    /**
     * Press edit button if enter is pressed
     */
    public void onKeyPressed(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            try {
                buttonEditCategory(new ActionEvent());
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
