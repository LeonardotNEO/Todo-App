package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

/**
 * Meant to be called in a TaskController.
 * Controls a confirmation popup.
 */
public class ConfirmationRepeatDelController {

    private TaskController taskController;
    private String operation;
    @FXML private Text headerText;
    @FXML private Text descriptionText;
    @FXML private CheckBox checkBoxSingle;
    @FXML private CheckBox checkBoxAll;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;


    Popup popup = new Popup();

    public ConfirmationRepeatDelController() {}

    /**
     * Displays a popup for repeatable tasks. For use when clicking "finish" or "delete" task.
     * Changes layout based on the String operation.
     * Takes a TaskController in order to control the task.
     * Takes a String to determine the textFields and outcome of methods.
     * @param task      TaskController for controlling the task.
     * @param operation String for determining outcome of methods.
     * @throws IOException
     */
    public static void display(TaskController task, String operation) throws IOException {
        // New Popup, not window
        FXMLLoader loader = new FXMLLoader(ConfirmationController.class.getResource("/fxml/confirmationRepeatDel.fxml"));
        AnchorPane root = loader.load();

        // set style of popup
        root.setStyle(UserStateService.getCurrentUser().getTheme());

        ConfirmationRepeatDelController controllerInstance = loader.getController();
        controllerInstance.setTaskController(task);

        // So other methods can access the operation string
        controllerInstance.operation = operation;

        if (operation.equalsIgnoreCase("finish")) {
            controllerInstance.headerText.setText("Finish Repeatable");
            controllerInstance.descriptionText.setText("This is a repeatable task. " +
                    "This means that if you decide to just finish this task it will recreate itself with a new deadline");
            controllerInstance.checkBoxSingle.setText("Finish just this (new task will be created)");
            controllerInstance.checkBoxAll.setText("Stop all future tasks (no new tasks will be created)");
        }

        controllerInstance.popup.getContent().add(root);
        controllerInstance.popup.setAutoHide(true);
        controllerInstance.popup.show(App.getStage());
    }

    /**
     * Hides popup.
     * @param event ActionEvent not in use.
     */
    @FXML
    void clickCancelButton(ActionEvent event) {
        popup.hide();
    }

    /**
     *  Checks if checkBoxAll is selected. Calls this.taskController.setTaskIsRepeatable(false) if false.
     *  Checks if checkBoxSingle is selected. If true: checks operation and executes delete task or finish task.
     * @param event ActionEvent
     * @throws IOException
     */
    @FXML
    void clickConfirmButton(ActionEvent event) throws IOException{
        if (checkBoxAll.isSelected()) {
            //Set repeatable to false
            this.taskController.setTaskIsRepeatable(false);
        }
        if (checkBoxSingle.isSelected()) {
            if (operation.equalsIgnoreCase("delete")) {
                //Delete task as usual
                this.taskController.deleteTask(event);
            }
            if (operation.equalsIgnoreCase("finish")) {
                this.taskController.finishTask(event);
            }
        }
        popup.hide();
    }

    /**
     * For setting parent task, in order to access its methods.
     * @param taskController
     */
    public void setTaskController(TaskController taskController) {
        this.taskController = taskController;
    }
}
