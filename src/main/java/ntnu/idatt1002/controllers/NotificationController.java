package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NotificationController {

    @FXML private Label title;
    @FXML private Label description;
    @FXML private Label dueDate;

    /**
     * A method to set a title
     * @param title
     */
    public void setTitle(String title) {
        this.title.setText(title);
    }

    /**
     * A method to set a description
     * @param description
     */
    public void setDescription(String description) {
        this.description.setText(description);
    }

    /**
     * A method to set a due date
     * @param dueDate
     */
    public void setDueDate(String dueDate) {
        this.dueDate.setText(dueDate);
    }
}
