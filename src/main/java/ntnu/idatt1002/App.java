package ntnu.idatt1002;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ntnu.idatt1002.controllers.MainController;
import ntnu.idatt1002.dao.UserStateDAO;
import ntnu.idatt1002.service.LoginService;
import ntnu.idatt1002.service.UpdateService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

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
            scene = new Scene(loadFXML("login"));
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
        if(UserStateService.checkIfUserState()){
            if(!UserStateService.getCurrentUser().isRememberMe()){
                LoginService.logOut();
            }
        }
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

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
     * Method for updating the theme according to current user
     */
    public static String updateThemeCurrentUser(String theme){
        UserStateService.getCurrentUser().setTheme(theme);
        String style = "";

        switch (theme){
            case "blue":
                style = "color-1: #001021; color-2: #001933 ; color-3: #00254d; color-4: #ffa500;";
                break;
            case "green":
                style = "color-1: #004d00; color-2: #006600; color-3: #008000; color-4: #ffa500;";
                break;
            case "red":
                style = "color-1: #660011; color-2: #800015; color-3: #99001a; color-4: #ffa500;";
                break;
            case "pink":
                style = "color-1: #ff99aa; color-2: #ffb3bf; color-3: #ffc0cb; color-4: #ffa500;";
                break;
            case "brown":
                style = "color-1: #3d1010; color-2: #511515; color-3: #651b1b; color-4: #ffa500;";
                break;
            case "purple":
                style = "color-1: #4d004d; color-2: #660066; color-3: #800080; color-4: #ffa500;";
                break;
            default:
                break;
        }

        App.getCurrentScene().getRoot().setStyle(style);
        return style;
    }

    /**
     * Method for fetching the scene
     * @return
     */
    public static Scene getCurrentScene(){
        return scene;
    }

    /**
     * Method for fetching the stage
     * @return
     */
    public static Stage getStage() {
        return stage;
    }
}