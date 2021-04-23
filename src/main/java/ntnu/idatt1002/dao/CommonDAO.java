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
        String project = (task.getProject() == null ? "Standard" : task.getProject());
        String category = task.getCategory();
        if(listTasks(project, category).size() == 0){
            ArrayList<Task> newTasks = new ArrayList<>();
            newTasks.add(task);
            tasks.get(project).put(category, newTasks);
        }else {
            tasks.get(project).get(category).add(task);
        }
    }

    /**
     * Get an array of all projects for the logged in user.
     * @return a {@link String}[] of project names.
     */
    public static String[] listProjects(){
        ArrayList<String> projects = new ArrayList<>(tasks.keySet());
        projects.remove("Standard");
        return projects.toArray(new String[0]);
    }

    /**
     * Get an array of all projects, including 'Standard', for the logged in user.
     * @return a {@link String}[] of project names.
     */
    static String[] listAllProjects(){
        ArrayList<String> projects = new ArrayList<>(tasks.keySet());
        return projects.toArray(new String[0]);
    }

    /**
     * Get an array of all standard categories for the logged in user.
     * @return a {@link String}[] of category names.
     */
    public static String[] listCategories(){
        try {
            ArrayList<String> categories = new ArrayList<>(tasks.get("Standard").keySet());
            return categories.toArray(new String[0]);
        }catch (NullPointerException npe){
            return new String[0];
        }
    }

    /**
     * Get an array of all categories within a project for the logged in user.
     * @param project the project name.
     * @return a {@link String}[] of category names.
     */
    public static String[] listCategories(String project){
        ArrayList<String> categories = new ArrayList<>(tasks.get(project).keySet());
        return categories.toArray(new String[0]);
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
     * Get an ArrayList of all tasks within a project category for the logged in user.
     * @param project the project name.
     * @param category the category name.
     * @return an {@link ArrayList}{@code <>} of {@link Task} objects.
     */
    public static ArrayList<Task> listTasks(String project, String category){
        try {
            return tasks.get(project).get(category);
        }catch (NullPointerException npe){
            return new ArrayList<>();
        }
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
     * @param newUsername the new username.
     */
    static void edit(String newUsername){
        ArrayList<Task> allTasks = listTasks();
        for(Task task : allTasks){
            task.setUserName(newUsername);
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
        String project = (task.getProject() == null ? "Standard" : task.getProject());
        String category = task.getCategory();
        tasks.get(project).get(category).remove(task);
    }
 }
