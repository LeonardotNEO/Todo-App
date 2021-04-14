package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

/**
 * A class which contains the buttons related to the creation of a new category
 */
public class NewEditCategoryController {

    @FXML private TextField titleTextField;
    @FXML private Label errorMessage;
    @FXML private Text headerText;
    @FXML private Button button;

    public void intializeNewCategory(){
        headerText.setText("New category");

        button.setText("New category");
        button.setOnAction(event -> {
            try {
                buttonNewCategory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void intializeEditCategory(String categoryName){
        headerText.setText("Edit category");

        button.setText("Edit category");
        button.setOnAction(event -> {
            try {
                buttonEditCategory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        titleTextField.setText(categoryName);
    }

    /**
     * Cancel button loads the tasks page back into center-content of dashboard
     * @throws IOException
     */
    public void buttonCancelNewCategory() throws IOException {
        DashboardController.getInstance().initialize();
    }

    /**
     * Check if title is good, then add category, set category and load dashboard
     * @throws IOException
     */
    public void buttonNewCategory() throws IOException {
        String categoryTitle = titleTextField.getText();

        if(CategoryService.validateCategoryTitleSyntax(categoryTitle)){
            CategoryService.addCategoryToCurrentUser(categoryTitle);
            UserStateService.getCurrentUser().setCurrentlySelectedCategory(categoryTitle);
            DashboardController.getInstance().initialize();
        } else {
            errorMessage.setText("Title need to be between 0 and 24 characters");
        }
    }

    /**
     * Edit category button allows one to edit a category which is already created, by recreating a new one with the
     * changes in its place
     * @throws IOException
     */
    public void buttonEditCategory() throws IOException {
        if(CategoryService.validateCategoryTitleSyntax(titleTextField.getText())){
            // Make new category
            CategoryService.addCategoryToCurrentUser(titleTextField.getText());

            // Move tasks in old category to new category
            TaskService.editCategoryOfTasks(TaskService.getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory()), titleTextField.getText());

            // Delete old category
            CategoryService.deleteCategoryCurrentUser(UserStateService.getCurrentUser().getCurrentlySelectedCategory());

            // Set current category to new one
            UserStateService.getCurrentUser().setCurrentlySelectedCategory(titleTextField.getText());

            // Load dashboard into mainContent
            DashboardController.getInstance().initialize();
        } else {
            errorMessage.setText("Title need to be between 0 and 24 characters");
        }
    }

    /**
     * Press new category button if enter is pressed
     */
    public void onKeyPressed(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            try {
                buttonNewCategory();
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
