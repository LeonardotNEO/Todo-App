package ntnu.idatt1002.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.HelpSection;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.HelpService;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HelpPageController {
    @FXML private VBox helpMenuVBox;
    @FXML private Text headerText;
    @FXML private Text descriptionText;
    @FXML private VBox vboxForInfoText;
    @FXML private ScrollPane helpMenuScroll;

    public void initialize() {
        fillMenuPage();
    }

    public void fillMenuPage() {
        ArrayList<String> pages = HelpService.getSections();
        pages.forEach(page -> {
            Button b = new Button(page);
            b.setOnAction(event -> {
                getInfoPage(page);
            });
            helpMenuVBox.getChildren().add(b);
        });
    }

    public void getInfoPage(String section) {
        vboxForInfoText.getChildren().clear();
        HelpSection helpSection = HelpService.getSection(section);
        headerText.setText(helpSection.getSection());
        descriptionText.setText(helpSection.getDescription());
        new Text(helpSection.getFields().toString());
        ArrayList<HelpSection.Info> field = helpSection.getFields();
        String info = "";
        for (HelpSection.Info txt: field) {
            Pane pane = new Pane();
            if (txt.getText() != null) {
                Text text = new Text("This is text: " + txt.getText() + "\n");
                pane.getChildren().add(text);
            }
            if (txt.getImage() != null) {
                try {
                    InputStream stream = new FileInputStream(txt.getImage());
                    Image image = new Image(stream);
                    ImageView imageView = new ImageView();
                    //Setting image to the image view
                    imageView.setImage(image);
                    pane.getChildren().add(imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            vboxForInfoText.getChildren().add(vboxForInfoText.getChildren().size(), pane);
        }
    }
}
