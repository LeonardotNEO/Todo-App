package ntnu.idatt1002.service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;
import ntnu.idatt1002.dao.UserLogDAO;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * A class which provides some necessary features which utilises task-data
 */
public class TaskService {
    public static boolean newTask(Task newTask) {
        String username = UserStateService.getCurrentUser().getUsername();
        TaskDAO.serializeTask(newTask);
        UserLogDAO.setTaskAdded(username, newTask.getName());
        return true;
    }

    /**
     * Method for editing task. This will override Previous task object variables
     * @param task
     */
    public static void editTask(Task task, long taskId){
        task.setId(taskId);
        TaskDAO.serializeTask(task);
    }

    /**
     * A method to change the category assigned to the task
     * @param tasks
     * @param newCategory
     */
    public static void editCategoryOfTasks(ArrayList<Task> tasks, String newCategory){
        tasks.forEach(task -> {
            editCategoryOfTask(task, newCategory);
        });
    }

    public static void editCategoryOfTask(Task task, String newCategory){
        TaskDAO.deleteTask(task);
        task.setCategory(newCategory);
        TaskDAO.serializeTask(task);
        UserLogDAO.setTaskMoved(task.getUserName(), newCategory);
    }

    /**
     * Get tasks by category
     * @param category
     * @return
     */
    public static ArrayList<Task> getTasksByCategory(String category){
        return TaskDAO.getTasksByCategory(UserStateService.getCurrentUserUsername(), category);
    }

    /**
     * Method for getting tasks based on if the task does not contain a specific set of categories
     * @param tasks
     * @param categories
     * @return
     */
    public static ArrayList<Task> getTasksExcludingCategories(ArrayList<Task> tasks, ArrayList<String> categories){
        ArrayList<Task> tasksExludingCategories = new ArrayList<>();
        tasks.forEach(task -> {
            if(!categories.contains(task.getCategory())){
                tasksExludingCategories.add(task);
            }
        });

        return tasksExludingCategories;
    }

    /**
     * Get all tasks for the user currently logged inn
     * @return
     */
    public static ArrayList<Task> getTasksByCurrentUser(){
        return TaskDAO.getTasksByUser(UserStateService.getCurrentUserUsername());
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
     * Methode that sorts all the tasks by category.
     * @param CategoryName
     * @return
     */
    public static ArrayList<Task> TaskSortedByCategory(String CategoryName){
        ArrayList<Task> usersTasks = getTasksByCurrentUser();
        ArrayList<Task> tasksSortedByCat = (ArrayList<Task>) usersTasks.stream().filter(t -> t.getCategory().equals(CategoryName));
        return tasksSortedByCat;
    }

    /**
     * Returns an Array of all the tasks sorted by their priority.
     * @return
     */
    public static ArrayList<Task> TaskSortedByPriority(){
        //ArrayList<Task> userTasks = getTasksByCurrentUser();
        ArrayList<Task> userTasks = getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
        Collections.sort(userTasks, (o1, o2) -> o1.getPriority() > o2.getPriority() ? -1 : (o1.getPriority() < o2.getPriority()) ? 1 : 0);
        return userTasks;
    }

    /**
     * Returns an Array of all the tasks sorted by their date.
     * @return
     */
    public static ArrayList<Task> TasksSortedByDate(){
        //ArrayList<Task> userTasks = getTasksByCurrentUser();
        ArrayList<Task> userTasks = getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
        Collections.sort(userTasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                long task1Date = o1.getDeadline();
                long task2Date = o2.getDeadline();
                if(task1Date > task2Date){
                    return 1;
                }
                return -1;
            }
        });

