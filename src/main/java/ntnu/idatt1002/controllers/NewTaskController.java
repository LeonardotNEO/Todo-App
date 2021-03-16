package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class NewTaskController {

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelNewTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void buttonNewTask(ActionEvent event) throws  IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }
}
