package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import ntnu.idatt1002.service.NotificationService;

import java.io.IOException;

/**
 * A class which contains the methods related to checking off notifications
 */
public class NotificationElementController {

    private int notificationId;
    @FXML private Text title;
    @FXML private Label description;

    /**
     * When notification is checked, it will be removed from notifications in navbar, but stay in notificationsHistory. Then we refresh the navbar.
     * @param event
     * @throws IOException
     */
    public void checkNotification(ActionEvent event) throws IOException {
        if(NotificationService.checkNotification(notificationId)){
            MainController.getInstance().setNavbar("navbar");
        }
    }

    /**
     * A method to set a title
     * @param title
     */
    public void setTitle(String title){
        this.title.setText(title);
    }

    /**
     * A method to set a description
     * @param description
     */
    public void setDescription(String description){
        this.description.setText(description);
    }

    /**
     * A method to set a notification id
     * @param id
     */
    public void setNotificationId(int id){
        this.notificationId = id;
    }
}
