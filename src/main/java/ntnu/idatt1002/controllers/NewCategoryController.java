package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class NewCategoryController {

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelNewCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void buttonNewCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }
}
