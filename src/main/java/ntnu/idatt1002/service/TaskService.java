package ntnu.idatt1002.service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskService {

    public static boolean newTask(String title, String deadline, String description, int priority, String startDate, String category) {
        Task newTask = new Task(title, UserStateService.getCurrentUser().getUsername(), description, deadline, priority, startDate, category);
        TaskDAO.serializeTask(newTask);
        return true;
    }

    /**
     * Get all tasks for the user currently logged inn
     * @return
     */
    public static ArrayList<Task> getTasksByCurrentUser(){
        return TaskDAO.getTasksByUser(UserStateService.getCurrentUser().getUsername());
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
     * Returns a list of all the categories the current user got.
     * @return
     */
    public static ArrayList<String> getCategoryNames() {
        return new ArrayList<>(getCategoriesWithTasks().keySet());
    }

    /**
     * Methode that sorts all the tasks by category.
     * @param CategoryName
     * @param Username
     * @return
     */
    public static ArrayList<Task> TaskSortedByCategory(String CategoryName, String Username){
        TaskDAO taskdao = new TaskDAO();
        ArrayList<Task> usersTasks = taskdao.getTasksByUser(Username);
        ArrayList<Task> tasksSortedByCat = new ArrayList<>();
        for(Task t: usersTasks) {
            if(t.getCategory().equals(CategoryName)){
                tasksSortedByCat.add(t);
            }
        }
        return tasksSortedByCat;
    }

}


