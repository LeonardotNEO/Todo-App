package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

public class NewCategoryController {

    @FXML private TextField titleTextField;
    @FXML private Label errorMessage;

    /**
     * Cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelNewCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().initialize();
    }

    /**
     * Check if title is good, then add category, set category and load dashboard
     * @param event
     * @throws IOException
     */
    public void buttonNewCategory(ActionEvent event) throws IOException {
        String categoryTitle = titleTextField.getText();

        if(CategoryService.validateCategoryTitleSyntax(categoryTitle)){
            CategoryService.addCategoryToCurrentUser(categoryTitle);
            UserStateService.setCurrentUserCategory(categoryTitle);
            DashboardController.getInstance().initialize();
        } else {
            errorMessage.setText("Title need to be between 0 and 24 characters");
        }
    }
}
