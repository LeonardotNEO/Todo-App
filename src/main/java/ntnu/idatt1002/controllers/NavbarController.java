package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.LoginService;
import ntnu.idatt1002.service.NotificationService;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;


import java.awt.*;
import java.io.IOException;

/**
 * A class which contains the buttons related to the navigation bar
 */
public class NavbarController {

    @FXML private Pane notificationCountPane;
    @FXML private Text notificationCountText;
    @FXML private static AnchorPane notificationMenuPopup;
    @FXML private static Popup popup;

    /**
     * Initialize method adds notificationHistoryButton and notifications to notificationBell
     * If there is notifications, the notification bell will show UI on how many
     */
    public void initialize(){
        // Notification Bell Count
        if(NotificationService.getActiveAndNotCheckedNotifications().isEmpty()){
            notificationCountPane.setVisible(false);
        } else {
            notificationCountPane.setVisible(true);
            notificationCountText.setText(Integer.toString(NotificationService.getActiveAndNotCheckedNotifications().size()));
        }
    }

    /**
     * Communicate with mainController to update its main-content to dashboard.fxml when button in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonDashboard(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("dashboard");
    }

    /**
     * Communicate with mainController to update its main-content to account.fxml when button in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonAccount(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("account");
    }

    /**
     * Communicate with mainController to update its main-content to overview.fxml when butt0n in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonOverview(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("overview");
    }

    /**
     * Communicate with mainController to update its main-content to about.fxml when butt0n in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonAbout(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("about");
    }

    /**
     * Method for loading login.fxml
     * @param event
     * @throws IOException
     */
    public void buttonLogout(ActionEvent event) throws IOException {
        App.setRoot("newLogin");
        LoginService.logOut();
    }

    /**
     * When notification bell button in navbar is pressed, when show the notification bell popup
     * @param event
     * @throws IOException
     */
    public void buttonNotificationMenuPopup(ActionEvent event) throws IOException {
        showNotificationBellPopup();
    }

    /**
     * Method for displaying notificationbellpopup
     * @throws IOException
     */
    public void showNotificationBellPopup() throws IOException {
        // create notificationMenuPopup
        FXMLLoader loaderPopup = new FXMLLoader(App.class.getResource("/fxml/notificationMenuPopup.fxml"));
        notificationMenuPopup = loaderPopup.load();
        VBox notificationMenuPopupVBox = (VBox) notificationMenuPopup.getChildren().get(1);
        popup = new Popup();
        popup.setAutoHide(true);
        popup.getContent().add(notificationMenuPopup);

        // placement of popup
        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        popup.setX(mouseLocation.getX() - 250);
        popup.setY(mouseLocation.getY());

        // set style of popup
        notificationMenuPopup.getStylesheets().add(App.class.getResource("/css/main.css").toExternalForm());
        notificationMenuPopup.setStyle(UserStateService.getCurrentUser().getTheme());

        // Load notifications into notificationMenuPopup
        loadNotificationsIntoPopup(notificationMenuPopupVBox);

        popup.show(App.getStage());
    }

    /**
     * Method for updating the popup while its open. We remove the notifications that are there, and load them in again.
     * @param popup
     */
    public static void updateNotificationBellPopup(VBox popup){
        removeNotificationsFromPopup(popup);
        loadNotificationsIntoPopup(popup);
    }

    /**
     * Remove UI elements from VBox of popup
     * @param popup
     */
    public static void removeNotificationsFromPopup(VBox popup){
        popup.getChildren().removeAll(popup.getChildren());
    }

    /**
     * Load each notification UI element of all notifications that are not checked and active into the popup
     * @param popup
     */
    public static void loadNotificationsIntoPopup(VBox popup){
        NotificationService.getActiveAndNotCheckedNotifications().forEach(notification -> {
            try {
                // load an empty notificationUI element, and get the controller of it
                FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/notification.fxml"));
                Node notificationNode = loader.load();
                NotificationController notificationController = loader.getController();

                // use the controller to display notification UI
                notificationController.display(notification);

                // add notifcationUI to notificationMenuPopup
                popup.getChildren().add(notificationNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Display text if notifications are empty
        if(NotificationService.getActiveAndNotCheckedNotifications().size() == 0){
            Text emptyMessage = new Text("There are no unchecked notifications!");
            emptyMessage.setFont(new Font(14));

            popup.getChildren().add(emptyMessage);
        }
    }

    /**
     * Get the whole UI for popup
     * @return
     */
    public static AnchorPane getNotificationMenuPopup(){
        return notificationMenuPopup;
    }

    /**
     * Method used for hiding the notificationMenuPopup
     */
    public static void hideNotificationMenuPopup(){
        popup.hide();
    }

}