        return userTasks;
    }

    /**
     * Returns an ArrayList of all the tasks sorted by the alphabetical order of the first letter in them.
     * @return
     */
    public static ArrayList<Task> TasksSortedByAlphabet(){
        ArrayList<Task> userTasks = getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
        Collections.sort(userTasks, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2){
                return o1.getName().compareTo(o2.getName());
            }
        });

        return userTasks;
    }

    /**
     * Method that retuns a list of tasks between a specific set of dates
     * @param tasks
     * @param start
     * @param stop
     * @return
     */
    public static ArrayList<Task> getTasksBetweenDates(ArrayList<Task> tasks, long start, long stop){
        ArrayList<Task> tasksBetweenDates = new ArrayList<>();

        for(Task task : tasks){
            if(task.getDeadline() >= start && task.getDeadline() <= stop){
                tasksBetweenDates.add(task);
            }
        }

        return tasksBetweenDates;
    }

    public static ArrayList<Task> getTasksByDate(ArrayList<Task> tasks, long datelong){
        ArrayList<Task> tasksByDate = new ArrayList<>();

        for(Task task : tasks){
            LocalDate dateInput = Instant.ofEpochMilli(datelong).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dateTask = Instant.ofEpochMilli(task.getDeadline()).atZone(ZoneId.systemDefault()).toLocalDate();

            if(dateInput.getDayOfMonth() == dateTask.getDayOfMonth() && dateInput.getMonthValue() == dateTask.getMonthValue() && dateInput.getYear() == dateTask.getYear()){
                tasksByDate.add(task);
            }
        }

        return tasksByDate;
    }

    /**
     * Returns an ArrayLists of Tasks that have a name that contains the a given string.
     * @param DesiredName
     * @return
     */
    public static ArrayList<Task> TasksFoundWithSearchBox(String DesiredName){
        ArrayList<Task> userTasks = getTasksByCurrentUser();
        ArrayList<Task> CompatableTasks = new ArrayList<>();

        for(Task t: userTasks){
            if(t.getName().toLowerCase().contains(DesiredName.toLowerCase())){
                CompatableTasks.add(t);
            }
        }
        return CompatableTasks;
    }

    /**
     * Finds all tasks withing a given interval
     * @param start interval start time in ms
     * @param end interval end time in ms
     * @return Lists of all tasks within the given interval
     */
    public static ArrayList<Task> getTaskByDateInterval(long start, long end) {
        ArrayList<Task> userTasks = getTasksByCurrentUser();
        return userTasks.stream()
                .filter(t -> t.getDeadline() > start && t.getDeadline() < end)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Uses TaskDAO and UserStateDAO to get task by id for current user
     * @param id
     * @return
     */
    public static Task getTaskByCurrentUser(long id){
        Task task = TaskDAO.deserializeTask(UserStateService.getCurrentUserUsername(), id);
        return task;
    }

    /**
     * Communicates with TaskDAO to delete a task
     * @param task
     */
    public static void deleteTask(Task task){
        TaskDAO.deleteTask(task);
        UserLogDAO.setTaskRemoved(task.getUserName(), task.getName());
    }

    /**
     * Method that validates if task input is correct
     * @param title
     * @param description
     * @return an ArrayList of errorcodes. Errorcodes can be used i front end to display an errormessage for each scenario
     */
    public static ArrayList<Integer> validateTaskInput(String title, String description, String priority, long deadlineTime){
        ArrayList<Integer> errorsCodes = new ArrayList<>();

        if(title.length() < 1 || title.length() > 30){
            errorsCodes.add(1);
        }
        if(description.length() > 170){
            errorsCodes.add(2);
        }
        try{
            Integer.parseInt(priority);
        } catch (NumberFormatException nfe) {
            errorsCodes.add(3);
        }
        if(deadlineTime == 0) {
            errorsCodes.add(5);
        } else if(deadlineTime < new Date().getTime()) {
            errorsCodes.add(4);
        }

        return errorsCodes;
    }

    public static String getErrorMessageString(ArrayList<Integer> errorCodes){
        String errorMessageDisplayString = "";

        for(Integer errorCode : errorCodes){
            switch (errorCode){
                case 1:
                    errorMessageDisplayString += "- Title must be between 0 and 30 characters \n";
                    break;
                case 2:
                    errorMessageDisplayString += "- Description must be between cant be more than 170 characters \n";
                    break;
                case 3:
                    errorMessageDisplayString += "- Priority must be choosen \n";
                    break;
                case 4:
                    errorMessageDisplayString += "- Deadline cannot be in the past. Please choose a date in the future";
                case 5:
                    errorMessageDisplayString += "- Please select a date";
                default:
                    break;
            }
        }

        return errorMessageDisplayString;
    }
}


