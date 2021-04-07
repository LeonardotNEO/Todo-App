package ntnu.idatt1002.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import ntnu.idatt1002.service.TaskService;

import ntnu.idatt1002.utils.DateConverter;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;
import ntnu.idatt1002.utils.TimeConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * A class which contains the buttons related to editing a task
 */
public class EditTaskController {

    private long id;
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
    @FXML private Label errorMessage;

    /**
     * Cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelEditTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().initialize();
    }

    /**
     * When editButton is clicked, we delete the old task and make a new one
     * @param event
     * @throws IOException
     */
    public void buttonEditTask(ActionEvent event) throws IOException {
        // check if there is any errorcodes
        ArrayList<Integer> errorCodes = TaskService.validateTaskInput(titleTextField.getText(), descriptionTextArea.getText(), priorityMenu.getText());

        if(errorCodes.size() == 0){
            // get all the input tags and put them in a list
            ArrayList<String> tagsList = new ArrayList<>();
            tags.getChips().forEach(tag -> {
                System.out.println(tag.toString());
            });

            // Make new task
            boolean newTaskSuccesfull = TaskService.newTask(
                    titleTextField.getText(),
                    DateUtils.getAsMs(datePicker.getValue().atTime(timePicker.getValue().getHour(), timePicker.getValue().getMinute())),
                    descriptionTextArea.getText(),
                    Integer.parseInt(priorityMenu.getText()),
                    DateUtils.getAsMs(LocalDate.now()),
                    categoryMenu.getText(),
                    color.getValue().toString(),
                    locationTextField.getText(),
                    notification.isSelected(),
                    tagsList
            );

            // Delete old one
            TaskService.deleteTask(TaskService.getTaskByCurrentUser(id));

            if(newTaskSuccesfull){
                // set current category to this tasks category
                UserStateService.setCurrentUserCategory(categoryMenu.getText());

                // navigate back to tasks
                DashboardController.getInstance().initialize();
            }
        } else {
            errorMessage.setText(TaskService.getErrorMessageString(errorCodes));
        }
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

    public void setId(long id){
        this.id = id;
    }

    /**
     * A method to set a title in a text field
     * @param title
     */
    public void setTitleTextField(String title) {
        this.titleTextField.setText(title);
    }

    /**
     * A method to set a description in a text field
     * @param description
     */
    public void setDescriptionTextArea(String description) {
        this.descriptionTextArea.setText(description);
    }

    /**
     * A method to set a date
     * @param date
     */
    public void setDatePicker(String date) {
        this.datePicker.setValue(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    /**
     * A method to set the converter for the date one uses.
     * @param dateConverter
     */
    public void setDatePicker(DateConverter dateConverter) {
        this.datePicker.setConverter(dateConverter);
    }

    /**
     * A method to set a time
     * @param clock
     */
    public void setTimePicker(String clock) {
        this.timePicker.setValue(LocalTime.parse(clock, DateTimeFormatter.ofPattern("HH:mm")));
    }

    /**
     * A method to set the converter for the time that is beeing used.
     * @param timeConverter
     */
    public void setTimePicker(TimeConverter timeConverter) {
        this.timePicker.setConverter(timeConverter);
    }

    public void setTimePicker24Hour(boolean time){
        timePicker.set24HourView(time);
    }

    public void setCategoryMenu(String category) {
        this.categoryMenu.setText(category);
    }

    /**
     * A method to set a priority to the priority menu
     * @param priority
     */
    public void setPriorityMenu(String priority) {
        this.priorityMenu.setText(priority);
    }

    /**
     * A method to set the color
     * @param color
     */
    public void setColor(String color){
        this.color.setValue(Color.valueOf(color));
    }

    /**
     * A method to set tags
     * @param tags
     */
    public void setTags(ArrayList<String> tags){
        this.tags.getChips().addAll(tags);
    }

    /**
     * A method to set notification
     * @param notification
     */
    public void setNotification(boolean notification){
        this.notification.setSelected(notification);
    }

    public void setLocation(String locationText){
        this.locationTextField.setText(locationText);
    }
}
