package service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserStateService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    @BeforeAll
    public static void SetupTestData() {
        User user = new User("Test User");
        UserDAO.serializeUser(user);
        UserStateService.setCurrentUser("Test User");
        assertDoesNotThrow(() -> {
            TaskService.newTask("Hei", "21/02/21", "Hei på deg", 1, null, "Category");
        });
    }

    @AfterAll
    public static void CleanTestData() {
        // Delete the user & tasks
    }

    @Test
    public void tasksByCategoryTest() {
        HashMap<String, ArrayList<Task>> list = TaskService.getCategoriesWithTasks();
        assertEquals(list.get("Category").get(0).getName(), "Hei");
    }

    @Test
    public void getListOfCategoriesTest() {
        ArrayList<String> categories = TaskService.getCategoryNames();
        assertTrue(categories.contains("Category"));
    }

    @Test
    public void getCategoryTasksTest() {
        ArrayList<Task> taskList = TaskService.getCategoryWithTasks("Category");
        assertEquals(taskList.get(0).getName(), "Hei");
    }
}
