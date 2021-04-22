package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;

import java.util.ArrayList;
import java.util.HashMap;

public final class CommonDAO {
    private static HashMap<String, HashMap<String, ArrayList<Task>>> tasks = new HashMap<>();

    /**
     * Get tasks from application.
     * @return a {@code HashMap} with projects, categories and tasks.
     */
    static HashMap<String, HashMap<String, ArrayList<Task>>> getTasks(){
        return tasks;
    }

    /**
     * Fill temporary tasks variable from storage.
     * @param newTasks a {@code HashMap} with projects, categories and tasks.
     */
    static void setTasks(HashMap<String, HashMap<String, ArrayList<Task>>> newTasks){
        tasks = newTasks;
    }
 }
