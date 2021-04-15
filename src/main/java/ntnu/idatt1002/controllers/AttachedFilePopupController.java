package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import ntnu.idatt1002.App;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;

import java.awt.*;
import java.io.File;

public class AttachedFilePopupController {
    @FXML private Button buttonOpenFile;
    @FXML private Button buttonRemoveFile;
    @FXML private Button buttonCloseFileOptions;
    private String filePath;
    private Task taskWithFile;

    public void buttonOpenFile() {
        try {
            File open = new File(filePath);
            if (!Desktop.isDesktopSupported()) {

            }
            Desktop desktop = Desktop.getDesktop();
            if(open.exists()) {
                desktop.open(open);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void buttonRemoveFile() {
        //task.listOfFiles.remove(filePath);
        TaskService.removeAttachedFile(taskWithFile, filePath);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setTaskWithFiles(Task task){
        this.taskWithFile = task;
    }

    public void updateAttachedFiles() {

    }
}
