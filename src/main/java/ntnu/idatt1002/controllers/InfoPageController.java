package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class InfoPageController {
    @FXML Pane background;

    /**
     * At initializing of this UI, we display the minimized version
     */
    public void initialize(){
        addClickTaskListener();
    }

    public void clickInfoPage() {

    }

    public void addClickTaskListener(){
        background.setOnMouseClicked(mouseEvent -> {
            clickInfoPage();
        });
    }
}
