package dao;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.TaskDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskDAOTest {
    private final static User userA = new User("olanormann");
    private final static String categoryA = "Home";
    private final static Task taskA = new Task("Clean room","olanormann","",0,"Home");
    private static int taskA_ID;

    @BeforeAll
    public static void setup() {
        UserDAO.serializeUser(userA);
        CategoryDAO.addCategory("olanormann", categoryA);
        TaskDAO.serializeTask(taskA);
        taskA_ID = taskA.hashCode();
    }

    @Test
    public void _getTasksByUser(){
        ArrayList<Task> tasks = TaskDAO.getTasksByUser("olanormann");

        assertTrue(tasks.contains(taskA));
    }

    @Test
    public void _saveTasks(){
        Task taskB = new Task("Do dishes","olanormann","",0,"Home");
        ArrayList<Task> tasksA = new ArrayList<>();
        tasksA.add(taskB);
        TaskDAO.saveTasks(tasksA);

        ArrayList<Task> tasksB = TaskDAO.getTasksByUser("olanormann");

        assertTrue(tasksB.contains(taskB));
    }

    @Nested
    public class deserialize_task{
        @Test
        public void by_username_category_id(){
            Task taskB = TaskDAO.deserializeTask("olanormann","Home", taskA_ID);

            assertEquals(taskA, taskB);
        }

        @Test
        public void by_username_id(){
            Task taskB = TaskDAO.deserializeTask("olanormann", taskA_ID);

            assertEquals(taskA, taskB);
        }
    }

    @Nested
    public class file_not_existing{
        @Test
        public void username(){
            assertNull(TaskDAO.deserializeTask("josephjoestar", "Home", taskA_ID));
        }

        @Test
        public void category(){
            assertNull(TaskDAO.deserializeTask("olanormann","Work", taskA_ID));
        }

        @Test
        public void task(){
            assertNull(TaskDAO.deserializeTask("olanormann","Home", 1234));
        }
    }

    @AfterAll
    public static void cleanup(){
        UserDAO.deleteUser("olanormann");
    }
}
