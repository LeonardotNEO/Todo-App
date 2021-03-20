package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;

public class EditTaskController {

    private int id;
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
        DashboardController.getInstance().loadTasksPage(TaskService.getTasksByCurrentUser());
    }

    public void buttonEditTask(ActionEvent event) throws IOException {
        // Make new task
        TaskService.newTask(titleTextField.getText(), datePicker.getValue().toString(), descriptionTextArea.getText(), 0, LocalDate.now().toString(), categoryMenu.getText());

        // Delete old one
        TaskService.deleteTask(TaskService.getTaskByCurrentUser(id));

        // navigate back to tasks
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitleTextField(String title) {
        this.titleTextField.setText(title);
    }

    public void setDescriptionTextArea(String description) {
        this.descriptionTextArea.setText(description);
    }

    public void setCategoryMenu(ArrayList<String> categories) {
        categories.forEach(category -> {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(category);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    categoryMenu.setText(category);
                }
            });

            categoryMenu.getItems().add(menuItem);
        });
    }

    public void setDatePicker(String date) {
        this.datePicker.setValue(LocalDate.parse(date));
    }
}
