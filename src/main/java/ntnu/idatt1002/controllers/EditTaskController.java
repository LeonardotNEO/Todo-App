package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EditTaskController {

    @FXML private TextField titleTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private MenuButton categoryMenu;
    @FXML private DatePicker datePicker;

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelEditTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void buttonEditTask(ActionEvent event) throws IOException {
        boolean editTask = true; //

        if(editTask){
            DashboardController.getInstance().setCenterContent("tasks");
        } else {
            //errormessage
        }
    }

    public void editTask(){
        titleTextField.getText();
        descriptionTextArea.getText();
        datePicker.getPromptText();
    }

}
