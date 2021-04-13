package ntnu.idatt1002.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class OverviewController {

    public static ArrayList<String> excludedCategories;
    private LocalDateTime currentDate;
    @FXML private VBox dueThisWeek;
    @FXML private VBox dueNext7Days;
    @FXML private VBox dueThisMonth;
    @FXML private ScrollPane scrollpane;

    @FXML private AnchorPane calenderView;
    @FXML private GridPane calenderViewGrid;
    @FXML private Text month;

    @FXML private AnchorPane content;

    public OverviewController(){
        // Fill excludedcategories with categories which wont be included in overview pages
        excludedCategories = new ArrayList<>();
        excludedCategories.add("Finished tasks");
        excludedCategories.add("Trash bin");
    }
    
    /**
     * Initialize method used for loading the SimpleView as default when loading "Overview"
     * @throws IOException
     */
    public void initialize() throws IOException {
        initializeSimpleView();
        currentDate = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * Method used for loading SimpleView
     * @throws IOException
     */
    public void initializeSimpleView() throws IOException {
        // clean simpleview before adding new task UI elements
        removeTasksFromSimpleView();

        // display this node
        displayNode("simpleView");

        // add listeners to scrollpanes (for resizing)
        addScrollpaneListener();

        // Time now
        LocalDateTime now = LocalDateTime.now();

        // The remaining days of the current week
        LocalDateTime firstDayOfNextWeek = now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        LocalDateTime nowPlusDaysUntilNextMonday = now.plusDays(ChronoUnit.DAYS.between(now, firstDayOfNextWeek));
        ArrayList<Task> tasksThisWeek = TaskService.getTasksBetweenDates(TaskService.getTasksExcludingCategories(TaskService.getTasksByCurrentUser(), excludedCategories), DateUtils.getAsMs(now), DateUtils.getAsMs(nowPlusDaysUntilNextMonday));
        addTasksToSimpleView("dueThisWeek", getTaskUIListFromTaskObjectList(tasksThisWeek));

        // The next 7 days date
        LocalDateTime nowPlus7Days = now.plusDays(7);
        ArrayList<Task> tasks7Days = TaskService.getTasksBetweenDates(TaskService.getTasksExcludingCategories(TaskService.getTasksByCurrentUser(), excludedCategories), DateUtils.getAsMs(now), DateUtils.getAsMs(nowPlus7Days));
        addTasksToSimpleView("dueNext7Days", getTaskUIListFromTaskObjectList(tasks7Days));

        // The remaining days of the current month
        LocalDateTime endOfMonthDate = now.with(TemporalAdjusters.lastDayOfMonth());
        ArrayList<Task> tasksCurrentMonth = TaskService.getTasksBetweenDates(TaskService.getTasksExcludingCategories(TaskService.getTasksByCurrentUser(), excludedCategories), DateUtils.getAsMs(now), DateUtils.getAsMs(endOfMonthDate));
        addTasksToSimpleView("dueThisMonth", getTaskUIListFromTaskObjectList(tasksCurrentMonth));
    }

    /**
     * Method used for loading CalenderView
     */
    public void initializeCalenderView(int plusMinus) throws IOException {
        // display this node
        displayNode("calenderView");

        // remove elements from grid before adding new ones
        removeGridFromCalenderView();

        // if 1 or 2 is specificed in the arguments for the method, go a month forward or backward
        if(plusMinus == 1){
            currentDate = this.currentDate.plusMonths(1);
        }
        if(plusMinus == 2){
            currentDate = this.currentDate.minusMonths(1);
        }

        LocalDateTime currentTimeThisPage = currentDate;
        int currentWeek = currentTimeThisPage.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int daysInMonth = YearMonth.of(currentTimeThisPage.getYear(), currentTimeThisPage.getMonthValue()).lengthOfMonth();
        int row = 1;

        // change current month text
        month.setText(currentTimeThisPage.getMonth().toString());
        month.setTextAlignment(TextAlignment.CENTER);

        // each day represent a number
        HashMap<String, Integer> daysColumns = new HashMap<>() {{
            put("MONDAY", 1);
            put("TUESDAY", 2);
            put("WEDNESDAY", 3);
            put("THURSDAY", 4);
            put("FRIDAY", 5);
            put("SATURDAY", 6);
            put("SUNDAY", 7);
        }};

        // ui elements to grid
        for(int i = 0; i < daysInMonth; i++){
            // Loads calenderElement page and get the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/calenderElement.fxml"));
            AnchorPane calenderElement = loader.load();
            CalenderElementController calenderElementController = loader.getController();
            calenderElementController.display(Integer.toString(currentTimeThisPage.get(ChronoField.DAY_OF_MONTH)), TaskService.getTasksExcludingCategories(TaskService.getTasksByDate(TaskService.getTasksByCurrentUser(), DateUtils.getAsMs(currentTimeThisPage)), excludedCategories));

            calenderViewGrid.add(calenderElement, daysColumns.get(currentTimeThisPage.getDayOfWeek().toString()), row);

            // Add week text at grid left column
            if(currentWeek != currentTimeThisPage.get(ChronoField.ALIGNED_WEEK_OF_YEAR)){
                currentWeek = currentTimeThisPage.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

                Text weekText = new Text(Integer.toString(currentWeek));
                weekText.setTextAlignment(TextAlignment.CENTER);
                weekText.setFont(new Font("System", 16));

                calenderViewGrid.add(weekText, 0,row);
            }

            // add critera for jumping to new row
            if(currentTimeThisPage.getDayOfWeek().getValue() == 7){
                row++;
            }

            // increment currendate by one day
            currentTimeThisPage = currentTimeThisPage.plusDays(1);
        }
    }

    /**
     * Pressing SimpleView button in overview-navbar
     * @throws IOException
     */
    public void buttonSimpleView() throws IOException {
        initializeSimpleView();
    }

    /**
     * Pressing CalenderView button in overview-navbar
     * @throws IOException
     */
    public void buttonCalenderView() throws IOException {
        initializeCalenderView(0);
    }

    public void buttonChangeMonthRight() throws IOException {
        initializeCalenderView(1);
    }

    public void buttonChangeMonthLeft() throws IOException {
        initializeCalenderView(2);
    }

    /**
     * Method for switching between navbar pages inside overview page
     * @param nodeId
     */
    public void displayNode(String nodeId){
        for(Node node : content.getChildren()){
            if(node.getId().equals(nodeId)){
                node.setVisible(true);
            } else {
                node.setVisible(false);
            }
        }
    }

    /**
     * Method for adding an arraylist of taskUI to section duethisweek, duenextmonth or duethismonth
     * @param sectionName
     * @param taskUI
     */
    public void addTasksToSimpleView(String sectionName, ArrayList<AnchorPane> taskUI){
        switch (sectionName){
            case "dueThisWeek":
                for(AnchorPane task : taskUI){
                    dueThisWeek.getChildren().add(dueThisWeek.getChildren().size(), task);
                }
                break;
            case "dueNext7Days":
                for(AnchorPane task : taskUI) {
                    dueNext7Days.getChildren().add(dueNext7Days.getChildren().size(), task);
                }
                break;
            case "dueThisMonth":
                for(AnchorPane task : taskUI) {
                    dueThisMonth.getChildren().add(dueThisMonth.getChildren().size(), task);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Method used for removing UI elements from dueThisWeek VBox, dueNext7Days VBox and dueThisMonth VBox
     */
    public void removeTasksFromSimpleView(){
        dueThisWeek.getChildren().removeAll(dueThisWeek.getChildren());
        dueNext7Days.getChildren().removeAll(dueNext7Days.getChildren());
        dueThisMonth.getChildren().removeAll(dueThisMonth.getChildren());
    }

    /**
     * Remove grid elements from calenerViewGrid, we dont remove the elements that have tags (the day of the week headers)
     */
    public void removeGridFromCalenderView(){
        ArrayList<Node> nodesToRemove = new ArrayList<>();

        calenderViewGrid.getChildren().forEach(node -> {
            if(node.getId() == null){
                nodesToRemove.add(node);
            }
        });

        calenderViewGrid.getChildren().removeAll(nodesToRemove);
    }

    /**
     * Method for getting a list of task UI's from a list of task objects
     * @param tasks
     * @return
     * @throws IOException
     */
    public ArrayList<AnchorPane> getTaskUIListFromTaskObjectList(ArrayList<Task> tasks) throws IOException {
        ArrayList<AnchorPane> tasksUI = new ArrayList<>();

        for(Task task : tasks){
            tasksUI.add(getTaskUIFromTaskObject(task));
        }

        return tasksUI;
    }

    /**
     * Method for getting a task UI from a task object
     * @param taskObject A task object is turned into a UI element
     * @throws IOException
     */
    public AnchorPane getTaskUIFromTaskObject(Task taskObject) throws IOException {
        // Loads task page and get the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/task.fxml"));
        AnchorPane task = loader.load();
        TaskController taskController = loader.getController();

        // add id to task anchorpane. A task is identified in TaskDAO as the taskobject's hashcode
        task.setId(Integer.toString(taskObject.hashCode()));

        // use controller to change content of task before adding to to tasks
        taskController.setTaskName(taskObject.getName());
        taskController.setTaskDescription(taskObject.getDescription());
        taskController.setTaskDate(DateUtils.getFormattedFullDate(taskObject.getDeadline()));
        taskController.setTaskPriority(taskObject.getPriority());
        taskController.setTaskId(taskObject.getId());
        taskController.setTaskColor(taskObject.getColor());

        return task;
    }

    /**
     * Adds a listener to scrollpane that makes the width of tasksVBox change when scrollpane size changes
     */
    public void addScrollpaneListener(){
        scrollpane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                dueThisWeek.setPrefWidth(scrollpane.getWidth() - 15);
                dueNext7Days.setPrefWidth(scrollpane.getWidth() - 15);
                dueThisMonth.setPrefWidth(scrollpane.getWidth() - 15);
            }
        });
    }
}
