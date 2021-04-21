package ntnu.idatt1002;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ntnu.idatt1002.controllers.MainController;
import ntnu.idatt1002.dao.UserStateDAO;
import ntnu.idatt1002.service.LoginService;
import ntnu.idatt1002.service.UpdateService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;
import java.util.Timer;

/**
 * To-do app main class
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    /**
     * A method to start the program
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        // set stage objectvariable
        this.stage = stage;

        // Set icon
        Image logo = new Image(getClass().getResourceAsStream("/images/displayLogo.png"));
        stage.getIcons().add(logo);

        // Load custom font, Roboto
        Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Roboto/Roboto-Light.ttf"), 14);

        // create userstate.ser if it doesnt exist
        if(!UserStateDAO.fileExists()){
            UserStateDAO.setUserState(null, null, null, false);
        }

        // Start timer for checking for updates every 5 seconds
        UpdateService.start();

        // check if userState contains a saved user, loads login if not
        if(UserStateService.checkIfUserState()){
            scene = new Scene(loadFXML("main"));
            LoginService.login(UserStateService.getCurrentUser().getUsername(), true);
        } else {
            scene = new Scene(loadFXML("loginRegister"));
        }

        // fill stage with the scene choosen above. Set properties of the stage.
        stage.setScene(scene);
        stage.setTitle("ToDo-App");
        stage.setMinHeight(640);
        stage.setMinWidth(1020);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * When application is stopped (not by logout button), we check if UserState is false, if it is we logOut()
     */
    public void stop(){
        // check if we should log the user out when the application is stopped based on user settings
        if(UserStateService.checkIfUserState()){
            if(!UserStateService.getCurrentUser().isRememberMe()){
                LoginService.logOut();
            }
        }

        // stop timer in UpdateService
        UpdateService.stop();
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {launch();}

    /**
     * Method that uses loadFXML method to load fxml file to the scene
     * @param fxml
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Method used for loading fxml file
     * @param fxml the name of the fxml file
     * @return
     * @throws IOException
     */
    public static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        return parent;
    }

    /**
     * Method for fetching the scene
     * @return
     */
    public static Scene getCurrentScene(){
        return scene;
    }

    /**
     * Method for updating the theme according to current user
     */
    public static void updateThemeCurrentUser(String changes){
        UserStateService.getCurrentUser().setTheme(changes);
        App.getCurrentScene().getRoot().setStyle(changes);
    }

    /**
     * Method for fetching the stage
     * @return
     */
    public static Stage getStage() {
        return stage;
    }
}