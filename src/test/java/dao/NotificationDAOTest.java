package dao;

import ntnu.idatt1002.model.Notification;
import ntnu.idatt1002.model.User;
import ntnu.idatt1002.dao.NotificationDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationDAOTest {
    public static final Notification notifA = new Notification("Task A expired", "olanormann", "", LocalDateTime.now());
    public static final int id_A = notifA.getNotifId();

    @BeforeAll
    public static void test_data(){
        UserDAO.serialize(new User("olanormann"));
        NotificationDAO.serialize(notifA);
        NotificationDAO.serialize(new Notification("Task B expired", "olanormann", "", LocalDateTime.now()));
        NotificationDAO.serialize(new Notification("Task C expired", "olanormann", "", LocalDateTime.now()));
    }

    @Test
    public void list_of_notifications(){
        ArrayList<Notification> notifs = NotificationDAO.list("olanormann");

        assertEquals(notifs.size(), 3);
    }

    @Test
    public void deserializing_notification(){
        Notification notifB = NotificationDAO.deserialize("olanormann", id_A);

        assertEquals(notifA, notifB);
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void list_of_none_existing_notifications(){
            assertEquals(NotificationDAO.list("joseph").size(), 0);
        }

        @Test
        public void deserializing_notification_that_dont_exist(){
            assertNull(NotificationDAO.deserialize("joseph", id_A));
            assertNull(NotificationDAO.deserialize("olanormann", 666666));
        }

        @Test
        public void delete_notifications_that_does_not_exist_by_user(){
            assertFalse(NotificationDAO.deleteByUser("joseph"));
        }

        @Test
        public void delete_notifications_by_id_with_wrong_arguments(){
            assertFalse(NotificationDAO.delete("joseph", id_A));
            assertFalse(NotificationDAO.delete("olanormann", 666666));
        }
    }

    @AfterAll
    public static void delete_test_data(){
        UserDAO.delete("olanormann");
    }
}
