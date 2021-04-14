package classes;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void createTask() {
        Task t = new Task.TaskBuilder("admin", "This is the task title")
                .category("test")
                .build();
        System.out.println(t);

    }
}
