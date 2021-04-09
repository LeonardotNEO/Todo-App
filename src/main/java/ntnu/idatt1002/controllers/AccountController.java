package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
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
        if(!RegisterService.checkIfPasswordValidSyntax(editPassword.getText(), editRepeatPassword.getText())){
            errorMessageString += "Password length must be more than 6 characters \n";
        }
        if (!RegisterService.checkIfPasswordValid(editPassword.getText(), editRepeatPassword.getText())){
            errorMessageString += "Password do not match \n";
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
        UserStateService.getCurrentUser().setTheme("themeBlue");
        App.updateThemeCurrentUser();
    }
    public void buttonGreenTheme(){
        UserStateService.getCurrentUser().setTheme("themeGreen");
        App.updateThemeCurrentUser();
    }
    public void buttonRedTheme(){
        UserStateService.getCurrentUser().setTheme("themeRed");
        App.updateThemeCurrentUser();
    }
    public void buttonPinkTheme(){
        UserStateService.getCurrentUser().setTheme("themePink");
        App.updateThemeCurrentUser();
    }
    public void buttonBrownTheme(){
        UserStateService.getCurrentUser().setTheme("themeBrown");
        App.updateThemeCurrentUser();
    }
}
