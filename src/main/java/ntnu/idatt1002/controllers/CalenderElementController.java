package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import ntnu.idatt1002.Task;

import java.util.ArrayList;

public class CalenderElementController {
    @FXML private Text dateText;

    public void display(String date, ArrayList<Task> tasks){
        dateText.setText(date);
    }
}
