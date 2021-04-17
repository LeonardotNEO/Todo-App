package dao;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ExcelDAOTest {
    private final static Task taskA = new Task.TaskBuilder("olanormann", "Do laundry")
            .description("Wash colored and wool")
            .priority(1)
            .category("Home")
            .build();

    private final static Task taskB = new Task.TaskBuilder("olanormann", "Build walls")
            .description("All outer walls")
            .priority(2)
            .project("Build house")
            .category("Carpentry")
            .build();

    @BeforeAll
    public static void setup(){
        UserDAO.serialize(new User("olanormann"));
        CategoryDAO.add("olanormann", "Home");
        ProjectDAO.add("olanormann", "Build house");
        CategoryDAO.add("olanormann", "Build house", "Carpentry");
        TaskDAO.serialize(taskA);
        TaskDAO.serialize(taskB);
    }

    @Test
    public void export(){
        assertDoesNotThrow(() -> ExcelDAO.write("olanormann"));
    }

    @AfterAll
    public static void cleanup(){
        TaskDAO.deleteByUser("olanormann");
    }
}
