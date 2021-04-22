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
 * Meant to be called in a TaskController
 */
public class ConfirmationController {

    private TaskController task;
    private String operation;
    @FXML private Button noButton;
    @FXML private Button yesButton;
    @FXML private CheckBox checkBox;
    @FXML private Text confirmQuestion;
    @FXML private Text extraText;


    Popup popup = new Popup();

    public ConfirmationController() {}


    public void initialize() throws IOException {
        confirmQuestion.setText("Are you sure you want to delete this task?");
        extraText.setText("It wil only go in to the trash bin for now...");
        checkBox.setText("This message is annoying, dont show it again!");
    }

    /**
     * Takes a TaskController in order to control the task
     * Takes a String to determine the textFields.
     * @param task
     * @throws IOException
     */
    public static void display(TaskController task, String operation) throws IOException {

        // New Popup, not window
        FXMLLoader loader = new FXMLLoader(ConfirmationController.class.getResource("/fxml/confirmation.fxml"));
        AnchorPane root = loader.load();

        // set style of popup
        root.setStyle(UserStateService.getCurrentUser().getTheme());

        ConfirmationController controllerInstance = loader.getController();
        controllerInstance.setTask(task);

        // So other methods can access the operation string
        controllerInstance.operation = operation;

        // Repeatable task text added as standard
        if (operation.equalsIgnoreCase("delete")) {
            // This is the standard texts in the fxml file and not really needed
            controllerInstance.confirmQuestion.setText("Are you sure you want to delete this task?");
            controllerInstance.extraText.setText("It wil only go in to the trash bin for now...");
            controllerInstance.checkBox.setText("This message is annoying, dont show it again!");
        } else if (operation.equalsIgnoreCase("finish")) {
            controllerInstance.confirmQuestion.setText("Please confirm:");
            controllerInstance.extraText.setText("The task can still be found in the \"finished tasks\" folder," +
                    "\nbut as of now, there is no way to restore it.");
            controllerInstance.yesButton.setText("Yes, move it!");
            controllerInstance.noButton.setText("No, wait!");
        }

        controllerInstance.popup.getContent().add(root);
        controllerInstance.popup.setAutoHide(true);
        controllerInstance.popup.show(App.getStage());
    }

    @FXML
    void dontShowAgain(ActionEvent event) {
    }

    /**
     * Executes operation
     * @param event
     * @throws IOException
     */
    @FXML
    void yesOption(ActionEvent event) throws IOException{
        if (checkBox.isSelected()) {
            if (operation.equalsIgnoreCase("delete")) {
                UserStateService.getCurrentUser().setDeleteTaskDontShowAgainCheckbox(true);
            } else if (operation.equalsIgnoreCase("finish")) {
                UserStateService.getCurrentUser().setFinishTaskDontShowAgainCheckbox(true);
            }
        }
        if (operation.equalsIgnoreCase("delete")) {
            this.task.deleteTask(event);
        } else if (operation.equalsIgnoreCase("finish")) {
            this.task.finishTask(event);
        }
        popup.hide();
    }

    /**
     * Cancels operation
     * @param event
     */
    @FXML
    void noOption(ActionEvent event) {
        if (checkBox.isSelected()) {
            if (operation.equalsIgnoreCase("delete")) {
                UserStateService.getCurrentUser().setDeleteTaskDontShowAgainCheckbox(true);
            } else if (operation.equalsIgnoreCase("finish")) {
                UserStateService.getCurrentUser().setFinishTaskDontShowAgainCheckbox(true);
            }
        }
        popup.hide();
    }

    /**
     * For setting parent task, in order to access its methods.
     * @param task
     */
    public void setTask(TaskController task) {
        this.task = task;
    }

    public Button getNoButton() {
        return noButton;
    }

    public void setNoButton(Button noButton) {
        this.noButton = noButton;
    }

    public void setNoButtonText(String newText) {
        this.noButton.setText(newText);
    }

    public Button getYesButton() {
        return yesButton;
    }

    public void setYesButton(Button yesButton) {
        this.yesButton = yesButton;
    }

    public void setYesButtonText(String newText) {
        this.yesButton.setText(newText);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void setCheckBoxText(String newText) {
        this.checkBox.setText(newText);
    }

    public Text getConfirmQuestion() {
        return confirmQuestion;
    }

    public void setConfirmQuestion(Text confirmQuestion) {
        this.confirmQuestion = confirmQuestion;
    }

    public void setConfirmQuestionText(String newText) {
        this.confirmQuestion.setText(newText);
    }

    public Text getExtraText() {
        return extraText;
    }

    public void setExtraText(Text extraText) {
        this.extraText = extraText;
    }

    public void setExtraText(String newText) {
        this.extraText.setText(newText);
    }
}
