package ntnu.idatt1002.service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Comparator for dates in tasks.
 */
class TaskComparator implements Comparator<Task>{

    @Override
    public int compare(Task o1, Task o2) {
        String[] task1DateArray = o1.getDeadline().split(".",3);
        String task1Date = task1DateArray[2]+task1DateArray[1]+task1DateArray[0];

        String[] task2DateArray = o2.getDeadline().split(".",3);
        String task2Date = task2DateArray[2]+task2DateArray[1]+task2DateArray[0];

        if(Long.parseLong(task1Date)<=Long.parseLong(task2Date)){
            return 1;
        }
        return -1;
    }
}


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
     * @return
     */
    public ArrayList<Task> TaskSortedByCategory(String CategoryName){
        TaskDAO taskdao = new TaskDAO();
        ArrayList<Task> usersTasks = getTasksByCurrentUser();
        ArrayList<Task> tasksSortedByCat = new ArrayList<>();
        tasksSortedByCat = (ArrayList<Task>) usersTasks.stream().filter(t -> t.getCategory().equals(CategoryName));
        return tasksSortedByCat;
    }

    /**
     * Returns Array of all the tasks sorted by their priority.
     * @return
     */
    public ArrayList<Task> TaskSortedByPriority(){
        TaskDAO taskDAO = new TaskDAO();
        ArrayList<Task> userTasks = getTasksByCurrentUser();
        ArrayList<Task> taskSortedByPrio = new ArrayList<>();
        for(Task t: userTasks){// the large amout of for loops is because we want a sertain sequence. 3 first, 2 second....
            if(t.getPriority() == 3){
                taskSortedByPrio.add(t);
            }
        }

        for(Task t: userTasks){
            if(t.getPriority() == 2){
                taskSortedByPrio.add(t);
            }
        }

        for(Task t: userTasks){
            if(t.getPriority() == 1){
                taskSortedByPrio.add(t);
            }
        }

        for(Task t: userTasks){
            if(t.getPriority() == 0){
                taskSortedByPrio.add(t);
            }
        }
        return taskSortedByPrio;
    }

    /**
     * Returns list of tasks sorted.
     * @return
     */
    public ArrayList<Task> TasksSortedByDate (){
        ArrayList<Task> userTasks = getTasksByCurrentUser();
        TaskDAO taskDAO = new TaskDAO();
        Comparator<Task> Datecomparer = new TaskComparator();
        Collections.sort(userTasks,Datecomparer);


        return userTasks;
    }

    /**
     * Uses TaskDAO and UserStateDAO to get task by id for current user
     * @param id
     * @return
     */
    public static Task getTaskByCurrentUser(int id){
        Task task = TaskDAO.deserializeTask(UserStateService.getCurrentUser().getUsername(), id);
        return task;
    }
}


