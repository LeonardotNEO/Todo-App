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

import javax.swing.*;
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
                try {
                    getInfoPage(page);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            helpMenuVBox.getChildren().add(b);
        });
    }

    public void getInfoPage(String section) throws IOException {
        // Clearing children
        vboxForInfoText.getChildren().clear();

        // Getting information
        HelpSection helpSection = HelpService.getSection(section);

        // Setting header and description
        headerText.setText(helpSection.getSection());
        descriptionText.setText(helpSection.getDescription());

        // Getting fields
        ArrayList<HelpSection.Info> fields = helpSection.getFields();

        for(HelpSection.Info field: fields) {
            // Load controller and AnchorPane
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/infoField.fxml"));
            AnchorPane infoField = loader.load();
            InfoFieldController infoFieldController = loader.getController();

            //AnchorPane p = infoFieldController.getAnchor();

            // Add title if it exists
            if(field.getTitle() != null) {
                infoFieldController.setTitle(field.getTitle());
            } else {
                infoFieldController.hideTitle();
            }

            //Add text if it exists
            if(field.getText() != null) {
                infoFieldController.setText(field.getText());
            } else {
                infoFieldController.hideText();
            }

            //Add image if it exists
            if(field.getImage() != null) {
                InputStream stream = new FileInputStream(field.getImage());
                Image image = new Image(stream);
                infoFieldController.setImageView(image);
            } else {
                infoFieldController.hideImage();
            }

            // Adding it to the vbox
            vboxForInfoText.getChildren().add(vboxForInfoText.getChildren().size(), infoField);
        }
    }
}