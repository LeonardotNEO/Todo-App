package service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.service.CategoryService;
import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserService;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    @BeforeAll
    public static void SetupTestData() {
        User user = new User("Test User");
        UserDAO.serializeUser(user);
        UserStateService.setCurrentUserUsername("Test User");
        assertDoesNotThrow(() -> {
            CategoryService.addCategoryToCurrentUser("home");
            CategoryService.addCategoryToCurrentUser("Category");
            TaskService.newTask(new Task("Hei", UserStateService.getCurrentUser().getUsername(), "Hei på deg", DateUtils.getAsMs(LocalDate.of(2021, 02, 12)),  1, 1l, "Category", "", null, false, false, false, null,false,0L, null));
            TaskService.newTask(new Task("Test10001", UserStateService.getCurrentUser().getUsername(), "dsadksajdskajdkasd", DateUtils.getAsMs(LocalDate.of(2021, 04, 21)), 2, 1l, "home", "", null, false, false, false,null,false,0L,null));
            TaskService.newTask(new Task("Test1", UserStateService.getCurrentUser().getUsername(), "dsadksajdskajdkasd", DateUtils.getAsMs(LocalDate.of(2021, 05, 15)), 1, 1l, "home", "", null, false, false, false,null,false,0L,null));
            TaskService.newTask(new Task("Test2", UserStateService.getCurrentUser().getUsername(), "dsadksajdskajdkasd", DateUtils.getAsMs(LocalDate.of(2021, 02, 21)), 2, 1l, "home", "", null, false, false, false,null,false,0L,null));
            TaskService.newTask(new Task("Test64", UserStateService.getCurrentUser().getUsername(), "dsadksajdskajdkasd", DateUtils.getAsMs(LocalDate.of(2021, 06, 21)), 1, 1l, "home", "", null, false, false, false,null,false,0L,null));
            TaskService.newTask(new Task("Test4", UserStateService.getCurrentUser().getUsername(), "dsadksajdskajdkasd", DateUtils.getAsMs(LocalDate.of(2021, 02, 16)), 3, 1l, "home", "", null, false, false, false,null,false,0L,null));
            TaskService.newTask(new Task("Test5", UserStateService.getCurrentUser().getUsername(), "dsadksajdskajdkasd", DateUtils.getAsMs(LocalDate.of(2021, 01, 21)), 1, 1l, "home", "", null, false, false, false,null,false,0L,null));
            TaskService.newTask(new Task("Test61001", UserStateService.getCurrentUser().getUsername(), "dsadksajdskajdkasd", DateUtils.getAsMs(LocalDate.of(2022, 02, 20)), 0, 1l, "home", "", null, false, false, false,null,false,0L,null));
        });
    }

    @AfterAll
    public static void CleanTestData() {
        UserService.deleteUser();
    }

    @Test
    public void tasksByAlphabetTest(){
        UserStateService.getCurrentUser().setCurrentlySelectedCategory("home");
        ArrayList<Task> list = TaskService.TasksSortedByAlphabet();
        assertTrue(list.get(0).getName().compareTo(list.get(list.size() - 1).getName())<1);
    }

    @Test
    public void tasksByCategoryTest() {
        HashMap<String, ArrayList<Task>> list = TaskService.getCategoriesWithTasks();
        assertEquals("Hei",list.get("Category").get(0).getName());
    }

    @Test
    public void getCategoryTasksTest() {
        ArrayList<Task> taskList = TaskService.getCategoryWithTasks("Category");
        assertEquals(taskList.get(0).getName(), "Hei");
    }

    @Test
    public void prioritySortTest() {
        UserStateService.getCurrentUser().setCurrentlySelectedCategory("home");
        ArrayList<Task> list = TaskService.TaskSortedByPriority();

        // Higher number means higher priority.
        assertTrue(list.get(0).getPriority() >= list.get(list.size() - 1).getPriority());
    }

    @Test
    public void tasksByDateIntervalTest() {
        long start = DateUtils.getAsMs(LocalDate.of(2021, 02, 12)) - 100;
        long end = DateUtils.getAsMs(LocalDate.of(2021, 02, 12)) + 100;

        // Test if we have found the correct task
        assertEquals("Hei", TaskService.getTaskByDateInterval(start, end).get(0).getName());

        // Check that we only found one task
        assertEquals(1, TaskService.getTaskByDateInterval(start, end).size());
    }

    @Test
    public void dateSortTest() {
        UserStateService.getCurrentUser().setCurrentlySelectedCategory("home");
        ArrayList<Task> list = TaskService.TasksSortedByDate();

        long earlyMillis = list.get(0).getDeadline();
        long lateMillis = list.get(list.size() - 1).getDeadline();

        assertTrue(earlyMillis < lateMillis);
    }

    @Test
    public void taskBetweenDates(){
        ArrayList<Task> taskList = TaskService.getCategoryWithTasks("home");
        assertTrue(TaskService.getTasksBetweenDates(taskList, DateUtils.getAsMs(LocalDate.of(2021, 1, 1)), DateUtils.getAsMs(LocalDate.of(2021,7,5))).size() == 6);
    }
}
