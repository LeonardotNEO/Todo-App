package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import ntnu.idatt1002.App;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.service.*;
import ntnu.idatt1002.utils.DateUtils;

import java.io.IOException;

/**
 * A class which contains the buttons related to a user account
 */
public class AccountController {

    // information
    @FXML private Text username;
    @FXML private Text dateCreated;
    @FXML private Text taskCount;
    @FXML private Text categoryCount;
    @FXML private VBox information;

    // edit account
    @FXML private VBox editUserPage;
    @FXML private TextField editUsername;
    @FXML private TextField editPassword;
    @FXML private TextField editRepeatPassword;
    @FXML private Label errorMessage;
    @FXML private CheckBox noPasswordRegister;

    // general
    @FXML private AnchorPane content;
    @FXML private HBox bottomButtons;

    /**
     * When accountcontroller is initialized, we load the information page
     */
    public void initialize(){
        // load information page
        showInformationPage();
    }

    /**
     * Method runs when confirmEditUser is clicked.
     * Check if username and password syntax is valid, and display errormessage.
     * Display successful message if successful.
     * @param event
     */
    public void confirmEditUser(ActionEvent event){
        String errorMessageString = "";

        // username
        if(!RegisterService.checkIfUsernameValid(editUsername.getText()) && !UserStateService.getCurrentUserUsername().equals(editUsername.getText())){
            errorMessageString += "That username is not available \n";
        }
        if(!RegisterService.checkIfUsernameValidSyntax(editUsername.getText())){
            errorMessageString += "Username cant be empty \n";
        }

        // password
        if(noPasswordRegister.isSelected()) {
            editPassword.setText("");
        } else {
            if(!RegisterService.checkIfPasswordValidSyntax(editPassword.getText(), editRepeatPassword.getText())){
                errorMessageString += "Password length must be more than 6 characters \n";
            }
            if (!RegisterService.checkIfPasswordValid(editPassword.getText(), editRepeatPassword.getText())){
                errorMessageString += "Password do not match \n";
            }
        }

        if(errorMessageString.isEmpty()){
            UserStateService.getCurrentUser().setUsername(editUsername.getText());
            UserStateService.getCurrentUser().setPassword(editPassword.getText());

            errorMessage.setText("Account successfully edited!");
            errorMessage.setTextFill(Paint.valueOf("green"));
        } else {
            errorMessage.setText(errorMessageString);
            errorMessage.setTextFill(Paint.valueOf("red"));
        }
    }

    /**
     * Button editAccount in information page
     * @param event
     */
    public void buttonEditUser(ActionEvent event){
        showEditAccountPage();
    }

    /**
     * Button cancel in edit account page
     * @param event
     */
    public void buttonCancel(ActionEvent event){
        showInformationPage();
    }

    /**
     * Method used for showing the information page
     */
    public void showInformationPage(){
        displayNode(content, "information");
        displayNode(bottomButtons, "buttonEditUser");

        username.setText(UserStateService.getCurrentUserUsername());
        dateCreated.setText(DateUtils.getFormattedDate(UserStateService.getCurrentUser().getDateCreated()));
        taskCount.setText(Integer.toString(TaskService.getTasksByCurrentUser().size()));
        categoryCount.setText(Integer.toString(CategoryService.getCategoriesCurrentUserWithoutPremades().size()));
    }

    /**
     * Method used for showing the edit account page
     */
    public void showEditAccountPage(){
        displayNode(content,"editUserPage");
        displayNode(bottomButtons, "buttonCancel");

        editUsername.setText(UserStateService.getCurrentUserUsername());
        errorMessage.setText("");
    }

    /**
     * Display this page under anchorpane content in account page
     * @param nodeId
     */
    public void displayNode(Parent parentNode, String nodeId){
        for(Node node : parentNode.getChildrenUnmodifiable()){
            if(node.getId().equals(nodeId)){
                node.setVisible(true);
                node.setManaged(true);
            } else {
                node.setVisible(false);
                node.setManaged(false);
            }
        }
    }

    /**
     * Press confirm button if enter is pressed
     */
    public void onKeyPressed(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            confirmEditUser(new ActionEvent());
        }
    }

    /**
     * Change the users theme in their user settings variables, and then update UI
     */
    public void buttonBlueTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #001021; -fx-color-2: #001933 ; -fx-color-3: #00254d; -fx-color-4: orange;");
    }
    public void buttonGreenTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #004d00; -fx-color-2: #006600; -fx-color-3: #008000; -fx-color-4: orange;");
    }
    public void buttonRedTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #660011; -fx-color-2: #800015; -fx-color-3: #99001a; -fx-color-4: orange;");
    }
    public void buttonPinkTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #ff99aa; -fx-color-2: #ffb3bf; -fx-color-3: #ffc0cb; -fx-color-4: black;");
    }
    public void buttonBrownTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #3d1010; -fx-color-2: #511515; -fx-color-3: #651b1b; -fx-color-4: orange;");
    }
    public void buttonPurpleTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #4d004d; -fx-color-2: #660066; -fx-color-3: #800080; -fx-color-4: orange;");
    }
}
