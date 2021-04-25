package ntnu.idatt1002.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.LoginService;
import ntnu.idatt1002.service.NotificationService;
import ntnu.idatt1002.service.UserStateService;

import java.awt.*;
import java.io.IOException;

/**
 * Controller for navbar.fxml
 */
public class NavbarController {

    private String currentlySelectedPage = "Dashboard";
    @FXML private Pane notificationCountPane;
    @FXML private Text notificationCountText;
    @FXML private static AnchorPane notificationMenuPopup;
    @FXML private static Popup popup;
    @FXML private HBox navbarHbox;

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

        // set selected button
        setSelectedButton();
    }

    /**
     * Communicate with mainController to update its main-content to dashboard.fxml when button in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonDashboard(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("dashboard");
        currentlySelectedPage = "Dashboard";
        setSelectedButton();
    }

    /**
     * Communicate with mainController to update its main-content to account.fxml when button in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonAccount(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("account");
        currentlySelectedPage = "Account";
        setSelectedButton();
    }

    /**
     * Communicate with mainController to update its main-content to overview.fxml when butt0n in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonOverview(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("overview");
        currentlySelectedPage = "Overview";
        setSelectedButton();
    }

    /**
     * Communicate with mainController to update its main-content to helpPage.fxml when butt0n in navbar is clicked
     * @param event
     * @throws IOException
     */
    public void buttonHelp(ActionEvent event) throws IOException {
        MainController.getInstance().setMainContent("helpPage");
        currentlySelectedPage = "Help";
        setSelectedButton();
    }

    /**
     * Method for loading login.fxml
     * @param event
     * @throws IOException
     */
    public void buttonLogout(ActionEvent event) throws IOException {
        App.setRoot("loginRegister");
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
     *
     */
    public void setSelectedButton(){
        for(Node node : navbarHbox.getChildren()){
            Button button = (Button) node;


            FontAwesomeIconView icon = (FontAwesomeIconView) button.getGraphic();
            //System.out.println(icon.getStyle());

            if(button.getText().equals(currentlySelectedPage)){
                System.out.println(button.getStyleClass());
                button.getStyleClass().clear();
                button.getStyleClass().add("navbarButton-selected");
                //button.setStyle("-fx-text-fill: -fx-text-hover-color");
                //icon.setStyle("-fx-fill: -fx-text-hover-color; -fx-font-family: FontAwesome; -fx-font-size: 1em;");
            } else {
                button.getStyleClass().clear();
                button.getStyleClass().add("navbarButton");
            }
        }
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
                notificationController.display(notification, true);

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
