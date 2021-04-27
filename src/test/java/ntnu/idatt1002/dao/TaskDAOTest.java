package ntnu.idatt1002.dao;

import ntnu.idatt1002.model.Task;
import ntnu.idatt1002.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskDAOTest {
    private final static Task taskA = new Task.TaskBuilder("olanormann", "Do laundry")
            .description("")
            .priority(0)
            .category("Home")
            .build();
    private final static long id_A = taskA.getId();

    private final static Task taskB = new Task.TaskBuilder("olanormann", "Build walls")
            .description("")
            .priority(0)
            .project("Build house")
            .category("Carpentry")
            .build();
    private final static long id_B = taskB.getId();

    @BeforeAll
    public static void test_data(){
        UserDAO.serialize(new User("olanormann"));
        CategoryDAO.add("olanormann", "Home");
        ProjectDAO.add("olanormann", "Build house");
        CategoryDAO.add("olanormann", "Build house", "Carpentry");
        TaskDAO.serialize(taskA);
        TaskDAO.serialize(taskB);
    }

    @Test
    public void list_of_tasks(){
        ArrayList<Task> allTasks = TaskDAO.list("olanormann");
        ArrayList<Task> categoryTasks = TaskDAO.list("olanormann", "Home");
        ArrayList<Task> projectTasks = TaskDAO.list("olanormann", "Build house", "Carpentry");

        assertTrue(allTasks != null && categoryTasks != null && projectTasks != null);
        assertTrue(allTasks.size() == 2 && categoryTasks.size() == 1 && projectTasks.size() == 1);
    }

    @Test
    public void deserialize_tasks(){
        Task taskC = TaskDAO.deserialize("olanormann", id_A);
        Task taskD = TaskDAO.deserialize("olanormann", "Home", id_A);

        assertTrue(taskC != null && taskD != null);
        assertTrue(taskA.getName().equals(taskC.getName()) && taskA.getName().equals(taskD.getName()));

        Task taskE = TaskDAO.deserialize("olanormann", "Build house", "Carpentry", id_B);

        assertEquals(taskB, taskE);
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void list_with_wrong_arguments(){
            assertNull(TaskDAO.list("joseph"));
            assertNull(TaskDAO.list("joseph", "Home"));
            assertNull(TaskDAO.list("olanormann", "Work"));
            assertNull(TaskDAO.list("joseph", "Build house", "Carpentry"));
            assertNull(TaskDAO.list("olanormann", "Start business", "Take loan"));
            assertNull(TaskDAO.list("olanormann", "Build house", "Foundation"));
        }

        @Test
        public void deserializing_non_existing_tasks(){
            assertNull(TaskDAO.deserialize("joseph", id_A));
            assertNull(TaskDAO.deserialize("olanormann", 66666));
            assertNull(TaskDAO.deserialize("joseph", "Home", id_A));
            assertNull(TaskDAO.deserialize("olanormann", "Work", id_A));
            assertNull(TaskDAO.deserialize("olanormann", "Home", 666666));
            assertNull(TaskDAO.deserialize("joseph", "Build house", "Carpentry", id_A));
            assertNull(TaskDAO.deserialize("olanormann", "Start business", "Carpentry", id_A));
            assertNull(TaskDAO.deserialize("olanormann", "Build house", "Foundation", id_A));
            assertNull(TaskDAO.deserialize("olanormann", "Build house", "Carpentry", 666666));
        }

        @Test
        public void delete_tasks_by_none_existing_user(){
            assertFalse(TaskDAO.deleteByUser("joseph"));
        }

        @Test
        public void delete_none_existing_tasks(){
            assertFalse(TaskDAO.deleteByCategory("joseph", "Home"));
            assertFalse(TaskDAO.deleteByCategory("olanormann", "Work"));
        }

        @Test
        public void delete_tasks_in_non_existing_project(){
            assertFalse(TaskDAO.deleteByProjectCategory("joseph", "Build house", "Carpentry"));
            assertFalse(TaskDAO.deleteByProjectCategory("olanormann", "Start business", "Take loan"));
            assertFalse(TaskDAO.deleteByProjectCategory("olanormann", "Build house", "Foundation"));
        }

        @Test
        public void deleting_tasks_with_wrong_arguments(){
            assertFalse(TaskDAO.delete("joseph", "Home", id_A));
            assertFalse(TaskDAO.delete("olanormann", "Work", id_A));
            assertFalse(TaskDAO.delete("olanormann", "Home", 666666));
            assertFalse(TaskDAO.delete("joseph", "Build house", "Carpentry", id_A));
            assertFalse(TaskDAO.delete("olanormann", "Start business", "Carpentry", id_A));
            assertFalse(TaskDAO.delete("olanormann", "Build house", "Foundation", id_A));
            assertFalse(TaskDAO.delete("olanormann", "Build house", "Carpentry", 666666));
        }
    }

    @AfterAll
    public static void delete_test_data(){
        UserDAO.delete("olanormann");
    }
}
