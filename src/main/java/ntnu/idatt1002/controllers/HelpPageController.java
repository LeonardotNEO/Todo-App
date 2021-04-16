package ntnu.idatt1002.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;

import java.io.IOException;
import java.util.ArrayList;

public class HelpPageController {
    @FXML private VBox helpMenuVBox;
    @FXML private Text headerText;
    @FXML private Text descriptionText;
    @FXML private VBox vboxForInfoText;
    @FXML private VBox vboxForImages;
    @FXML private ScrollPane scrollpane;

    /**
     * Method that runs when this controller is initialized
     */
    public void initialize(){
        addScrollpaneListener();
    }

    /**
     * Method for adding a Task UI element to tasksVBox
     * @param taskObject A task object is turned into a UI element
     * @throws IOException
     */
    public void addTask(Task taskObject) throws IOException {
        // Loads task page and get the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/infoPage.fxml"));
        AnchorPane infoPage = loader.load();
        TaskController helpPageController = loader.getController();

        // add id to task anchorpane. A task is identified in TaskDAO as the taskobject's hashcode
        task.setId(Integer.toString(taskObject.hashCode()));

        // use controller to display task
        taskController.display(taskObject);

        // adding the infoPage to helpPage
        helpMenuVBox.getChildren().add(helpMenuVBox.getChildren().size(), infoPage);
    }

    /**
     * Uses helper-method addTask to add an arraylist of tasks
     * @param tasks
     */
    public void addTasks(ArrayList<Task> tasks){
        if(tasks != null){
            tasks.forEach(t -> {
                try {
                    addTask(t);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Adds a listener to scrollpane that makes the width of tasksVBox change when scrollpane size changes
     */
    public void addScrollpaneListener(){
        scrollpane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                helpMenuVBox.setPrefWidth(scrollpane.getWidth() - 15);
            }
        });
    }
}
