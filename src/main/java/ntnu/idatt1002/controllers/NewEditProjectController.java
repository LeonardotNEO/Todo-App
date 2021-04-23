package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import ntnu.idatt1002.service.ProjectService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

public class NewEditProjectController {

    private boolean isNew;
    @FXML private TextField title;
    @FXML private Text header;
    @FXML private Button buttonNewEditProject;
    @FXML private Label errorMessage;

    public void initializeNew(){
        isNew = true;
        header.setText("Create project");
        buttonNewEditProject.setText("Create project");
        buttonNewEditProject.setOnAction(event -> {
            try {
                newProject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void initializeEdit(){
        isNew = false;
        header.setText("Edit project");
        buttonNewEditProject.setText("Edit project");
        buttonNewEditProject.setOnAction(event -> {
            try {
                editProject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void buttonCancel() throws IOException {
        DashboardController.getInstance().initialize();
    }

    public void newProject() throws IOException {
        // check if title is valid
        if(ProjectService.validateTitle(title.getText())){
            // add project to current user
            ProjectService.addNewProjectCurrentUser(title.getText());

            // set this project as currently selected
            UserStateService.getCurrentUser().setCurrentlySelectedProject(title.getText());

            // load dashboard
            DashboardController.getInstance().initialize();
        } else {
            // display errormessage if title is not valid
            errorMessage.setText("* Title must be between 0 and 30 characters");
        }
    }

    public void editProject() throws IOException {
        // check if title is valid
        if(ProjectService.validateTitle(title.getText())){
            // edit project name
            ProjectService.editProject(UserStateService.getCurrentUser().getCurrentlySelectedProject(), title.getText());

            // set this project as currently selected
            UserStateService.getCurrentUser().setCurrentlySelectedProject(title.getText());

            // load dashboard
            DashboardController.getInstance().initialize();
        } else {
            // display errormessage if title is not valid
            errorMessage.setText("* Title must be between 0 and 30 characters");
        }
    }

    /**
     * When enter key is pressed, we either press new project button or edit project button
     */
    public void onKeyPressed(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            try {
                if(isNew){
                    newProject();
                } else {
                    editProject();
                }
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
