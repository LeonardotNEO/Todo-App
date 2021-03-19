package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.NotificationService;
import ntnu.idatt1002.service.UserStateService;


import java.io.IOException;

public class NavbarController {

    @FXML private MenuButton notificationBell;
    @FXML private Button buttonNotificationHistory;
    @FXML private Pane notificationCountPane;
    @FXML private Text notificationCountText;

    public void initialize(){
        Button newButton = buttonNotificationHistory;
        notificationBell.getItems().add(new CustomMenuItem(newButton));

        if(NotificationService.getNotCheckedNotifications().isEmpty()){
            notificationCountPane.setVisible(false);
        } else {
            notificationCountPane.setVisible(true);
            notificationCountText.setText(Integer.toString(NotificationService.getNotCheckedNotifications().size()));
        }

        NotificationService.getNotCheckedNotifications().forEach(notification -> {
            try {
                Pane element = FXMLLoader.load(getClass().getResource("/fxml/notificationElement.fxml"));
                element.setId(Integer.toString(notification.hashCode()));

                Text title = (Text) element.lookup("#title");
                title.setText(notification.getTitle());

                Label description = (Label) element.lookup("#description");
                description.setText(notification.getDescription());

                notificationBell.getItems().add(new CustomMenuItem(element));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Communicate with mainController to updates its main-content to dashboard.fxml when button in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonDashboard(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("dashboard");
    }

    /**
     * Communicate with mainController to updates its main-content to account.fxml when button in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonAccount(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("account");
    }

    /**
     * Communicate with mainController to updates its main-content to notificationHistory.fxml when button in notificationBell-menu is clicked
     * @param event
     * @throws IOException
     */
    public void buttonNotificationHistory(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("notificationHistory");
    }

    public void notificationBellClicked(ActionEvent event) throws IOException {
        System.out.println("clicked");
    }

    /**
     * method for loading login.fxml
     * @param event
     * @throws IOException
     */
    public void buttonLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
        UserStateService.setCurrentUser(null);
    }



}
