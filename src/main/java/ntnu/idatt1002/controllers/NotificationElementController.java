package ntnu.idatt1002.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import ntnu.idatt1002.service.NotificationService;

import java.io.IOException;

public class NotificationElementController {

    /**
     * When notification is checked, it will be removed from notifications in navbar, but stay in notificationsHistory. Then we refresh the page.
     * @param event
     * @throws IOException
     */
    public void checkNotification(ActionEvent event) throws IOException {
        Button thisElement = (Button) event.getSource();
        Pane thisPane = (Pane) thisElement.getParent();
        int idOfElement = Integer.parseInt(thisPane.getId());

        if(NotificationService.checkNotification(idOfElement)){
            MainController.getInstance().setNavbar("navbar");
        }
    }
}