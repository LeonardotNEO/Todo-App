package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Controller that represents a user.fxml file
 */
public class UserController {
    @FXML Text username;
    @FXML Button login;

    /**
     * Sets a name in the username field
     * @param username username to be added
     */
    public void setUsername (String username) {
        this.username.setText(username);
    }


}
