package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public class EditCategoryController {

    public void buttonCancelEditCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void buttonEditCategory(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }
}
