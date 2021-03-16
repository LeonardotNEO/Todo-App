package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class TaskController {

    @FXML private Text taskName;
    @FXML private Text taskDescription;

    public void setTaskName(String name){
        taskName.setText(name);
    }

    public void setTaskDescription(String description){
        taskDescription.setText(description);
    }

    /**
     * delete task from this user completely (or set task category to trashbin?)
     * @param event
     * @throws IOException
     */
    public void deleteTask(ActionEvent event) throws IOException {

    }

    /**
     * updates center-content of dashboard to editTask.fxml
     * @param event
     * @throws IOException
     */
    public void editTask(ActionEvent event) throws IOException{
        //DashboardController.getInstance().setCenterContent();
    }
}
