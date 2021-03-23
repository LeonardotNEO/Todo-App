package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import ntnu.idatt1002.service.UserStateService;

public class AccountController {

    @FXML private Text usernameText;
    @FXML private Text dashboardsText;
    @FXML private Text dateCreatedText;


    public void initialize(){
        // when page is initialized, set textfields to appropraite values (f.eks usernameText.setText())
        usernameText.setText(UserStateService.getCurrentUserUsername());
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
