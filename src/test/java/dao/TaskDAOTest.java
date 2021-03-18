package dao;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.TaskDAO;
import ntnu.idatt1002.dao.UserDAO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskDAOTest {
    User user = new User("olanormann");
    Task taskA = new Task("Clean room","olanormann","",0,"Bil");
    int taskID = taskA.hashCode();

    @Test
    public void save_and_get_task_from_user(){
        UserDAO.serializeUser(user);
        TaskDAO.serializeTask(taskA);
        Task taskB = TaskDAO.deserializeTask("olanormann", taskID);

        assertEquals(taskA, taskB);
    }

    @Test
    public void get_all_tasks_from_user(){
        UserDAO.serializeUser(user);
        TaskDAO.serializeTask(taskA);
        ArrayList<Task> tasks = TaskDAO.getTasksByUser("olanormann");

        assertNotNull(tasks);
        assertTrue(tasks.contains(taskA));
    }

    @Test
    public void save_arraylist_of_tasks(){
        Task taskB = new Task("Throw trash","olanormann","",0,"Bil");

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(taskB);
        int taskBID = taskB.hashCode();
        TaskDAO.saveTasksToUser(tasks);

        Task taskC = TaskDAO.deserializeTask("olanormann", taskBID);

        assertEquals(taskB, taskC);
    }
}
