package dao;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.*;
import ntnu.idatt1002.utils.DateUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelDAOTest {
    private final static Task taskA = new Task.TaskBuilder("olanormann", "Do laundry")
            .description("Wash colored and wool")
            .priority(1)
            .startDate(DateUtils.getAsMs(LocalDate.of(2021, 1, 1)))
            .deadline(DateUtils.getAsMs(LocalDate.of(2021, 8, 21)))
            .category("Home")
            .location("Bergen")
            .build();

    private final static Task taskB = new Task.TaskBuilder("olanormann", "Build walls")
            .description("All outer walls")
            .priority(2)
            .startDate(DateUtils.getAsMs(LocalDate.of(2021, 4, 10)))
            .deadline(DateUtils.getAsMs(LocalDate.of(2021, 11, 3)))
            .project("Build house")
            .category("Carpentry")
            .location("Trondheim")
            .build();

    @BeforeAll
    public static void setup(){
        Storage.newUser(new User("olanormann"));
        Storage.read("olanormann");
        CommonDAO.addCategory("Home");
        CommonDAO.addProject("Build house");
        CommonDAO.addCategory("Build house", "Carpentry");
        CommonDAO.addTask(taskA);
        CommonDAO.addTask(taskB);
    }

    @Test
    public void _write(){
        assertDoesNotThrow(() -> ExcelDAO.write("olanormann"));
    }

    @Test
    public void _read(){
        File file = ExcelDAO.read("olanormann");
        assertNotNull(file);
        assertTrue(file.exists());
    }

    @Nested
    public class wrong_arguments{
        @Test
        public void _write(){
            assertDoesNotThrow(() -> ExcelDAO.write("joseph"));
        }

        @Test
        public void _read(){
            assertNull(ExcelDAO.read("joseph"));
        }
    }

    @AfterAll
    public static void cleanup(){
        Storage.deleteUser("olanormann");
    }
}
