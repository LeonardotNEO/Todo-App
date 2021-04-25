package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.*;
import ntnu.idatt1002.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for account.fxml
 */
public class AccountController {

    // information
    @FXML private Text username;
    @FXML private Text dateCreated;
    @FXML private Text taskCount;
    @FXML private Text categoryCount;
    @FXML private VBox information;
    @FXML private HBox backgrounds;
    @FXML private BorderPane background;

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

        // set background of page
        setBackground(UserStateService.getCurrentUser().getCurrentlySelectedBackground());
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
     * Method for handling when the "add background" button is clicked
     * @param event
     * @throws IOException
     */
    public void buttonAddBackground(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Add background image");
        File file = fileChooser.showOpenDialog(App.getStage());

        ImageService.addImageToCurrentUser(file);

        initialize();
    }

    /**
     * Method for displaying the current users images to the backgrounds HBox
     * Add listener to each button, to change the background when clicked.
     */
    public void displayBackgrounds(){
        // remove background if there are any
        removeBackgroundImages();

        ArrayList<File> files = ImageService.getAllImagesCurrentUser();

        files.forEach(file -> {
            try {
                Button button = (Button) App.loadFXML("backgroundButton");
                button.setOnAction(event -> {
                    UserStateService.getCurrentUser().setCurrentlySelectedBackground("-fx-background-image: url(\"" + file.toURI().toString() + "\");-fx-background-size: stretch; -fx-background-color: #e6e6e6;");
                    initialize();
                });
                ImageView imageView = (ImageView) button.getGraphic();
                imageView.setImage(new Image(file.toURI().toString()));

                backgrounds.getChildren().add(button);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method for removing all background UI buttons from backgrounds HBox.
     */
    public void removeBackgroundImages(){
        backgrounds.getChildren().removeAll(backgrounds.getChildren());
    }

    /**
     * Method for handling when the "None" background button is clicked.
     */
    public void buttonNoBackground(){
        UserStateService.getCurrentUser().setCurrentlySelectedBackground("-fx-background-color: #e6e6e6;");
        initialize();
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
        displayBackgrounds();
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

    public void setBackground(String style){
        background.setStyle(style);
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
     * When button is clicked, we change the theme of the current user (Blue)
     */
    public void buttonBlueTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #001021; -fx-color-2: #001933 ; -fx-color-3: #00254d; -fx-text-hover-color: orange; -fx-color-text-color: white");
    }

    /**
     * When button is clicked, we change the theme of the current user (Green)
     */
    public void buttonGreenTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #004d00; -fx-color-2: #006600; -fx-color-3: #008000; -fx-text-hover-color: orange; -fx-color-text-color: white");
    }

    /**
     * When button is clicked, we change the theme of the current user (Red)
     */
    public void buttonRedTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #660011; -fx-color-2: #800015; -fx-color-3: #99001a; -fx-text-hover-color: orange; -fx-color-text-color: white");
    }

    /**
     * When button is clicked, we change the theme of the current user (Pink)
     */
    public void buttonPinkTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #ff99aa; -fx-color-2: #ffb3bf; -fx-color-3: #ffc0cb; -fx-text-hover-color: #404040; -fx-color-text-color: black");
    }

    /**
     * When button is clicked, we change the theme of the current user (Brown)
     */
    public void buttonBrownTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #3d1010; -fx-color-2: #511515; -fx-color-3: #651b1b; -fx-text-hover-color: orange; -fx-color-text-color: white");
    }

    /**
     * When button is clicked, we change the theme of the current user (Purple)
     */
    public void buttonPurpleTheme(){
        App.updateThemeCurrentUser("-fx-color-1: #4d004d; -fx-color-2: #660066; -fx-color-3: #800080; -fx-text-hover-color: orange; -fx-color-text-color: white");
    }
}
