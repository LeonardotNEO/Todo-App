package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller that handles the initial login page that the user meets when opening the program.
 */
public class NewLoginController {
    @FXML Text pageHeader;
    @FXML AnchorPane mainContent;
    @FXML VBox userVbox;
    @FXML Button backToHome;
    @FXML ScrollPane scrollPane;

    /**
     * Method that initializes the FXML file and populates it with the required data
     */
    public void initialize() {
        //Clear data
        mainContent.getChildren().clear();
        userVbox.getChildren().clear();

        // Set top and bottom padding (to make it so it does not go out of the vbox)
        userVbox.setPadding(new Insets(5, 0, 15, 0));
        pageHeader.setText("Login");

        // Add scrollPane to main content
        mainContent.getChildren().add(scrollPane);

        // Hide back button
        backToHome.setVisible(false);
        backToHome.setManaged(false);

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
                    loginButtonEvent(u.getUsername());
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
     * Method that handles the creation of the register new account page.
     * @throws IOException
     */
    public void registerButtonEvent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registerLogin.fxml"));
        AnchorPane pane = loader.load();
        LoginRegisterController controller = loader.getController();

        // set header to register
        pageHeader.setText("Register");

        // Make back button visible
        backToHome.setVisible(true);
        backToHome.setManaged(true);

        // Clear content inn mainContent and add the new page content
        mainContent.getChildren().clear();
        mainContent.getChildren().add(pane);

        //Initialize the register content
        controller.initializeRegister();
    }

    /**
     * Makes the mainContent ready for the login content added by initializeLogin in registerLogin
     * @param username
     * @throws IOException
     */
    public void loginButtonEvent (String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registerLogin.fxml"));
        AnchorPane pane = loader.load();
        LoginRegisterController controller = loader.getController();

        // Set header page to login
        pageHeader.setText("Login");

        // Make the back button visible
        backToHome.setVisible(true);
        backToHome.setManaged(true);

        // Clear main content and add the new page content
        mainContent.getChildren().clear();
        mainContent.getChildren().add(pane);

        // Set the username and initialize the login
        controller.setUsername(username);
        controller.initializeLogin();
    }

    /**
     * Resets the whole page back to the initial content
     */
    public void backButton() {
        initialize();
    }
}
