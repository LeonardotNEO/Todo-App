package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NotificationController {

    @FXML private Label title;
    @FXML private Label description;
    @FXML private Label dueDate;

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setDueDate(String dueDate) {
        this.dueDate.setText(dueDate);
    }
}
