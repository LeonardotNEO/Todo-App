package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A class which contains the buttons related to the creation of a new category
 */
public class NewEditCategoryController {

    private String projectName = "";
    @FXML private TextField categoryTitle;
    @FXML private Label errorMessage;
    @FXML private Text headerText;
    @FXML private Button button;

    public void intializeNewCategory(){
        projectName = UserStateService.getCurrentUser().getCurrentlySelectedProject();
        headerText.setText("Create category");

        button.setText("Create category");
        button.setOnAction(event -> {
            try {
                buttonNewCategory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void intializeEditCategory(){
        projectName = UserStateService.getCurrentUser().getCurrentlySelectedProject();
        headerText.setText("Edit category");

        button.setText("Edit category");
        button.setOnAction(event -> {
            try {
                buttonEditCategory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if(projectName == null){
            categoryTitle.setText(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
        } else {
            categoryTitle.setText(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory());
        }
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
        if(CategoryService.validateCategoryTitleSyntax(categoryTitle.getText())){
            // we specify if this category should go to a project
            if(projectName == null){
                // add new category
                CategoryService.addCategoryToCurrentUser(categoryTitle.getText());

                // set currently selected category to the new one
                UserStateService.getCurrentUser().setCurrentlySelectedCategory(categoryTitle.getText());
            } else {
                // add new category
                CategoryService.addCategoryToCurrentUser(projectName, categoryTitle.getText());

                // set currently selected category to the new one
                UserStateService.getCurrentUser().setCurrentlySelectedProjectCategory(categoryTitle.getText());
                UserStateService.getCurrentUser().setCurrentlySelectedProject(projectName);
            }

            // load dashboard back in
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
        if(CategoryService.validateCategoryTitleSyntax(categoryTitle.getText())){
            if(projectName == null){
                // Make new category
                CategoryService.addCategoryToCurrentUser(categoryTitle.getText());

                // Move tasks in old category to new category
                ArrayList<Task> tasksToMove = TaskService.getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory(), null);
                System.out.println(tasksToMove.size());
                TaskService.editCategoryAndProjectOfTasks(tasksToMove, categoryTitle.getText(), null);

                // Delete old category
                CategoryService.deleteCategoryCurrentUser(UserStateService.getCurrentUser().getCurrentlySelectedCategory());

                // Set current category to new one
                UserStateService.getCurrentUser().setCurrentlySelectedCategory(categoryTitle.getText());
            } else {
                // Make new category
                CategoryService.addCategoryToCurrentUser(projectName, categoryTitle.getText());

                // Move tasks in old category to new category
                ArrayList<Task> tasksToMove = TaskService.getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory(), projectName);
                TaskService.editCategoryAndProjectOfTasks(tasksToMove, categoryTitle.getText(), projectName);

                // Delete old category
                CategoryService.deleteCategoryCurrentUser(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory(), projectName);

                // Set current category to new one
                UserStateService.getCurrentUser().setCurrentlySelectedProjectCategory(categoryTitle.getText());
            }

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
