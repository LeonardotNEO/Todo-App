package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class OverviewController {

    @FXML private ScrollPane dueThisWeek;
    @FXML private ScrollPane dueNext7Days;
    @FXML private ScrollPane dueThisMonth;
    @FXML private HBox simpleView;
    @FXML private AnchorPane calenderView;
    @FXML private AnchorPane content;

    public void buttonSimpleView() {
        displayNode("simpleView");
    }

    public void buttonCalenderView() {
        displayNode("calenderView");
    }

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
}
