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
     * Add a task to the logged in user.
     * @param task the {@link Task} object.
     */
    public static void addTask(Task task){
        String project = (task.getProject().isEmpty() ? "Standard" : task.getProject());
        String category = task.getCategory();
        tasks.get(project).get(category).add(task);
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
     * Get an ArrayList of all tasks for the logged in user.
     * @return an {@link ArrayList}{@code <>} of {@link Task} objects.
     */
    public static ArrayList<Task> listTasks(){
        ArrayList<Task> allTasks = new ArrayList<>();
        tasks.values().forEach(h -> h.values().forEach(allTasks::addAll));
        return allTasks;
    }

    /**
     * Get an ArrayList of all tasks within a project for the logged in user.
     * @param project the project name.
     * @return an {@link ArrayList}{@code <>} of {@link Task} objects.
     */
    public static ArrayList<Task> listTasks(String project){
        ArrayList<Task> projectTasks = new ArrayList<>();
        tasks.get(project).values().forEach(projectTasks::addAll);
        return projectTasks;
    }

    /**
     * Get an ArrayList of all tasks within a project category for the logged in user.
     * @param project the project name.
     * @param category the category name.
     * @return an {@link ArrayList}{@code <>} of {@link Task} objects.
     */
    public static ArrayList<Task> listTasks(String project, String category){
        return tasks.get(project).get(category);
    }

    /**
     * Get an ArrayList of all tasks within a standard category for the logged in user.
     * @param category the category name.
     * @return an {@link ArrayList}{@code <>} of {@link Task} objects.
     */
    public static ArrayList<Task> listTasksByCategory(String category){
        return listTasks("Standard", category);
    }

    /**
     * Search entire task list for a task with a specific id. Much more efficient if category and eventually
     * project is known.
     * @param id the {@link Task} id.
     * @return the {@link Task} object, or {@code null} if none is found.
     */
    public static Task getTask(long id){
        ArrayList<Task> allTasks = listTasks();
        for(Task task : allTasks){
            if (task.getId() == id){ return task; }
        }
        return null;
    }

    /**
     * Get a task by the given arguments. Will not search in projects.
     * @param category the category name.
     * @param id the {@link Task} id.
     * @return the {@link Task} object, or {@code null} if none is found.
     */
    public static Task getTask(String category, long id){
        return getTask("Standard", category, id);
    }

    /**
     * Get a task by the given arguments.
     * @param project the project name.
     * @param category the category name.
     * @param id the {@link Task} id.
     * @return the {@link Task} object, or {@code null} if none is found.
     */
    public static Task getTask(String project, String category, long id){
        ArrayList<Task> allTasks = listTasks(project, category);
        for(Task task : allTasks){
            if(task.getId() == id){ return task; }
        }
        return null;
    }

    /**
     * Change the owner of all tasks
     * @param username the new username.
     */
    static void edit(String username){
        ArrayList<Task> allTasks = listTasks();
        for(Task task : allTasks){
            task.setUserName(username);
        }
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

    /**
     * Delete a task from the logged in user.
     * @param task the {@link Task} object.
     */
    public static void deleteTask(Task task){
        String project = (task.getProject().isEmpty() ? "Standard" : task.getProject());
        String category = task.getCategory();
        tasks.get(project).get(category).remove(task);
    }
 }
