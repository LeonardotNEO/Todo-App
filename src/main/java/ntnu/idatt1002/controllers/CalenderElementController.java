package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;
import java.util.ArrayList;

public class CalenderElementController {
    @FXML private Text dateText;
    @FXML private HBox tasksHBox;

    /**
     * Method used for displaying a calender element
     * @param date
     * @param tasks
     */
    public void display(String date, ArrayList<Task> tasks){
        dateText.setText(date);
        addButtonToCalenderElement(tasks);
    }

    /**
     * Method for adding task buttons to each calender element
     * @param tasks
     */
    public void addButtonToCalenderElement(ArrayList<Task> tasks){
        for(Task task : tasks){
            Button button = new Button();
            button.setText(task.getName());
            button.setOnAction(event -> {
                try {
                    // set current category to this tasks category
                    UserStateService.setCurrentUserCategory(task.getCategory());

                    // load dashboard to maincontent
                    MainController.getInstance().setMainContent("dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            button.setVisible(true);
            button.styleProperty().setValue("-fx-background-radius: 7; -fx-background-color: #001021;");
            button.setTextFill(Paint.valueOf("orange"));
            button.setCursor(Cursor.HAND);

            tasksHBox.getChildren().add(button);
        }
    }
}
