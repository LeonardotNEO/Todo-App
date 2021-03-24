package ntnu.idatt1002.service;

import javafx.scene.control.DatePicker;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.TaskDAO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Comparator for dates in tasks.
 */
class TaskComparator implements Comparator<Task>{

    @Override
    public int compare(Task o1, Task o2) {
        long task1Date = o1.getDeadline();
        long task2Date = o2.getDeadline();
        if(task1Date > task2Date){
            return 1;
        }
        return -1;
    }

}


public class TaskService {
    public static boolean newTask(String title, LocalDate deadline, String description, int priority, String startDate, String category) {
        Task newTask = new Task(title, UserStateService.getCurrentUser().getUsername(), description, getDeadlineMs(deadline), priority, startDate, category);
        TaskDAO.serializeTask(newTask);
        return true;
    }

    public static void editCategoryOfTasks(ArrayList<Task> tasks, String newCategory){
        tasks.forEach(task -> {
            TaskDAO.deleteTask(task);
            task.setCategory(newCategory);
            TaskDAO.serializeTask(task);
        });
    }

    /**
     * Get tasks by category
     * @param category
     * @return
     */
    public static ArrayList<Task> getTasksByCategory(String category){
        return TaskDAO.getTasksByCategory(UserStateService.getCurrentUserUsername(), category);
    }

    /**
     * Get all tasks for the user currently logged inn
     * @return
     */
    public static ArrayList<Task> getTasksByCurrentUser(){
        return TaskDAO.getTasksByUser(UserStateService.getCurrentUserUsername());
    }

    /**
     * Returns a HashMap for all the categories with all tasks in an arraylist
     * @return
     */
    public static HashMap<String, ArrayList<Task>> getCategoriesWithTasks() {
        HashMap<String, ArrayList<Task>> categoriesList = new HashMap<>();
        ArrayList<Task> tasks = getTasksByCurrentUser();
        tasks.forEach(task -> {
            if(categoriesList.get(task.getCategory()) == null) {
                categoriesList.put(task.getCategory(), new ArrayList<Task>());
            }
            categoriesList.get(task.getCategory()).add(task);
        });
        return categoriesList;
    }

    /**
     * Returns a list of task from a given category
     * @param category
     * @return List of tasks or null if category is not found.
     */
    public static ArrayList<Task> getCategoryWithTasks(String category) {
        return getCategoriesWithTasks().get(category);
    }

    /**
     * Methode that sorts all the tasks by category.
     * @param CategoryName
     * @return
     */
    public static ArrayList<Task> TaskSortedByCategory(String CategoryName){
        ArrayList<Task> usersTasks = getTasksByCurrentUser();
        ArrayList<Task> tasksSortedByCat = (ArrayList<Task>) usersTasks.stream().filter(t -> t.getCategory().equals(CategoryName));
        return tasksSortedByCat;
    }

    /**
     * Returns Array of all the tasks sorted by their priority.
     * @return
     */
    public static ArrayList<Task> TaskSortedByPriority(){
        ArrayList<Task> userTasks = getTasksByCurrentUser();
        Collections.sort(userTasks, (o1, o2) -> o1.getPriority() > o2.getPriority() ? -1 : (o1.getPriority() < o2.getPriority()) ? 1 : 0);
        return userTasks;
    }

    /**
     * Returns list of tasks sorted.
     * @return
     */
    public static ArrayList<Task> TasksSortedByDate(){
        ArrayList<Task> userTasks = getTasksByCurrentUser();
        Comparator<Task> Datecomparer = new TaskComparator();
        Collections.sort(userTasks,Datecomparer);

        return userTasks;
    }

    public static ArrayList<Task> TasksSortedByAlphabet(){
        ArrayList<Task> userTasks = getTasksByCurrentUser();
        Collections.sort(userTasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2){
                return o1.getName().compareTo(o2.getName());
            }
        });

        return userTasks;
    }

    /**
     * Uses TaskDAO and UserStateDAO to get task by id for current user
     * @param id
     * @return
     */
    public static Task getTaskByCurrentUser(int id){
        Task task = TaskDAO.deserializeTask(UserStateService.getCurrentUserUsername(), id);
        return task;
    }

    /**
     * Returns a long representing time in milliseconds since 1/1/1970
     * @param localdate
     * @return
     */
    public static long getDeadlineMs(LocalDate localdate) {
        Instant instant = localdate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * Takes a long and turns it into a date in the format dd/MM/yyyy. The ms inserted represents the time since 1/1/1970
     * @param ms
     * @return
     */
    public static String transformDeadline(long ms) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms + 1);

        // Adds a 0 to month/Day if under 9 so the format is always MM
        String month = calendar.get(Calendar.MONTH) > 9 ? "" + (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1);
        String day = calendar.get(Calendar.DAY_OF_MONTH) > 9 ? "" + calendar.get(Calendar.DAY_OF_MONTH) : "0" + calendar.get(Calendar.DAY_OF_MONTH);

        return day+ "/" + month + "/" + calendar.get(Calendar.YEAR);
    }

    /**
     * Communicates with TaskDAO to delete a task
     * @param task
     */
    public static void deleteTask(Task task){
        TaskDAO.deleteTask(task);
    }
}


