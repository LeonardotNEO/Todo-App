package ntnu.idatt1002.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;

import ntnu.idatt1002.utils.DateConverter;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EditTaskController {

    private int id;
    @FXML private TextField titleTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private MenuButton categoryMenu;
    @FXML private JFXDatePicker datePicker;
    @FXML private JFXTimePicker timePicker;
    @FXML private MenuButton priorityMenu;
    @FXML private JFXCheckBox notification;
    @FXML private JFXColorPicker color;
    @FXML private JFXChipView tags;
    @FXML private TextField locationTextField;

    /**
     * Cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelEditTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().loadTasksPage(TaskService.getTasksByCurrentUser());
    }

    /**
     * When editButton is clicked, we delete the old task and make a new one
     * @param event
     * @throws IOException
     */
    public void buttonEditTask(ActionEvent event) throws IOException {
        ArrayList<String> tagsList = new ArrayList<>();
        tags.getChips().forEach(tag -> {
            System.out.println(tag.toString());
        });

        // Make new task
        TaskService.newTask(
                titleTextField.getText(),
                datePicker.getValue(),
                descriptionTextArea.getText(),
                Integer.parseInt(priorityMenu.getText()),
                TaskService.getDeadlineMs(LocalDate.now()),
                categoryMenu.getText(),
                color.getValue().toString(),
                locationTextField.getText(),
                notification.isSelected(),
                tagsList
                );

        // Delete old one
        TaskService.deleteTask(TaskService.getTaskByCurrentUser(id));

        // set current category to this tasks category
        UserStateService.setCurrentUserCategory(categoryMenu.getText());

        // navigate back to tasks
        DashboardController.getInstance().initialize();
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

    /**
     * Loads menuItem elements with categoryNames into categoryMenuButton
     * @param categories
     */
    public void setCategoryMenu(ArrayList<String> categories) {
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

    public void setId(int id){
        this.id = id;
    }

    public void setTitleTextField(String title) {
        this.titleTextField.setText(title);
    }

    public void setDescriptionTextArea(String description) {
        this.descriptionTextArea.setText(description);
    }

    public void setDatePicker(String date) {
        this.datePicker.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public void setDatePicker(DateConverter dateConverter) {
        this.datePicker.setConverter(dateConverter);
    }

    public void setTimePicker(LocalTime localTime) {
        this.timePicker.setValue(localTime);
    }

    public void setTimePicker24Hour(boolean time){
        timePicker.set24HourView(time);
    }

    public void setCategoryMenu(String category) {
        this.categoryMenu.setText(category);
    }

    public void setPriorityMenu(String priority) {
        this.priorityMenu.setText(priority);
    }

    public void setColor(String color){
        this.color.setValue(Color.valueOf(color));
    }

    public void setTags(ArrayList<String> tags){
        this.tags.getChips().addAll(tags);
    }

    public void setNotification(boolean notification){
        this.notification.setSelected(notification);
    }

    public void setLocation(String locationText){
        this.locationTextField.setText(locationText);
    }
}
