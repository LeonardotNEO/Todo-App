package ntnu.idatt1002.service;

import ntnu.idatt1002.Category;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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
        for(Task t: usersTasks) {
            if(t.getCategory().equals(CategoryName)){
                tasksSortedByCat.add(t);
            }
        }
        return tasksSortedByCat;
    }

}


