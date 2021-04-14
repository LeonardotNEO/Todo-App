package ntnu.idatt1002.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.CategoryService;
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
    @FXML private JFXCheckBox notification1Hour;
    @FXML private JFXCheckBox notification24Hours;
    @FXML private JFXCheckBox notification7Days;
    @FXML private HBox colorBox;
    @FXML private JFXColorPicker color;
    @FXML private HBox tagsBox;
    @FXML private JFXChipView tags;
    @FXML private HBox attachBox;
    @FXML private Label errorMessage;
    @FXML private Button button;


    /**
     * Method used for initializing new task page
     */
    public void initializeNewTask(){
        // show simple template first
        buttonSimpleTemplate();

        // fill MenuButton categoryMenu with categories
        setCategoryMenu(CategoryService.getCategoriesCurrentUserWithoutPremades());

        // Changes the date format of the datePicker
        this.datePicker.setConverter(new DateConverter());
        this.datePicker.setPromptText("dd/mm/yyyy");

        // set timePicker
        this.timePicker.setConverter(new TimeConverter());
        this.timePicker.set24HourView(true);

        // set onAction of button
        this.button.setText("New task");
        this.button.setOnAction(event -> {
            try {
                buttonNewTask();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // set header
        this.header.setText("New task");
    }

    /**
     * Method for initializing edit task page
     * @param task the task thats going to be edited
     */
    public void initializeEditTask(Task task){
        // when editing, we want to show advanced template
        buttonAdvancedTemplate();

        // set title prompt
        this.titleTextField.setText(task.getName());

        // set description prompt
        this.descriptionTextArea.setText(task.getDescription());

        // set location prompt
        this.locationTextField.setText(task.getLocation());

        // set categories in menuButton
        setCategoryMenu(CategoryService.getCategoriesCurrentUserWithoutPremades());

        // set category prompt
        this.categoryMenu.setText(task.getCategory());

        // set datepicker prompt and DateConverter
        this.datePicker.setValue(LocalDate.parse(DateUtils.getFormattedDate(task.getDeadline()), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.datePicker.setConverter(new DateConverter());

        // set timePicker
        this.timePicker.setValue(LocalTime.parse(DateUtils.getFormattedTime(task.getDeadline()), DateTimeFormatter.ofPattern("HH:mm")));
        this.timePicker.setConverter(new TimeConverter());
        this.timePicker.set24HourView(true);

        // set priority prompt
        this.priorityMenu.setText(Integer.toString(task.getPriority()));

        // set notification booleans
        this.notification1Hour.setSelected(task.isNotification1Hour());
        this.notification24Hours.setSelected(task.isNotification24Hours());
        this.notification7Days.setSelected(task.isNotification7Days());

        // set color
        this.color.setValue(Color.valueOf(task.getColor()));

        // set tags
        this.tags.getChips().addAll(task.getTags());

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
        addEditTask(null);
    }

    /**
     * When editButton is clicked, we delete the old task and make a new one
     * @throws IOException
     */
    public void buttonEditTask(Task task) throws IOException {
        addEditTask(task);
    }

    /**
     * Method used for adding a new task or editing. When adding a new task, the task is created. When editing, the task with same id will be overridden.
     * @throws IOException
     */
    public void addEditTask(Task oldTask) throws IOException {
        // result
        boolean result = false;

        // If no input in timePicker set it to current time
        if(timePicker.getValue() == null) timePicker.setValue(LocalTime.now());

        // convert the data from datePicker and timePicker into ms. Set to 0l if datePicker returns null
        long deadlineTime = datePicker.getValue() == null ? 0l : DateUtils.getAsMs(datePicker.getValue().atTime(timePicker.getValue().getHour() , timePicker.getValue().getMinute()));

        // check if there is any errorcodes
        ArrayList<Integer> errorCodes = TaskService.validateTaskInput(titleTextField.getText(), descriptionTextArea.getText(), priorityMenu.getText(), deadlineTime);

        // handling if priority is set to empty
        if(errorCodes.contains(3)){
            priorityMenu.setText("0");
            errorCodes.remove(Integer.valueOf(3));
        }

        if(errorCodes.size() == 0){
            // get all the input tags and put them in a list
            ArrayList<String> tagsList = new ArrayList<>();
            tags.getChips().forEach(tag -> {
                System.out.println(tag.toString());
            });

            // TaskBuilder
            Task.TaskBuilder builder = new Task.TaskBuilder(UserStateService.getCurrentUser().getUsername(), titleTextField.getText())
                    .description(descriptionTextArea.getText())
                    .deadline(deadlineTime)
                    .priority(Integer.parseInt(priorityMenu.getText()))
                    .startDate(DateUtils.getAsMs(LocalDate.now()))
                    .category(categoryMenu.getText())
                    .color(color.getValue().toString())
                    .location(locationTextField.getText())
                    .tags(tagsList);

            // This can be done with each variable so that we do not have to make everything required
            if(notification1Hour.isSelected()) builder.notification1Hour();
            if(notification24Hours.isSelected()) builder.notification24Hours();
            if(notification7Days.isSelected()) builder.notification7Days();

            // Create the task:
            Task newTask = builder.build();

            // based on argument of method, we edit or add new task
            if(oldTask != null){
                TaskService.editTask(newTask, oldTask.getId());

                // if task change category, its not enough to override, because task has changed folder. We need to delete the old task from old folder
                if(!oldTask.getCategory().equals(newTask.getCategory())){
                    TaskService.deleteTask(oldTask);
                }
                result = true;
            } else {
                TaskService.newTaskValidation(newTask);
                result = true;
            }

            // if serializing the task is succesfull, we set current category to the new tasks category and initialize the dashboard
            if(result){
                // set current category to this tasks category
                UserStateService.getCurrentUser().setCurrentlySelectedCategory(categoryMenu.getText());

                // navigate back to tasks
                DashboardController.getInstance().initialize();
            }
        } else {
            errorMessage.setText(TaskService.getErrorMessageString(errorCodes));
        }
    }

    /**
     * Method for setting some nodes false, to simplify interface
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
        attachBox.setVisible(false);
        attachBox.setManaged(false);
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
        attachBox.setVisible(true);
        attachBox.setManaged(true);
    }

    public void buttonAttachFiles(ActionEvent event){

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

        categoryMenu.setText(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
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
     * Press new task button if enter is pressed
     */
    public void onKeyPressed(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            try {
                buttonNewTask();
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
