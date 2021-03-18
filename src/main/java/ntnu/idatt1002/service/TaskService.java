package ntnu.idatt1002.service;

import ntnu.idatt1002.Category;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TaskService {

    public static boolean newTask(String username, String title, String deadline, String description, int priority, String startDate) {
        //Task newTask = new Task(title, username, description, deadline, priority, startDate);
        //TaskDAO.serializeTask(newTask);

        return true;
    }

    public static ArrayList<Task> getTasksByCurrentUser(){
        return TaskDAO.getTasksByUser(UserStateService.getCurrentUser().getUsername());
    }

    /**
     * Methode that sorts all the tasks by category.
     * @param CategoryName
     * @param Username
     * @return
     */
    public ArrayList<Task> TaskSortedByCategory(String CategoryName, String Username){
        TaskDAO taskdao = new TaskDAO();
        ArrayList<Task> usersTasks = taskdao.getTasksByUser(Username);
        ArrayList<Task> tasksSortedByCat = new ArrayList<>();
        tasksSortedByCat = (ArrayList<Task>) usersTasks.stream().filter(t -> t.getCategory().equals(CategoryName));
        return tasksSortedByCat;
    }

    /**
     * Returns Array of all the tasks sorted by their priority.
     * @param Username
     * @return
     */
    public ArrayList<Task> TaskSortedByPriority(String Username){
        TaskDAO taskDAO = new TaskDAO();
        ArrayList<Task> userTasks = taskDAO.getTasksByUser(Username);
        ArrayList<Task> taskSortedByPrio = new ArrayList<>();
        for(Task t: userTasks){
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

}


