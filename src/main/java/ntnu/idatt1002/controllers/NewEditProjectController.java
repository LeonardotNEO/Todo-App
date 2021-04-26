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

/**
 * Controller for newEditProject.fxml
 */
public class NewEditProjectController {

    private boolean isNew;
    @FXML private TextField title;
    @FXML private Text header;
    @FXML private Button buttonNewEditProject;
    @FXML private Label errorMessage;

    /**
     * Method for initializing the new part of newEditProject
     */
    public void initializeNew(){
        isNew = true;
        header.setText("New project");
        buttonNewEditProject.setText("Create");
        buttonNewEditProject.setOnAction(event -> {
            try {
                newProject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method for initializing the edit part of newEditProject
     */
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

    /**
     * Button for handling when the cancel button is pressed.
     * When the button is pressed we load the dashboard back in.
     * @throws IOException
     */
    public void buttonCancel() throws IOException {
        DashboardController.getInstance().initialize();
    }

    /**
     * Method for handling when the "newProject" button is pressed
     * @throws IOException
     */
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

    /**
     * Method for handling when the "editProject" button is pressed
     * @throws IOException
     */
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
