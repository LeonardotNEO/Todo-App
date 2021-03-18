package ntnu.idatt1002.service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;
import java.util.ArrayList;

public class SortTaskByCategoryService {
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
            if(t.getCategory.equals(CategoryName)){
                tasksSortedByCat.add(t);
            }
        }
        return tasksSortedByCat;
    }
}
