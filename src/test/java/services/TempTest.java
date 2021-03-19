package services;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.service.TaskService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TempTest {

    @Test
    public void test() {
        ArrayList<Task> list = TaskService.TaskSortedByPriority();
        for(Task a: list) {
            System.out.println(a);
        }
    }
}
