package ntnu.idatt1002.service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;

public class NewTaskService {

    public static boolean newTask(String username, String title, String deadline, String description, int priority, String startDate) {
        Task newTask = new Task(title, username, description, deadline, priority, startDate);
        TaskDAO.serializeTask(newTask);

        return true;
    }

}


