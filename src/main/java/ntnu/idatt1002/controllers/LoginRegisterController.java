package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.model.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.service.LoginService;
import ntnu.idatt1002.service.RegisterService;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller for loginRegister.fxml
 */
public class LoginRegisterController {
    // general
    @FXML private Text pageHeader;
    @FXML private Text bottomText;
    @FXML private Button buttonNavigateToRegister;
    @FXML private Button buttonNavigateToLogin;

    // login
    @FXML private AnchorPane loginContent;
    @FXML private TextField usernameLogin;
    @FXML private PasswordField passwordLogin;
    @FXML private CheckBox rememberMeLogin;
    @FXML private Label errorMessageLogin;

    // login with accounts
    @FXML private AnchorPane loginAccountsContent;
    @FXML private VBox userVbox;
    @FXML private ScrollPane scrollPane;

    // register
    @FXML private AnchorPane registerContent;
    @FXML private TextField usernameRegister;
    @FXML private PasswordField passwordRegister;
    @FXML private PasswordField repeatPasswordRegister;
    @FXML private CheckBox noPasswordRegister;
    @FXML private CheckBox rememberMeRegister;
    @FXML private Label errorMessageRegister;

    /**
     * When loginRegister is loaded, we load displayWithAccounts part of loginRegister page
     * @throws IOException
     */
    public void initialize() throws IOException {
        displayLoginWithAccounts();
    }

    /**
     * Method for displaying login part of loginRegister fxml.
     * We set loginWithAccount and register to false and login to true
     */
    public void displayLogin(String username) throws IOException {
        pageHeader.setText("Login");
        buttonNavigateToRegister.setVisible(true);
        buttonNavigateToLogin.setVisible(false);

        loginContent.setVisible(true);
        registerContent.setVisible(false);
        loginAccountsContent.setVisible(false);

        bottomText.setVisible(true);

        usernameLogin.setText(username);
    }

    /**
     * Method for displaying loginWithAccount part of loginRegister fxml.
     * We set login and register to false and loginWithAccount to true
     * If password is empty (No password is set) it will auto log inn instead of going through the login page
     */
    public void displayLoginWithAccounts(){
        pageHeader.setText("Accounts");
        buttonNavigateToRegister.setVisible(true);
        buttonNavigateToLogin.setVisible(false);

        loginContent.setVisible(false);
        registerContent.setVisible(false);
        loginAccountsContent.setVisible(true);

        bottomText.setVisible(true);

        //Clear data
        userVbox.getChildren().clear();

        // Populate the Vbox with users
        ArrayList<User> users = UserDAO.list();
        users.forEach(u -> {
            // Create user pane
            AnchorPane pane = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            UserController controller = loader.getController();

            // Set login button to run the loginButtonEvent method when clicked
            controller.login.setOnAction(actionEvent -> {
                try {
                    if(u.getPassword().isEmpty()) {
                        LoginService.login(u.getUsername(), true);
                    } else {
                        displayLogin(u.getUsername());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Set username
            controller.setUsername(u.getUsername());
            // Add the created pane to the Vbox
            userVbox.getChildren().add(pane);
        });
    }

    /**
     * Method for displaying register part of loginRegister fxml.
     * We set login and loginAccounts to false and register to true
     */
    public void displayRegister(){
        pageHeader.setText("Register");
        buttonNavigateToRegister.setVisible(false);
        buttonNavigateToLogin.setVisible(true);

        loginContent.setVisible(false);
        registerContent.setVisible(true);
        loginAccountsContent.setVisible(false);
    }

    /**
     * When register button in footer is pressed we display register
     */
    public void buttonNavigateToRegister(){
        displayRegister();
    }

    /**
     * When login button in footer is pressed we display login with accounts
     */
    public void buttonNavigateToLogin() {
        displayLoginWithAccounts();
    }

    /**
     * Method that runs when register button is pressed.
     * Used for validating inputs and logging in.
     * @throws IOException
     */
    public void buttonLogin() throws IOException {
        String errorMessage = "";
        if(!LoginService.checkIfLoginSyntaxValid(usernameLogin.getText())) {
            errorMessage += "* Username cant be empty\n";
        }

        if(errorMessage.isEmpty()) {
            boolean loginValid = LoginService.checkIfLoginValid(usernameLogin.getText(), passwordLogin.getText());

            if(loginValid) {
                LoginService.login(usernameLogin.getText(), rememberMeLogin.isSelected());
            } else {
                errorMessage += "* Username or password is wrong";
            }
        }
        errorMessageLogin.setText(errorMessage);
    }

    /**
     * Method that runs when register button is pressed.
     * Used for validating inputs and registering.
     * @throws IOException
     */
    public void buttonRegister() throws IOException {
        String errorMessage = "";

        if(!RegisterService.checkIfUsernameValidSyntax(usernameRegister.getText())) {
            errorMessage += "* Username must be more than 0 characters \n";
        }
        if(!RegisterService.checkIfUsernameValid(usernameRegister.getText())) {
            errorMessage += "* Username already exists, choose antoher username\n";
        }

        if(noPasswordRegister.isSelected()){
            passwordRegister.setText("");
        } else {
            if(!RegisterService.checkIfPasswordValid(passwordRegister.getText(), repeatPasswordRegister.getText())) {
                errorMessage += "* The passwords do not match \n";
            } else if(!RegisterService.checkIfPasswordValidSyntax(passwordRegister.getText(), repeatPasswordRegister.getText())) {
                errorMessage += "* Password must be more than 6 characters \n";
            }
        }

        if(errorMessage.isEmpty()) {
            boolean userSuccessfullyRegistered =  RegisterService.registerNewUser(
                        usernameRegister.getText(),
                        passwordRegister.getText(),
                        rememberMeRegister.isSelected()
                );

            if(userSuccessfullyRegistered) {
                LoginService.login(usernameRegister.getText(), rememberMeRegister.isSelected());
            } else {
                errorMessage += "* Error in saving user to storage! \n";
            }
        }
        errorMessageRegister.setText(errorMessage);
    }
}
