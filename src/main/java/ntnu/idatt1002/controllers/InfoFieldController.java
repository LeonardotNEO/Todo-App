package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * Controller that manages a info field.
 */
public class InfoFieldController {
    @FXML ImageView imageView;
    @FXML Text text;
    @FXML Text subTitleText;

    /**
     * Method to hide the image from the screen
     */
    public void hideImage() {
        imageView.setManaged(false);
        imageView.setVisible(false);
    }

    /**
     * Method to hide the title from the screen
     */
    public void hideTitle() {
        subTitleText.setManaged(false);
        subTitleText.setVisible(false);
    }

    /**
     * Method to hide the text from the screen
     */
    public void hideText() {
        subTitleText.setManaged(false);
        subTitleText.setVisible(false);
    }

    /**
     * Method to set the image of the imageView
     * @param image Image to be added
     */
    public void setImageView(Image image) {
        imageView.setImage(image);
    }


    /**
     * Method that sets the text of this field
     * @param text String of text to be added
     */
    public void setText(String text) {
        this.text.setText(text);
    }

    /**
     * Method that sets the title text of this field
     * @param title String title of the field
     */
    public void setTitle(String title) {
        this.subTitleText.setText(title);
    }
}
