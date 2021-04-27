package ntnu.idatt1002.service;

import ntnu.idatt1002.model.Task;
import ntnu.idatt1002.model.User;
import ntnu.idatt1002.dao.UserDAO;
import ntnu.idatt1002.utils.DateUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    @BeforeAll
    public static void testData() {
        User user = new User("Test User");
        UserDAO.serialize(user);
        UserStateService.setCurrentUserUsername("Test User");
        assertDoesNotThrow(() -> {
            CategoryService.addCategoryToCurrentUser("home");
            CategoryService.addCategoryToCurrentUser("Category");
            CategoryService.addCategoryToCurrentUser("Stonks");


            String userName = UserStateService.getCurrentUser().getUsername();

            TaskService.newTask(
                    new Task.TaskBuilder(userName, "Hei")
                            .description("Hei på deg")
                            .deadline(DateUtils.getAsMs(LocalDate.of(2021, 02, 12)))
                            .priority(1)
                            .startDate(1l)
                            .category("Category")
                            .build()
            );

            TaskService.newTask(
                    new Task.TaskBuilder(userName, "Test1001")
                            .description("321832913291")
                            .deadline(DateUtils.getAsMs(LocalDate.of(2021, 04, 21)))
                            .priority(2)
                            .startDate(1l)
                            .category("home")
                            .build()
            );
            TaskService.newTask(
                    new Task.TaskBuilder(userName, "Test1")
                            .description("321832913291")
                            .deadline(DateUtils.getAsMs(LocalDate.of(2021, 05, 15)))
                            .priority(1)
                            .startDate(1l)
                            .category("home")
                            .build()
            );
            TaskService.newTask(
                    new Task.TaskBuilder(userName, "Test2")
                            .description("321832913291")
                            .deadline(DateUtils.getAsMs(LocalDate.of(2021, 02, 21)))
                            .priority(2)
                            .startDate(1l)
                            .category("home")
                            .build()
            );
            TaskService.newTask(
                    new Task.TaskBuilder(userName, "Test64")
                            .description("321832913291")
                            .deadline(DateUtils.getAsMs(LocalDate.of(2021, 06, 21)))
                            .priority(1)
                            .startDate(1l)
                            .category("home")
                            .build()
            );
            TaskService.newTask(
                    new Task.TaskBuilder(userName, "Test4")
                            .description("321832913291")
                            .deadline(DateUtils.getAsMs(LocalDate.of(2021, 02, 16)))
                            .priority(3)
                            .startDate(1l)
                            .category("home")
                            .build()
            );
            TaskService.newTask(
                    new Task.TaskBuilder(userName, "Test5")
                            .description("321832913291")
                            .deadline(DateUtils.getAsMs(LocalDate.of(2021, 01, 21)))
                            .priority(1)
                            .startDate(1l)
                            .category("home")
                            .build()
            );
            TaskService.newTask(
                    new Task.TaskBuilder(userName, "Test61001")
                            .description("321832913291")
                            .deadline(DateUtils.getAsMs(LocalDate.of(2022, 02, 20)))
                            .priority(0)
                            .startDate(1l)
                            .category("home")
                            .build()
            );
            TaskService.newTask(
                    new Task.TaskBuilder(userName,"TestRepeat")
                            .category("Stonks")
                            .repeatable(true,1000*60*60*12L)
                            .deadline(DateUtils.getAsMs(LocalDate.now()))
                            .build()
            );
        });
    }



    @Test
    public void sortTasksAlphabeticallyTest(){
        UserStateService.getCurrentUser().setCurrentlySelectedCategory("home");
        ArrayList<Task> list = TaskService.getTasksSortedAlphabetically(TaskService.getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory(), null));
        assertTrue(list.get(0).getName().compareTo(list.get(list.size() - 1).getName())<1);
    }

    @Test
    public void sortTasksByCategoryTest() {
        HashMap<String, ArrayList<Task>> list = TaskService.getCategoriesWithTasks();
        assertEquals("Hei",list.get("Category").get(0).getName());
    }

    @Test
    public void getAllTasksInGivenCategoryTest() {
        ArrayList<Task> taskList = TaskService.getCategoryWithTasks("Category");
        assertEquals(taskList.get(0).getName(), "Hei");
    }

    @Test
    public void sortTasksByPriorityTest() {
        UserStateService.getCurrentUser().setCurrentlySelectedCategory("home");
        ArrayList<Task> list = TaskService.getTasksSortedByPriority(TaskService.getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory(), null));

        // Higher number means higher priority.
        assertTrue(list.get(0).getPriority() >= list.get(list.size() - 1).getPriority());
    }

    @Test
    public void getTasksBetweenAIntervalTest() {
        long start = DateUtils.getAsMs(LocalDate.of(2021, 02, 12)) - 100;
        long end = DateUtils.getAsMs(LocalDate.of(2021, 02, 12)) + 100;

        // Test if we have found the correct task
        assertEquals("Hei", TaskService.getTasksBetweenDates(start, end).get(0).getName());

        // Check that we only found one task
        assertEquals(1, TaskService.getTasksBetweenDates(start, end).size());
    }

    @Test
    public void sortTasksByDateTest() {
        UserStateService.getCurrentUser().setCurrentlySelectedCategory("home");
        ArrayList<Task> list = TaskService.getTasksSortedByDate(TaskService.getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory(), null));

        long earlyMillis = list.get(0).getDeadline();
        long lateMillis = list.get(list.size() - 1).getDeadline();

        assertTrue(earlyMillis < lateMillis);
    }

    @Test
    public void getTasksBetweenDatesTest(){
        ArrayList<Task> taskList = TaskService.getCategoryWithTasks("home");
        System.out.println(TaskService.getTasksInDateInterval(taskList, DateUtils.getAsMs(LocalDate.of(2021, 1, 1)), DateUtils.getAsMs(LocalDate.of(2021,12,28))).size());

        assertTrue(TaskService.getTasksInDateInterval(taskList, DateUtils.getAsMs(LocalDate.of(2021, 1, 1)), DateUtils.getAsMs(LocalDate.of(2022,1,1))).size() == 6);
    }

    @Test
    public void getARepeatableTaskADayWhenItIsRepeatedTest(){
        ArrayList<Task> taskList = TaskService.getCategoryWithTasks("Stonks");
        long day = DateUtils.getAsMs(LocalDate.now());
        assertTrue(TaskService.getTasksOnGivenDate(taskList,day).size() == 2);
    }

    @AfterAll
    public static void deleteTestData() {
        UserService.deleteUser();
    }

}
