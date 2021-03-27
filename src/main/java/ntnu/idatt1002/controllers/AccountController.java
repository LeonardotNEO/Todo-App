package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import ntnu.idatt1002.service.UserStateService;

/**
 * A class which contains the buttons related to a user account
 */
public class AccountController {

    @FXML private Text username;
    @FXML private Text dateCreated;
    @FXML private Text taskCount;
    @FXML private Text categoryCount;


    public void initialize(){
        // when page is initialized, set text fields to appropriate values
        username.setText(UserStateService.getCurrentUserUsername());
        dateCreated.setText("");
        taskCount.setText("");
        categoryCount.setText("");
    }

    /**
     * Updates main content to resetPassword page
     * @param event
     */
    public void resetPasswordButton(ActionEvent event){

    }

    /**
     * Updates main content to resetEmail page
     * @param event
     */
    public void resetEmailButton(ActionEvent event){

    }

}
