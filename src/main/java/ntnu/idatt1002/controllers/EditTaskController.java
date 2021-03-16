package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditTaskController {

    @FXML private TextField titleTextField;
    @FXML private TextArea descriptionTextArea;

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelEditTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void buttonEditTask(ActionEvent event) throws IOException {

    }

    public void editTask(){
        titleTextField.getText();
        descriptionTextArea.getText();
    }

}
