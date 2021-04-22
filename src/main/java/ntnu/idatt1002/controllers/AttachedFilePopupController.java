package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.File;

/**
 * A class which contains the buttons related to attachFilePopup.
 * It contains a button for opening a file, closing a file and removing a file.
 */
public class AttachedFilePopupController {
    @FXML private Button buttonOpenFile;
    @FXML Button buttonRemoveFile;
    @FXML Button buttonCloseFileOptions;
    private String filePath;

    /**
     * A button which opens the File from filePath.
     */
    public void buttonOpenFile() {
        try {
            File open = new File(filePath);
            Desktop.isDesktopSupported();
            Desktop desktop = Desktop.getDesktop();
            if(open.exists()) {
                desktop.open(open);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * A method to set the filePath.
     *
     * @param filePath the new file path to be set.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
