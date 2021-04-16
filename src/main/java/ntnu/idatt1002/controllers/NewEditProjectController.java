package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import ntnu.idatt1002.service.ProjectService;

import java.io.IOException;

public class NewEditProjectController {
    @FXML private TextField title;

    @FXML private Text header;
    @FXML private Button buttonNewEditProject;
    @FXML private Label errorMessage;

    public void initializeNew(){
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
        header.setText("Edit project");
        buttonNewEditProject.setText("Edit project");
        buttonNewEditProject.setOnAction(event -> {
            try {
                newProject();
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

            // load dashboard
            DashboardController.getInstance().initialize();
        } else {
            // display errormessage if title is not valid
            errorMessage.setText("* Title must be between 0 and 30 characters");
        }
    }
}
