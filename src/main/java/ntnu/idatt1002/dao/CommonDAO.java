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

    /**
     * Add a standard category to the logged in user.
     * @param category the category name.
     */
    public static void addCategory(String category){
        tasks.get("Standard").put(category, new ArrayList<>());
    }

    /**
     * Add a project category to the logged in user.
     * @param project the already existing project name.
     * @param category the category name.
     */
    public static void addCategory(String project, String category){
        tasks.get(project).put(category, new ArrayList<>());
    }

    /**
     * Add a project to the logged in user. Can not be called 'Standard'.
     * @param project the project name.
     */
    public static void addProject(String project){
        if(!project.equals("Standard")){ tasks.put(project, new HashMap<>()); }
    }

    /**
     * Get an array of all projects for the logged in user.
     * @return a {@link String}[] of project names.
     */
    public static String[] listProjects(){
        return (String[]) tasks.keySet().toArray();
    }

    /**
     * Get an array of all standard categories for the logged in user.
     * @return a {@link String}[] of category names.
     */
    public static String[] listCategories(){
        return (String[]) tasks.get("Standard").keySet().toArray();
    }

    /**
     * Get an array of all categories within a project for the logged in user.
     * @param project the project name.
     * @return a {@link String}[] of category names.
     */
    public static String[] listCategories(String project){
        return (String[]) tasks.get(project).keySet().toArray();
    }

    /**
     * Delete a project from the logged in user. Can't delete 'Standard' project.
     * @param project the project name.
     */
    public static void deleteProject(String project){
        if(!project.equals("Standard")){ tasks.remove(project); }
    }

    /**
     * Delete a standard category from the logged in user.
     * @param category the category name.
     */
    public static void deleteCategory(String category){
        tasks.get("Standard").remove(category);
    }

    /**
     * Delete a project category from the logged in user.
     * @param project the project name.
     * @param category the category name.
     */
    public static void deleteCategory(String project, String category){
        tasks.get(project).remove(category);
    }
 }
