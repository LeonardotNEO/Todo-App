package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;

import java.io.IOException;
import java.util.ArrayList;

public class NewTaskController {

    @FXML private TextField titleTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private MenuButton categoryMenu;
    @FXML private DatePicker datePicker;

    public void initialize(){
        // fill MenuButton categoryMenu with categories
        setCategoryMenu(TaskService.getCategoryNames());

    }

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelNewTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().setCenterContent("tasks");
    }

    public void buttonNewTask(ActionEvent event) throws  IOException {
        boolean addTaskSuccessful = TaskService.newTask(
                titleTextField.getText(),
                datePicker.getValue().toString(),
                descriptionTextArea.getText(),
                1,
                null,
                categoryMenu.getText()
        ); // method that communicates with DAO to att new task (parameters are FXML parameters). If successful the method return true

        if(addTaskSuccessful){
            TaskService.getCategoriesWithTasks();
            DashboardController.getInstance().setCenterContent("tasks"); // redirects back to tasks if successful
        } else {
            //errormessage to textfield?
        }

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
}
