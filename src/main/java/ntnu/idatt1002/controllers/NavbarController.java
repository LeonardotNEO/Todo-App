package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    /**
     * Initialize method adds notificationHistoryButton and notifications to notificationBell
     * If there is notifications, the notification bell will show UI on how many
     */
    public void initialize(){
        // Add button at the top that navigates to notificationHistoryPage
        Button newButton = buttonNotificationHistory;
        notificationBell.getItems().add(new CustomMenuItem(newButton));

        // Notification Bell Count
        if(NotificationService.getNotCheckedNotifications().isEmpty()){
            notificationCountPane.setVisible(false);
        } else {
            notificationCountPane.setVisible(true);
            notificationCountText.setText(Integer.toString(NotificationService.getNotCheckedNotifications().size()));
        }

        // Load notifications into menuButton
        NotificationService.getNotCheckedNotifications().forEach(notification -> {
            try {
                // load an empty notificationUI element, and get the controller of it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/notificationElement.fxml"));
                Node node = loader.load();
                NotificationElementController notificationElementController = loader.getController();

                // use the controller to change notification element properties
                notificationElementController.setTitle(notification.getTitle());
                notificationElementController.setDescription(notification.getDescription());
                notificationElementController.setNotificationId(notification.hashCode());

                // add notifcationUI element to notificationBell menu
                notificationBell.getItems().add(new CustomMenuItem(node));
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
