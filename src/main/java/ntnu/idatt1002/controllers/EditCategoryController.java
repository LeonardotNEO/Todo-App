package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class EditCategoryController {

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelEditCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void buttonEditCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }
}
