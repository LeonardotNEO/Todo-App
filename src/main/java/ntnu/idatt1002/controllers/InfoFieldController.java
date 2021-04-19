package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class InfoFieldController {
    @FXML ImageView imageView;
    @FXML Text text;
    @FXML AnchorPane anchor;

    public void initialize() {
        System.out.println("I am listening");
        text.wrappingWidthProperty().bind(anchor.widthProperty());
    }
    public void setImageView(Image image) {
        imageView.setImage(image);
    }

    public AnchorPane getAnchor() {
        return anchor;
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
