package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import ntnu.idatt1002.App;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;

import java.io.IOException;

public class ConfirmationController {

    private TaskController task;
    @FXML private Button noButton;
    @FXML private Button yesButton;
    @FXML private CheckBox checkBox;
    @FXML private Text confirmQuestion;
    @FXML private Text extraText;


    Popup popup = new Popup();


    public void initialize() throws IOException {
        confirmQuestion.setText("Are you sure you want to delete this task?");
        extraText.setText("It wil only go in to the trash bin for now...");
        checkBox.setText("This message is annoying, dont show it again!");
    }

    public static void display(TaskController task) throws IOException {

        // New Popup, not window
        FXMLLoader loader = new FXMLLoader(ConfirmationController.class.getResource("/fxml/confirmation.fxml"));
        AnchorPane root = loader.load();

        ConfirmationController controllerInstance = loader.getController();
        controllerInstance.setTask(task);

        controllerInstance.popup.getContent().add(root);
        controllerInstance.popup.setAutoHide(true);
        controllerInstance.popup.show(App.getStage());



        // Alternative code: New window with Modality

        /*
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/confirmation.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        */

        //noButton.setOnMouseClicked(e -> popup.hide());


    }

    @FXML
    void dontShowAgain(ActionEvent event) {
    }

    @FXML
    void yesOption(ActionEvent event) throws IOException{
        this.task.deleteTask(event);
        popup.hide();
    }

    @FXML
    void noOption(ActionEvent event) {
        popup.hide();
    }

    public void setTask(TaskController task) {
        this.task = task;
    }

}
