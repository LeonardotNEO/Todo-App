package ntnu.idatt1002;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ntnu.idatt1002.dao.UserStateDAO;
import ntnu.idatt1002.service.LoginService;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

/**
 * To-do app main class
 */
public class App extends Application {

    private static Scene scene;

    /**
     * A method to start the program
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load custom font, Roboto
        Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Roboto/Roboto-Light.ttf"), 14);

        // create userstate.ser if it doesnt exist
        if(!UserStateDAO.fileExists()){
            UserStateDAO.setUserState(null, null, null, false);
        }

        // check if userState contains a saved user, loads login if not
        if(UserStateService.checkIfUserState()){
            scene = new Scene(loadFXML("main"));
        } else {
            scene = new Scene(loadFXML("login"));
        }

        // fill stage with the scene choosen above. Set properties of the stage.
        stage.setScene(scene);
        stage.setTitle("ToDo-App");
        stage.setMinHeight(820);
        stage.setMinWidth(1020);
        stage.setHeight(820);
        stage.setWidth(1020);
        stage.show();
    }

    /**
     * When application is stopped (not by logout button), we check if UserState is false, if it is we logOut()
     */
    public void stop(){
        if(UserStateService.checkIfUserState()){
            if(!UserStateService.getCurrentUserRememberMe()){
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
    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

}