package ntnu.idatt1002.service;

import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.model.Notification;
import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    @BeforeAll
    static public void setup() {
        User user = new User("Test User");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername("Test User");
    }

    @AfterAll
    static public void deleteTestData() {
        UserService.deleteUser();
    }

    @Test
    public void addAndGetNotificationTest(){
        NotificationService.newNotification("New", "This is a new notification", LocalDateTime.now());

        // Check if its added
        assertEquals("New", NotificationService.getNotificationsByCurrentUser().get(0).getTitle());
    }

    @Test
    public void editNotificationTest() {
        NotificationService.newNotification("New", "This is a new notification", LocalDateTime.now());
        Notification a = NotificationService.getNotificationsByCurrentUser().get(0);

        // Changing the checked value
        a.setChecked(true);

        // Edit notification
        NotificationService.editNotification(a);

        // Check if its added
        assertTrue( NotificationService.getNotificationsByCurrentUser().get(0).getChecked());
    }

}
