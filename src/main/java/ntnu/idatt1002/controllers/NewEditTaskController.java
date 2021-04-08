package ntnu.idatt1002.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.utils.ColorUtil;
import ntnu.idatt1002.utils.DateConverter;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;
import ntnu.idatt1002.utils.TimeConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A class which contains the buttons related to the creation of a new task
 */
public class NewEditTaskController {

    @FXML private Text header;
    @FXML private TextField titleTextField;
    @FXML private TextField locationTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private MenuButton categoryMenu;
    @FXML private HBox timeBox;
    @FXML private JFXDatePicker datePicker;
    @FXML private JFXTimePicker timePicker;
    @FXML private MenuButton priorityMenu;
    @FXML private JFXCheckBox notification;
    @FXML private HBox colorBox;
    @FXML private JFXColorPicker color;
    @FXML private HBox tagsBox;
    @FXML private JFXChipView tags;
    @FXML private Label errorMessage;
    @FXML private Button button;


    /**
     * Method used for initializing new task page
     */
    public void initializeNewTask(){
        // fill MenuButton categoryMenu with categories
        setCategoryMenu(CategoryService.getCategoriesCurrentUserWithoutPremades());

        // Changes the date format of the datePicker
        datePicker.setConverter(new DateConverter());
        datePicker.setPromptText("dd/mm/yyyy");

        // set timepicker to 24 hour mode
        timePicker.set24HourView(true);

        // set onAction of button
        button.setText("New task");
        button.setOnAction(event -> {
            try {
                buttonNewTask();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // set header
        header.setText("New task");
    }

    /**
     * Method for initializing edit task page
     * @param task the task thats going to be edited
     */
    public void initializeEditTask(Task task){
        // set title prompt
        setTitleTextField(task.getName());

        // set description prompt
        setDescriptionTextArea(task.getDescription());

        // set location prompt
        setLocation(task.getLocation());

        // set categories in menuButton
        setCategoryMenu(CategoryService.getCategoriesCurrentUserWithoutPremades());

        // set category prompt
        setCategoryMenu(task.getCategory());

        // set datepicker prompt and DateConverter
        setDatePicker(DateUtils.getFormattedDate(task.getDeadline()));
        setDatePicker(new DateConverter());

        // set timePicker
        setTimePicker(DateUtils.getFormattedTime(task.getDeadline()));
        setTimePicker(new TimeConverter());
        setTimePicker24Hour(true);

        // set priority prompt
        setPriorityMenu(Integer.toString(task.getPriority()));

        // set notification boolean
        setNotification(task.getNotification());

        // set color
        setColor(task.getColor());

        // set tags
        setTags(task.getTags());

        // set onAction of button and button text
        button.setText("Edit task");
        button.setOnAction(event -> {
            try {
                buttonEditTask(task);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // set header
        header.setText("Edit task");
    }

    /**
     * Cancel button loads the tasks page back into center-content of dashboard
     * @param event
     * @throws IOException
     */
    public void buttonCancelTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().initialize();
    }

    /**
     * Method that uses TaskService to add a new task to the current user
     * @throws IOException
     */
    public void buttonNewTask() throws  IOException {

        // If no input in timePicker set it to the current time
        if(timePicker.getValue() == null) timePicker.setValue(LocalTime.now());

        // convert the data from datePicker and timePicker into ms. Set to 0l if datePicker returns null
        long deadlineTime = datePicker.getValue() == null ? 0l : DateUtils.getAsMs(datePicker.getValue().atTime(timePicker.getValue().getHour() , timePicker.getValue().getMinute()));

        // check if there is any errorcodes
        ArrayList<Integer> errorCodes = TaskService.validateTaskInput(titleTextField.getText(), descriptionTextArea.getText(), priorityMenu.getText(), deadlineTime);

        // if errorCodes contains 3 (no selected priority), we set priority to 0 and remove this errorcode (because we solved it)
        if(errorCodes.contains(3)){
            priorityMenu.setText("0");
            Integer numberThree = 3;
            errorCodes.remove(numberThree);
        }

        if(errorCodes.size() == 0){
            // get all the input tags and put them in a list
            ArrayList<String> tagsList = new ArrayList<>();
            tags.getChips().forEach(tag -> {
                tagsList.add(tag.toString());
            });

            // try to add serialize a new task
            boolean addTaskSuccessful = TaskService.newTask(
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

            // if serializing the task is succesfull, we set current category to the new tasks category and initialize the dashboard
            if(addTaskSuccessful){
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
     * When editButton is clicked, we delete the old task and make a new one
     * @throws IOException
     */
    public void buttonEditTask(Task task) throws IOException {

        // If no input in timePicker set it to current time
        if(timePicker.getValue() == null) timePicker.setValue(LocalTime.now());

        // convert the data from datePicker and timePicker into ms. Set to 0l if datePicker returns null
        long deadlineTime = datePicker.getValue() == null ? 0l : DateUtils.getAsMs(datePicker.getValue().atTime(timePicker.getValue().getHour() , timePicker.getValue().getMinute()));

        // check if there is any errorcodes
        ArrayList<Integer> errorCodes = TaskService.validateTaskInput(titleTextField.getText(), descriptionTextArea.getText(), priorityMenu.getText(), deadlineTime);

        if(errorCodes.size() == 0){
            // get all the input tags and put them in a list
            ArrayList<String> tagsList = new ArrayList<>();
            tags.getChips().forEach(tag -> {
                System.out.println(tag.toString());
            });

            // Make new task
            boolean newTaskSuccesfull = TaskService.newTask(
                    titleTextField.getText(),
                    deadlineTime,
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
            TaskService.deleteTask(TaskService.getTaskByCurrentUser(task.getId()));

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
     * Method for setting some nodes false, to simply interface
     */
    public void buttonSimpleTemplate() {
        descriptionTextArea.setVisible(false);
        descriptionTextArea.setManaged(false);
        locationTextField.setVisible(false);
        locationTextField.setManaged(false);
        priorityMenu.setVisible(false);
        priorityMenu.setManaged(false);
        colorBox.setVisible(false);
        colorBox.setManaged(false);
        tagsBox.setVisible(false);
        tagsBox.setManaged(false);
    }

    /**
     * Method for activating nodes, to show more functionality
     */
    public void buttonAdvancedTemplate() {
        descriptionTextArea.setVisible(true);
        descriptionTextArea.setManaged(true);
        locationTextField.setVisible(true);
        locationTextField.setManaged(true);
        priorityMenu.setVisible(true);
        priorityMenu.setManaged(true);
        colorBox.setVisible(true);
        colorBox.setManaged(true);
        tagsBox.setVisible(true);
        tagsBox.setManaged(true);
    }

    /**
     * Loads categories into categoryMenuButton
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
