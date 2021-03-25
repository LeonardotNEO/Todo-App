package dao;

import ntnu.idatt1002.Notification;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationDAOTest {
    private final static User userA = new User("olanormann");
    private final static Notification notifA = new Notification("Task A expired","olanormann","",
            Clock.systemDefaultZone());

    @BeforeAll
    static void setup(){
        UserDAO.serializeUser(userA);
        NotificationDAO.serializeNotif(notifA);
    }

    @Test
    public void _getNotifsByUser(){
        ArrayList<Notification> notifs = NotificationDAO.getNotifsByUser("olanormann");

        assertTrue(notifs.contains(notifA));
    }

    @Test
    public void _saveNotifs(){
        Notification notifB = new Notification("Task B expired","olanormann","",
                Clock.systemDefaultZone());
        ArrayList<Notification> notifsA = new ArrayList<>();
        notifsA.add(notifB);

        NotificationDAO.saveNotifs(notifsA);
        ArrayList<Notification> notifsB = NotificationDAO.getNotifsByUser("olanormann");

        assertTrue(notifsB.contains(notifB));
    }

    @Nested
    public class file_not_existing{
        @Test
        public void username(){
            assertEquals(NotificationDAO.getNotifsByUser("josephjoestar").size(), 0);
        }

        @Test
        public void notification(){
            assertNull(NotificationDAO.deserializeNotif("olanormann", 1234));
        }
    }

    @AfterAll
    static void cleanup(){
        UserDAO.deleteUser("olanormann");
    }
}
