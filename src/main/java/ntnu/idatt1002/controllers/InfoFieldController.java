package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class InfoFieldController {
    @FXML ImageView imageView;
    @FXML Text text;
    @FXML Text subTitleText;

    public void hideImage() {
        imageView.setManaged(false);
        imageView.setVisible(false);
    }

    public void hideTitle() {
        subTitleText.setManaged(false);
        subTitleText.setVisible(false);
    }

    public void hideText() {
        subTitleText.setManaged(false);
        subTitleText.setVisible(false);
    }

    public void setImageView(Image image) {
        imageView.setImage(image);
    }



    public void setText(String text) {
        this.text.setText(text);
    }

    public void setTitle(String title) {
        this.subTitleText.setText(title);
    }

}
