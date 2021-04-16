package ntnu.idatt1002.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import ntnu.idatt1002.App;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.utils.ColorUtil;
import ntnu.idatt1002.utils.DateConverter;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;
import ntnu.idatt1002.utils.TimeConverter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A class which contains the buttons related to the creation of a new task.
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
    @FXML private Button buttonAttachFiles;
    @FXML private VBox vboxForFiles;
    @FXML private ScrollPane scrollPane;
    @FXML private static AnchorPane fileOptionsPopup;
    @FXML private static Popup popup;
    FileChooser fileChooser = new FileChooser();
    private File selectedFiles;
    private ArrayList<String> listOfFiles = new ArrayList<>();
    private Task taskWithFiles;


    /**
     * Method used for initializing new task page.
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
     * Method for initializing edit task page.
     *
     * @param task the task that's going to be edited.
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

        setTaskWithFiles(task);

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

        setListOfFiles(task.getFilePaths());

        addUpdateAttachedFiles(listOfFiles);
        scrollPane.setContent(vboxForFiles);

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
     * A method which allows the user to choose and attach a file to a task.
     * The method opens a file explorer window and lets the user choose a file.
     * The chosen file will be added to a scrollPane.
     */
    public void buttonAttachFiles() {
        vboxForFiles.getChildren().clear();
        selectedFiles = fileChooser.showOpenDialog(App.getStage());
        if (selectedFiles != null) {
            listOfFiles.add(selectedFiles.getAbsolutePath());
        } else {}
        addUpdateAttachedFiles(listOfFiles);
        scrollPane.setContent(vboxForFiles);
    }

    /**
     * A method which updates the attached files in vboxForFiles.
     * This method takes a list of files and adds them to the vboxForFiles as buttons.
     * These buttons all executes the fileOptionsPopup method when pressed.
     *
     * @param list the list of files which shall be added to the vbox.
     */
    public void addUpdateAttachedFiles(ArrayList<String> list) {
        list.forEach(e -> {
            String [] fileName = e.split("\\\\");
            Button b = new Button(fileName[fileName.length -1]);
            b.setOnAction(event -> {
                try {
                    fileOptionsPopup(e);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });
            vboxForFiles.getChildren().add(b);
        });
    }

    /**
     * A method which opens a popup window.
     * This window contains buttons for opening a attached file, removing a file from task and closing the window.
     *
     * @param filePath the filePath of the file one shall open the popup window for.
     *
     * @throws IOException
     */
    public void fileOptionsPopup(String filePath) throws IOException {
        FXMLLoader loaderPopup = new FXMLLoader(App.class.getResource("/fxml/attachedFilePopup.fxml"));
        fileOptionsPopup = loaderPopup.load();
        AttachedFilePopupController attachedFilePopup = loaderPopup.getController();
        popup = new Popup();
        popup.setAutoHide(true);
        popup.getContent().add(fileOptionsPopup);

        fileOptionsPopup.getStylesheets().add(App.class.getResource("/css/main.css").toExternalForm());
        fileOptionsPopup.setStyle(UserStateService.getCurrentUser().getTheme());
        popup.show(App.getStage());

        attachedFilePopup.setFilePath(filePath);
        attachedFilePopup.buttonRemoveFile.setOnAction(event -> {
            Task changedTask = TaskService.removeAttachedFile(taskWithFiles, filePath);
            vboxForFiles.getChildren().clear();
            addUpdateAttachedFiles(changedTask.getFilePaths());
            popup.hide();
        });
        attachedFilePopup.buttonCloseFileOptions.setOnAction(event -> popup.hide());
    }

    /**
     * Cancel button loads the tasks page back into center-content of dashboard.
     *
     * @param event
     *
     * @throws IOException
     */
    public void buttonCancelTask(ActionEvent event) throws IOException {
        DashboardController.getInstance().initialize();
    }

    /**
     * Method that uses TaskService to add a new task to the current user.
     *
     * @throws IOException
     */
    public void buttonNewTask() throws  IOException {
        addEditTask(null);
    }

    /**
     * When editButton is clicked, we delete the old task and make a new one.
     *
     * @throws IOException
     */
    public void buttonEditTask(Task task) throws IOException {
        addEditTask(task);
    }

    /**
     * Method used for adding a new task or editing.
     * When adding a new task, the task is created.
     * When editing, the task with same id will be overridden.
     *
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
                tagsList.add(tag.toString());
            });
            oldTask.setTags(tagsList);

            // TaskBuilder
            Task.TaskBuilder builder = new Task.TaskBuilder(UserStateService.getCurrentUser().getUsername(), titleTextField.getText())
                    .description(descriptionTextArea.getText())
                    .deadline(deadlineTime)
                    .priority(Integer.parseInt(priorityMenu.getText()))
                    .startDate(DateUtils.getAsMs(LocalDate.now()))
                    .category(categoryMenu.getText())
                    .color(ColorUtil.getCorrectColorFormat(color.getValue().toString()))
                    .location(locationTextField.getText())
                    .tags(tagsList)
                    .filePaths(listOfFiles);

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
                TaskService.newTask(newTask);
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
     * Method for setting some nodes false, to simplify interface.
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
     * Method for activating nodes, to show more functionality.
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

    /**
     * Loads categories into categoryMenuButton.
     *
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
     * When priorityMenuItem is clicked, we change the priorityMenuButton to the selection.
     *
     * @param event
     *
     * @throws IOException
     */
    public void clickPriority(ActionEvent event) throws IOException{
        MenuItem menuItem = (MenuItem) event.getSource();
        priorityMenu.setText(menuItem.getText());
    }

    /**
     * A method to set the color.
     *
     * @param color the new color.
     */
    public void setColor(String color){
        this.color.setValue(Color.valueOf(color));
    }

    /**
     * A method to set the listOfFiles.
     *
     * @param attachedFiles the attachedFiles which listOfFiles shall be set to.
     */
    public void setListOfFiles(ArrayList<String> attachedFiles) {
        this.listOfFiles =attachedFiles;
    }

    /**
     * A method to set the TaskWithFiles.
     *
     * @param task the task which has files attached.
     */
    public void setTaskWithFiles(Task task) {this.taskWithFiles = task;}

    /**
     * Press new task button if enter is pressed.
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
