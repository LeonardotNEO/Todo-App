package ntnu.idatt1002.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

public class OverviewController {

    @FXML private VBox dueThisWeek;
    @FXML private VBox dueNext7Days;
    @FXML private VBox dueThisMonth;
    @FXML private ScrollPane scrollpane;

    @FXML private GridPane calenderView;

    @FXML private AnchorPane content;

    /**
     * Initialize method used for loading the SimpleView as default when loading "Overview"
     * @throws IOException
     */
    public void initialize() throws IOException {
        initializeSimpleView();
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
        addTasksToSimpleView("dueThisWeek", getTaskUIListFromTaskObjectList(TaskService.getTasksBetweenDates(TaskService.getTasksByCurrentUser(), DateUtils.getAsMs(now), DateUtils.getAsMs(nowPlusDaysUntilNextMonday))));

        // The next 7 days date
        LocalDateTime nowPlus7Days = now.plusDays(7);
        addTasksToSimpleView("dueNext7Days", getTaskUIListFromTaskObjectList(TaskService.getTasksBetweenDates(TaskService.getTasksByCurrentUser(), DateUtils.getAsMs(now), DateUtils.getAsMs(nowPlus7Days))));

        // The remaining days of the current month
        LocalDateTime endOfMonthDate = now.with(TemporalAdjusters.lastDayOfMonth());
        addTasksToSimpleView("dueThisMonth", getTaskUIListFromTaskObjectList(TaskService.getTasksBetweenDates(TaskService.getTasksByCurrentUser(), DateUtils.getAsMs(now), DateUtils.getAsMs(endOfMonthDate))));
    }

    /**
     * Method used for loading CalenderView
     */
    public void initializeCalenderView(){
        // display this node
        displayNode("calenderView");

        // Time now
        LocalDateTime currentDate = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());

        int startWeek = currentDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int currentWeek = -1;
        int daysInMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonthValue()).lengthOfMonth();
        int row = 1;

        HashMap<String, Integer> daysColumns = new HashMap<>() {{
            put("MONDAY", 1);
            put("TUESDAY", 2);
            put("WEDNESDAY", 3);
            put("THURSDAY", 4);
            put("FRIDAY", 5);
            put("SATURDAY", 6);
            put("SUNDAY", 7);
        }};

        for(int i = 0; i < daysInMonth; i++){
            System.out.println(currentDate.getDayOfWeek().getValue());

            calenderView.add(new Text("Day: " + currentDate.get(ChronoField.DAY_OF_MONTH)), daysColumns.get(currentDate.getDayOfWeek().toString()), row);

            if(currentWeek != currentDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR)){
                currentWeek = currentDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
                calenderView.add(new Text("week: " + currentWeek), 0,row);
            }

            if(currentDate.getDayOfWeek().getValue() == 7){
                row++;
            }

            currentDate = currentDate.plusDays(1);
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
        initializeCalenderView();
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
                dueThisWeek.setPrefWidth(scrollpane.getWidth() - 25);
                dueNext7Days.setPrefWidth(scrollpane.getWidth() - 25);
                dueThisMonth.setPrefWidth(scrollpane.getWidth() - 25);
            }
        });
    }
}
