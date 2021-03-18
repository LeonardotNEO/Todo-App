package ntnu.idatt1002.service;

import ntnu.idatt1002.Category;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;
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
    public ArrayList<Task> TasksSortedByDate (){
        ArrayList<Task> userTasks = getTasksByCurrentUser();
        TaskDAO taskDAO = new TaskDAO();
        Comparator<Task> Datecomparer = new TaskComparator();
        Collections.sort(userTasks,Datecomparer);


        return userTasks;
    }
}


