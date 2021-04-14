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

    @Test
    public void HardcodingTask(){
        TaskService.newTask(
                new Task.TaskBuilder("omar","TestRepeat")
                        .category("Test")
                        .repeatable(true,1000*60*60*12L)
                        .deadline(DateUtils.getAsMs(LocalDate.now().atTime(1,0)))
                        .description("Testing stuff")
                        .color("#ffffff")
                        .build()
        );
    }
}
