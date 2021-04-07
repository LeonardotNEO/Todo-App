package ntnu.idatt1002.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;

public class OverviewController {

    @FXML private VBox dueThisWeek;
    @FXML private VBox dueNext7Days;
    @FXML private VBox dueThisMonth;
    @FXML private ScrollPane scrollpane;
    @FXML private HBox simpleView;

    @FXML private AnchorPane calenderView;

    @FXML private AnchorPane content;

    public void initialize() throws IOException {
        addScrollpaneListener();

        addTasksToSimpleView("dueThisWeek", getTaskUIListFromTaskObjectList(TaskService.getTasksByCategory("Finished tasks")));
        addTasksToSimpleView("dueNext7Days", getTaskUIListFromTaskObjectList(TaskService.getTasksByCategory("Trash bin")));
        addTasksToSimpleView("dueThisMonth", getTaskUIListFromTaskObjectList(TaskService.getTasksByCurrentUser()));
    }

    public void buttonSimpleView() {
        displayNode("simpleView");
    }

    public void buttonCalenderView() {
        displayNode("calenderView");
    }

    /**
     * Method for switching between navbar pages inside overview page
     * @param nodeId
     */
    public void displayNode(String nodeId){
        for(Node node : content.getChildren()){
            if(node.getId().equals(nodeId)){
                node.setVisible(true);
                node.setVisible(true);
            } else {
                node.setVisible(false);
                node.setManaged(false);
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
            case "dueNextMonth":
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
