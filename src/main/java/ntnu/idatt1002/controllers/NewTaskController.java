package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class NewTaskController {

    public void buttonCancelNewTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void buttonNewTask(ActionEvent event) throws  IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }
}
