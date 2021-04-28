package ntnu.idatt1002.controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
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
import ntnu.idatt1002.model.Task;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.ProjectService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.utils.ColorUtil;
import ntnu.idatt1002.utils.DateConverter;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;
import ntnu.idatt1002.utils.TimeConverter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @FXML private HBox timeBox;
    @FXML private JFXDatePicker datePicker;
    @FXML private JFXTimePicker timePicker;
    @FXML private MenuButton priorityMenu;
    @FXML private MenuButton repeatMenu;
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
    @FXML private HBox priorityBox;
    @FXML private HBox repeatBox;
    @FXML private HBox projectBox;
    @FXML private MenuButton projectMenu;
    @FXML private HBox categoryBox;
    @FXML private MenuButton categoryMenu;
    @FXML private ScrollPane scrollPane;
    @FXML private static AnchorPane fileOptionsPopup;
    @FXML private static Popup popup;
    FileChooser fileChooser = new FileChooser();
    private File selectedFiles;
    private ArrayList<String> listOfFiles = new ArrayList<>();
    private Task taskWithFiles;
    private boolean isNewTask;
    private Task currentTask;


    /**
     * Method used for initializing new task page.
     */
    public void initializeNewTask(){
        // show simple template first
        buttonSimpleTemplate();

        // Changes the date format of the datePicker
        this.datePicker.setConverter(new DateConverter());
        this.datePicker.setPromptText("dd/mm/yyyy");

        // set timePicker
        this.timePicker.setConverter(new TimeConverter());
        this.timePicker.set24HourView(true);

        // populate project and category buttons
        populateProjectMenu();

        // set onAction of button
        this.button.setText("Create");
        this.button.setOnAction(event -> {
            try {
                buttonNewTask();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // set header
        this.header.setText("New task");

        // set boolean to show which page we are on
        isNewTask = true;
    }

    /**
     * Method for initializing edit task page.
     *
     * @param task the task that's going to be edited.
     */
    public void initializeEditTask(Task task){
        // set current task
        currentTask = task;

        // when editing, we want to show advanced template
        buttonAdvancedTemplate();

        // set title prompt
        this.titleTextField.setText(task.getName());

        // set description prompt
        this.descriptionTextArea.setText(task.getDescription());

        // set location prompt
        this.locationTextField.setText(task.getLocation());

        // populate project and category buttons
        populateProjectMenu();

        // populate files
        setTaskWithFiles(task);

        // set datepicker prompt and DateConverter
        LocalDate date = task.getDeadline() == 0L ? null : LocalDate.parse(DateUtils.getFormattedDate(task.getDeadline()), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.datePicker.setValue(date);
        this.datePicker.setConverter(new DateConverter());

        // set timePicker
        LocalTime time = task.getDeadline() == 0L ? null : LocalTime.parse(DateUtils.getFormattedTime(task.getDeadline()), DateTimeFormatter.ofPattern("HH:mm"));
        this.timePicker.setValue(time);
        this.timePicker.setConverter(new TimeConverter());
        this.timePicker.set24HourView(true);

        // set priority prompt
        this.priorityMenu.setText(TaskService.convertPriorityIntToString(task.getPriority()));

        //set repeatTime
        this.repeatMenu.setText(TaskService.convertTimeRepeatToString(task));

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

        // set boolean to show which page we are on
        isNewTask = false;
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
        }
        addUpdateAttachedFiles(listOfFiles);
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
     * @throws IOException
     */
    public void buttonCancelTask() throws IOException {
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
        // If no input in timePicker set it to current time
        if(timePicker.getValue() == null) timePicker.setValue(LocalTime.now());

        // convert the data from datePicker and timePicker into ms. Set to 0l if datePicker returns null
        long deadlineTime = datePicker.getValue() == null ? 0L : DateUtils.getAsMs(datePicker.getValue().atTime(timePicker.getValue().getHour() , timePicker.getValue().getMinute()));

        // check if there is any errorcodes
        ArrayList<Integer> errorCodes = TaskService.validateTaskInput(titleTextField.getText(), descriptionTextArea.getText(), priorityMenu.getText(), deadlineTime, TaskService.convertTimeRepeatToLong(repeatMenu.getText()));

        // handling if priority is set to empty
        if(errorCodes.contains(3)){
            priorityMenu.setText("None");
            errorCodes.remove(Integer.valueOf(3));
        }

        if(errorCodes.size() == 0){
            // get all the input tags and put them in a list
            ArrayList<String> tagsList = new ArrayList<>();
            tags.getChips().forEach(tag -> {
                tagsList.add(tag.toString());
            });

            // set project to null if it is "No project"
            if(projectMenu.getText().equals("No project")){
                projectMenu.setText(null);
            }

            // TaskBuilder
            Task.TaskBuilder builder = new Task.TaskBuilder(UserStateService.getCurrentUser().getUsername(), titleTextField.getText())
                    .description(descriptionTextArea.getText())
                    .deadline(deadlineTime)
                    .priority(TaskService.convertPriorityStringToInt(priorityMenu.getText()))
                    .startDate(DateUtils.getAsMs(LocalDateTime.now()))
                    .category(categoryMenu.getText())
                    .project(projectMenu.getText())
                    .color(ColorUtil.getCorrectColorFormat(color.getValue().toString()))
                    .location(locationTextField.getText())
                    .tags(tagsList)
                    .filePaths(listOfFiles);

            // This can be done with each variable so that we do not have to make everything required
            if(notification1Hour.isSelected()) builder.notification1Hour();
            if(notification24Hours.isSelected()) builder.notification24Hours();
            if(notification7Days.isSelected()) builder.notification7Days();
            if(!repeatMenu.getText().equals("None") && !repeatMenu.getText().isEmpty() && !repeatMenu.getText().equals("Repeat task")) {
                builder.repeatable(true,TaskService.convertTimeRepeatToLong(repeatMenu.getText()));
            } else {
                builder.repeatable(false, 0L);
            }


            // Create the task:
            Task newTask = builder.build();

            // based on argument of method, we edit or add new task
            if(oldTask != null){
                TaskService.editTask(newTask, oldTask.getId());

                // if task change category, its not enough to override, because task has changed folder. We need to delete the old task from old folder
                if(!oldTask.getCategory().equals(newTask.getCategory())){
                    TaskService.deleteTask(oldTask);
                }
            } else {
                TaskService.newTask(newTask);
            }

            // if serializing the task is succesfull, we set current category to the new tasks category and initialize the dashboard
            // navigate back to tasks
            DashboardController.getInstance().initialize();
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
        priorityBox.setVisible(false);
        priorityBox.setManaged(false);
        repeatBox.setVisible(false);
        repeatBox.setManaged(false);
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
        priorityBox.setVisible(true);
        priorityBox.setManaged(true);
        repeatBox.setVisible(true);
        repeatBox.setManaged(true);
        colorBox.setVisible(true);
        colorBox.setManaged(true);
        tagsBox.setVisible(true);
        tagsBox.setManaged(true);
        attachBox.setVisible(true);
        attachBox.setManaged(true);
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
     * When RepeatMenuItem is clicked, we change the RepeatMenuButton to the selection.
     *
     * @param event
     *
     * @throws IOException
     */
    public void clickRepeat(ActionEvent event) throws IOException{
        MenuItem menuItem = (MenuItem) event.getSource();
        repeatMenu.setText(menuItem.getText());
    }

    /**
     * Method for populating the project menu button with menuItems
     */
    public void populateProjectMenu() {
        // get currently selected project
        String currentProject = UserStateService.getCurrentUser().getCurrentlySelectedProject();

        // get arraylist of all projects
        ArrayList<String> projects = ProjectService.getProjectCurrentUserArraylist();

        // add "No project" as the first option
        projects.add(0, "No project");

        // set initial text and elements of projectMenu
        if(currentProject != null){
            projectMenu.setText(currentProject);
            populateCategoryButton(CategoryService.getCategoriesByProjectCurrentUserArraylist(currentProject), UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory());
        } else {
            projectMenu.setText("No project");
            populateCategoryButton(CategoryService.getCategoriesCurrentUserWithoutPremades(), UserStateService.getCurrentUser().getCurrentlySelectedCategory());
        }

        // add listener to each project menuitem button. When clicked, we change the categorymenu depending on which project was clicked.
        for(String project : projects){
            MenuItem menuItem = new MenuItem(project);

            if(project.equals("No project")){
                menuItem.setOnAction(event -> {
                    projectMenu.setText("No project");
                    populateCategoryButton(CategoryService.getCategoriesCurrentUserWithoutPremades(), null);
                });
            } else {
                menuItem.setOnAction(event -> {
                    projectMenu.setText(project);
                    populateCategoryButton(CategoryService.getCategoriesByProjectCurrentUserArraylist(project), null);
                });
            }

            // add menuitem to projectmenu
            projectMenu.getItems().add(menuItem);
        }
    }

    /**
     * Method for populating the categoryMenuButton with categories.
     * @param categories A menuitem is added corresponding to each element in categories arraylist
     * @param currentlySelectedCategory The initial text of category is set to currentlySelectedCategory parameter
     */
    public void populateCategoryButton(ArrayList<String> categories, String currentlySelectedCategory){
        // clear the category menu before adding new elements
        categoryMenu.getItems().clear();

        // set the initial value of categoryMenu text
        if(currentlySelectedCategory == null){
            categoryMenu.setText(categories.get(0));
        } else {
            categoryMenu.setText(currentlySelectedCategory);
        }

        // add menuitems
        for(String category : categories){
            MenuItem menuItem = new MenuItem(category);
            menuItem.setOnAction(event -> {
                categoryMenu.setText(category);
            });

            categoryMenu.getItems().add(menuItem);
        }
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
                if(isNewTask){
                    buttonNewTask();
                } else {
                    buttonEditTask(currentTask);
                }
            }catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
