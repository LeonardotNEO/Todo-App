
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
        long dead = DateUtils.getAsMs(LocalDate.of(2022, 02, 20));
        Task b = new Task.TaskBuilder("admin", "Test61001")
                .description("321832913291")
                .deadline(dead)
                .priority(1)
                .startDate(1l)
                .category("home")
                .build();

        System.out.println(b);

        Task t = new Task.TaskBuilder("admin", "This is the task title")
                .category("test")
                .build();
        System.out.println(t);

    }
}
