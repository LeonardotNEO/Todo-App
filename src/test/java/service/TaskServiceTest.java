package service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserService;
import ntnu.idatt1002.service.UserStateService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    @BeforeAll
    public static void SetupTestData() {
        User user = new User("Test User");
        UserDAO.serializeUser(user);
        UserStateService.setCurrentUser("Test User");
        assertDoesNotThrow(() -> {
            TaskService.newTask("zei", "21/02/21", "Hei på deg", 1, null, "Category");
            TaskService.newTask("Test10001", "21/03/21", "dsadksajdskajdkasd", 2, "23/4/20", "home");
            TaskService.newTask("Test1", "21/05/21", "dsadksajdskajdkasd", 1, "23/4/20", "home");
            TaskService.newTask("Test2", "21/06/21", "dsadksajdskajdkasd", 2, "23/4/20", "home");
            TaskService.newTask("Test64", "21/10/21", "dsadksajdskajdkasd", 1, "23/4/20", "home");
            TaskService.newTask("Test4", "9/10/21", "dsadksajdskajdkasd", 3, "23/4/20", "home");
            TaskService.newTask("Test5", "1/01/21", "dsadksajdskajdkasd", 1, "23/4/20", "home");
            TaskService.newTask("Test61001", "11/03/21", "dsadksajdskajdkasd", 0, "23/4/20", "home");
        });
    }

    @AfterAll
    public static void CleanTestData() {
        UserService.deleteUser();
    }

    @Test
    public void tasksByAlphabetTest(){
        ArrayList<Task> list = TaskService.TasksSortedByAlphabet();
        assertTrue(list.get(0).getName().compareTo(list.get(list.size() - 1).getName())<1);
    }

    @Test
    public void tasksByCategoryTest() {
        HashMap<String, ArrayList<Task>> list = TaskService.getCategoriesWithTasks();
        assertEquals("Hei",list.get("Category").get(0).getName());
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

    @Test
    public void prioritySortTest() {
        ArrayList<Task> list = TaskService.TaskSortedByPriority();

        // Higher number means higher priority.
        assertTrue(list.get(0).getPriority() >= list.get(list.size() - 1).getPriority());
    }

    @Test
    public void dateSortTest() {
        ArrayList<Task> list = TaskService.TasksSortedByDate();

        Task early = list.get(0);
        Task late = list.get(list.size() - 1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date earlyDate = null;
        Date lateDate = null;

        try {
            earlyDate = sdf.parse(early.getDeadline());
            lateDate = sdf.parse(late.getDeadline());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        list.forEach(task -> {
            System.out.println(task);
        });

        long earlyMillis = earlyDate.getTime();
        long lateMillis = lateDate.getTime();

        assertTrue(earlyMillis < lateMillis);
    }
}
