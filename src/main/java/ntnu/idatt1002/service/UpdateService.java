package ntnu.idatt1002.service;

import ntnu.idatt1002.Notification;
import ntnu.idatt1002.controllers.MainController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class for handling checking for certain updates every second
 */
public class UpdateService {
    private static ArrayList<Notification> previousNotifications = new ArrayList<>();

    public static void start(){
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> {
                    // only run when there actually is an user is userstate
                    if(UserStateService.checkIfUserState()){

                        // NOTIFICATIONS
                        // If the current list of unchecked notifications changes, we update navbar UI
                        if(!(previousNotifications.equals(NotificationService.getActiveAndNotCheckedNotifications()))){
                            previousNotifications = NotificationService.getActiveAndNotCheckedNotifications();
                            try {
                                MainController.getInstance().setNavbar("navbar");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        // check if new notifications have become active
                        NotificationService.checkIfNotificationHasBecomeActive(NotificationService.getNotificationsByUser());
                    }
                });
            }
        }, 0, 1000);
    }
}
