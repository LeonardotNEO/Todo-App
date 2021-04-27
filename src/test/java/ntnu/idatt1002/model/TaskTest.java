package ntnu.idatt1002.model;

import ntnu.idatt1002.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void createTaskWithBuilderTest() {
        Task t = new Task.TaskBuilder("admin", "This is the task title")
                .category("test")
                .build();
        assertTrue(t.getCategory().equals("test")&&t.getUserName().equals("admin")&&t.getName().equals("This is the task title"));
    }
}
