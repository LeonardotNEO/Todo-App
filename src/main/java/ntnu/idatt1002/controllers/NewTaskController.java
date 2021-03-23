package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;

public class NewTaskController {

    @FXML private TextField titleTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private MenuButton categoryMenu;
    @FXML private DatePicker datePicker;
    @FXML private MenuButton priorityMenu;

    /**
     * Initialize method loads categories into categoryMenu when initialized
     */
    public void initialize(){
        // fill MenuButton categoryMenu with categories
        setCategoryMenu(CategoryService.getCategoriesCurrentUser());

    }

    /**
     * cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelNewTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().loadTasksPage(TaskService.getCategoryWithTasks(UserStateService.getCurrentUserCategory()));
    }

    /**
     * Method that uses TaskService to add a new task to the current user
     * @param event
     * @throws IOException
     */
    public void buttonNewTask(ActionEvent event) throws  IOException {
        boolean addTaskSuccessful = TaskService.newTask(
                titleTextField.getText(),
                datePicker.getValue().toString(),
                descriptionTextArea.getText(),
                Integer.parseInt(priorityMenu.getText()),
                Clock.systemUTC().toString(),
                categoryMenu.getText()
        );

        if(addTaskSuccessful){
            UserStateService.setCurrentUserCategory(categoryMenu.getText());
            DashboardController.getInstance().initialize();
        } else {
            //errormessage
        }

    }

    /**
     * Loads categories into categoryMenuButton
     * @param categories
     */
    public void setCategoryMenu(String[] categories) {
        for (String category : categories) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(category);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    categoryMenu.setText(category);
                }
            });

            categoryMenu.getItems().add(menuItem);
        }

        categoryMenu.setText(UserStateService.getCurrentUserCategory());
    }

    /**
     * When priorityMenuItem is clicked, we change the priorityMenuButton to the selection
     * @param event
     * @throws IOException
     */
    public void clickPriority(ActionEvent event) throws IOException{
        MenuItem menuItem = (MenuItem) event.getSource();
        priorityMenu.setText(menuItem.getText());
    }
}
