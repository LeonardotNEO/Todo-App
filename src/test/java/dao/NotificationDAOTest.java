package dao;

import ntnu.idatt1002.Notification;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationDAOTest {
    static User user = new User("olanormann");
    Notification notificationA = new Notification("Task A expired","olanormann","", Clock.systemDefaultZone());
    int noticationID = notificationA.hashCode();

    @BeforeAll
    public static void setup(){
        UserDAO.serializeUser(user);
    }

    @Test
    public void save_and_get_notif_from_user(){
        NotificationDAO.serializeNotif(notificationA);
        Notification notificationB = NotificationDAO.deserializeNotif("olanormann",noticationID);

        assertEquals(notificationA, notificationB);
    }

    @Test
    public void get_all_notifs_from_user() {
        NotificationDAO.serializeNotif(notificationA);
        ArrayList<Notification> notifications = NotificationDAO.getNotifsByUser("olanormann");

        assertNotNull(notifications);
        assertTrue(notifications.contains(notificationA));
    }

    @Test
    public void save_arraylist_of_notifs(){
        Notification notificationB = new Notification("Task B expires soon","olanormann","", Clock.systemDefaultZone());

        ArrayList<Notification> notifications = new ArrayList<>();
        notifications.add(notificationB);
        int notifBID = notificationB.hashCode();
        NotificationDAO.saveNotifsToUser(notifications);

        Notification notificationC = NotificationDAO.deserializeNotif("olanormann", notifBID);

        assertEquals(notificationB, notificationC);
    }

    @Test
    public void delete_notification(){
        Notification notificationD = new Notification("Task C expired","olanormann","", Clock.systemDefaultZone());
        NotificationDAO.serializeNotif(notificationD);

        NotificationDAO.deleteNotif(notificationD);
        ArrayList<Notification> notifications = NotificationDAO.getNotifsByUser("olanormann");

        assertFalse(notifications.contains(notificationD));
    }

    @AfterAll
    public static void cleanup(){
        UserDAOTest.cleanup();
    }
}
