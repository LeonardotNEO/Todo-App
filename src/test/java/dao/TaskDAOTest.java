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
    private final static Task taskA = new Task.TaskBuilder("olanormann", "Clean room")
            .description("")
            .priority(1)
            .deadline(1)
            .startDate(1)
            .category("Home")
            .build();

    private static long taskA_ID;

    @BeforeAll
    public static void setup() {
        UserDAO.serializeUser(userA);
        CategoryDAO.addCategory("olanormann", categoryA);
        TaskDAO.serializeTask(taskA);
        taskA_ID = taskA.getId();
    }

    @Test
    public void _getTasksByUser(){
        ArrayList<Task> tasks = TaskDAO.getTasksByUser("olanormann");

        assertTrue(tasks.contains(taskA));
    }

    @Test
    public void _saveTasks(){
        ArrayList<String> tags = new ArrayList();
        Task taskB = new Task.TaskBuilder("olanormann", "Do the dishes")
                .category("Home")
                .build();
        ArrayList<Task> tasksAArray = new ArrayList<>();
        tasksAArray.add(taskB);
        TaskDAO.saveTasks(tasksAArray);

        ArrayList<Task> tasksBArray = TaskDAO.getTasksByUser("olanormann");

        assertTrue(tasksBArray.contains(taskB));
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
