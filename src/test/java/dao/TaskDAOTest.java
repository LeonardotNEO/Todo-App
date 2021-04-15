package dao;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.ProjectDAO;
import ntnu.idatt1002.dao.TaskDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskDAOTest {
    private final static Task taskA = new Task("Do laundry", "olanormann", "", 0, "Home");
    private final static long id_A = taskA.getId();
    private final static Task taskB = new Task("Build walls", "olanormann", "", 0, "Build house", "Carpentry");
    private final static long id_B = taskB.getId();

    @BeforeAll
    public static void setup(){
        UserDAO.serialize(new User("olanormann"));
        CategoryDAO.add("olanormann", "Home");
        ProjectDAO.add("olanormann", "Build house");
        CategoryDAO.add("olanormann", "Build house", "Carpentry");
        TaskDAO.serialize(taskA);
        TaskDAO.serialize(new Task("Mow lawn", "olanormann", "", 0, "Home"));
        TaskDAO.serialize(taskB);
        TaskDAO.serialize(new Task("Lay roof tiles", "olanormann", "", 0, "Build house", "Carpentry"));
    }

    @Test
    public void _list(){
        ArrayList<Task> allTasks = TaskDAO.list("olanormann");
        ArrayList<Task> categoryTasks = TaskDAO.list("olanormann", "Home");
        ArrayList<Task> projectTasks = TaskDAO.list("olanormann", "Build house", "Carpentry");

        assertTrue(allTasks != null && categoryTasks != null && projectTasks != null);
        assertTrue(allTasks.size() == 4 && categoryTasks.size() == 2 && projectTasks.size() == 2);
    }

    @Test
    public void _deserialize(){
        Task taskC = TaskDAO.deserialize("olanormann", id_A);
        Task taskD = TaskDAO.deserialize("olanormann", "Home", id_A);

        assertTrue(taskC != null && taskD != null);
        assertTrue(taskA.getName().equals(taskC.getName()) && taskA.getName().equals(taskD.getName()));

        Task taskE = TaskDAO.deserialize("olanormann", "Build house", "Carpentry", id_B);

        assertEquals(taskB, taskE);
    }

    @AfterAll
    public static void cleanup(){
        UserDAO.delete("olanormann");
    }
}
