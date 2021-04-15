package ntnu.idatt1002.service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;
import ntnu.idatt1002.dao.UserLogDAO;
import ntnu.idatt1002.utils.DateUtils;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * a class which provides some necessary features which utilises task-data
 */
public class TaskService {

    /**
     * Methode to validate if a task was successfully added. 
     * Returns true if the task was added.
     * @param  newTask that is being added.
     * @return boolean depending on the validation was successful.
     */
    public static boolean newTask(Task newTask) {
        String username = UserStateService.getCurrentUser().getUsername();
        TaskDAO.serializeTask(newTask);
        UserLogDAO.setTaskAdded(username, newTask.getName());
        return true;
    }

    /**
     * Method for editing task. this will override previous task object variables
     * @param task Task that is being edited.
     */
    public static void editTask(Task task, long taskId){
        task.setId(taskId);
        TaskDAO.serializeTask(task);
    }

    /**
     * A method to change the category assigned to the task
     * @param tasks The Array of tasks with the current category.
     * @param newCategory New category name.
     */
    public static void editCategoryOfTasks(ArrayList<Task> tasks, String newCategory){
        tasks.forEach(task -> editCategoryOfTask(task, newCategory));
    }

    /**
     * Edits the category of a given task.
     * @param task Task that you want to edit.
     * @param newCategory The new category name.
     */
    public static void editCategoryOfTask(Task task, String newCategory){
        TaskDAO.deleteTask(task);
        task.setCategory(newCategory);
        TaskDAO.serializeTask(task);
        UserLogDAO.setTaskMoved(task.getUserName(), newCategory);
    }

    /**
     * Get tasks that have a given category.
     * @param category The category that the tasks should be in.
     * @return The tasks that were found with the given category.
     */
    public static ArrayList<Task> getTasksByCategory(String category){
        return TaskDAO.getTasksByCategory(UserStateService.getCurrentUserUsername(), category);
    }

    /**
     * Method for getting tasks based on if the task does not contain a specific set of categories
     * @param tasks ArrayList of tasks that are being filtered.
     * @param categories The category that, if the task have they will be excluded from the Arraylist.
     * @return Returns a ArrayList of tasks without the tasks that had the given category.
     */
    public static ArrayList<Task> getTasksExcludingCategories(ArrayList<Task> tasks, ArrayList<String> categories){
        ArrayList<Task> tasksExcludingCategories = new ArrayList<>();
        tasks.forEach(task -> {
            if(!categories.contains(task.getCategory())){
                tasksExcludingCategories.add(task);
            }
        });

        return tasksExcludingCategories;
    }

    /**
     * Get all tasks for the user currently logged inn
     * @return returns a ArrayList of tasks that the current user has active.
     */
    public static ArrayList<Task> getTasksByCurrentUser(){
        return TaskDAO.getTasksByUser(UserStateService.getCurrentUserUsername());
    }

    /**
     * @return Returns a HashMap for all the categories with all tasks in an arraylist
     */
    public static HashMap<String, ArrayList<Task>> getCategoriesWithTasks() {
        HashMap<String, ArrayList<Task>> categoriesList = new HashMap<>();
        ArrayList<Task> tasks = getTasksByCurrentUser();
        tasks.forEach(task -> {
            categoriesList.computeIfAbsent(task.getCategory(), k -> new ArrayList<>());
            categoriesList.get(task.getCategory()).add(task);
        });
        return categoriesList;
    }

    /**
     * Returns a list of task from a given category
     * @param category Name of the category you want to get tasks from.
     * @return List of tasks or null if category is not found.
     */
    public static ArrayList<Task> getCategoryWithTasks(String category) {
        return getCategoriesWithTasks().get(category);
    }

    /**
     * Returns an ArrayList of all the tasks sorted by their priority.
     * @return A ArrayList of all the tasks sorted from highest priority(3) first in the ArrayList, and down to (0).
     */
    public static ArrayList<Task> TaskSortedByPriority(){
        ArrayList<Task> userTasks = getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
        userTasks.sort((o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority()));
        return userTasks;
    }

    /**
     * Returns an ArrayList of all the tasks sorted by their date.
     * @return An ArrayList of all tasks in the currently selected category, sorted by date.
     * The further away the deadline of the task is, the higher index it has.
     */
    public static ArrayList<Task> tasksSortedByDate(){
        ArrayList<Task> userTasks = getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
        userTasks.sort((o1, o2) -> {
            long task1Date = o1.getDeadline();
            long task2Date = o2.getDeadline();
            if (task1Date > task2Date) {
                return 1;
            }
            return -1;
        });
        return userTasks;
    }

    /**
     * Returns an ArrayList of all the tasks sorted by the alphabetical order of the first letter in them.
     * @return Returns an ArrayList of tasks that is sorted alphabetically by the title of the task. it only sorts the
     * tasks inside the currently selected category by the user.
     */
    public static ArrayList<Task> sortedAlphabetically(){
        
        ArrayList<Task> userTasksInCategory = getTasksByCategory(UserStateService.getCurrentUser().getCurrentlySelectedCategory());
        userTasksInCategory.sort(Comparator.comparing(Task::getName));
        return userTasksInCategory;
    }

    /**
     * Method that returns a list of tasks between a specific set of dates.
     * @param tasks The set of tasks the methode is being preformed on.
     * @param start interval start time in ms.
     * @param end interval end time in ms.
     * @return Lists of all tasks within the given interval.
     */
    public static ArrayList<Task> getTasksInDateInterval(ArrayList<Task> tasks, long start, long end){
        ArrayList<String> unWantedCategories = new ArrayList();
        unWantedCategories.add("Finished tasks");
        unWantedCategories.add("Trash bin");
        tasks.addAll(getRepeatTasks(getTasksExcludingCategories(getTasksByCurrentUser(),unWantedCategories),end));
        return tasks.stream()
                .filter(t-> t.getDeadline() > start && t.getDeadline() < end)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Finds all tasks withing a given interval. It uses all the tasks that the user has currently active.
     * @param start interval start time in ms
     * @param end interval end time in ms
     * @return Lists of all tasks within the given interval
     */
    public static ArrayList<Task> getTasksInDateInterval(long start, long end) {
        return getTasksInDateInterval(getTasksByCurrentUser(),start,end);
    }

    /**
     * Method that returns a list of tasks between a specific date.
     * @param tasks The set of tasks the methode is being preformed on.
     * @param dateLong The date.
     * @return An ArrayList of all the tasks that occurs that date.
     */
    public static ArrayList<Task> getTasksOnGivenDate(ArrayList<Task> tasks, long dateLong){
        return getTasksInDateInterval(tasks,dateLong,dateLong+24*60*60*1000);
    }

    /**
     * Returns an ArrayList of Tasks that have a name that contains the a given string.
     * The methode is not case sensitive.
     * @param DesiredName A part(or entire) string that is contained in the task(s) that you want to find.
     * @return An ArrayList of all the tasks that contains the DesiredName in the title.
     */
    public static ArrayList<Task> containsDesiredNameInTitle(String DesiredName){
        ArrayList<Task> userTasks = getTasksByCurrentUser();

        return userTasks.stream()
                .filter(t-> t.getName().toLowerCase().contains(DesiredName.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Uses TaskDAO and UserStateDAO to get task by id for current user
     * @param id
     * @return
     */
    public static Task getTaskByCurrentUser(long id){
        return TaskDAO.deserializeTask(UserStateService.getCurrentUserUsername(), id);
    }

    /**
     * Communicates with TaskDAO to delete a task
     * @param task The task that is going to be deleted.
     */
    public static void deleteTask(Task task){

        TaskDAO.deleteTask(task);
        UserLogDAO.setTaskRemoved(task.getUserName(), task.getName());

    }

    /**
     * Method that validates if task input is correct
     * @param title Title of task
     * @param description Description of the task
     * @return an ArrayList of errorCodes. ErrorCodes can be used i front end to display an errormessage for each scenario
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

    /**
     * Methode for returning the right error message as a String depending on the error code.
     * @param errorCodes An ArrayList of integers, that contains the potential errorCodes.
     * @return A String containing a description of the errors.
     */
    public static String getErrorMessageString(ArrayList<Integer> errorCodes) {
        StringBuilder errorMessageDisplayString = new StringBuilder();

        for (Integer errorCode : errorCodes) {
            switch (errorCode) {
                case 1:
                    errorMessageDisplayString.append("- Title must be between 0 and 30 characters \n");
                    break;
                case 2:
                    errorMessageDisplayString.append("- Description must be between cant be more than 170 characters \n");
                    break;
                case 3:
                    errorMessageDisplayString.append("- Priority must be chosen \n");
                    break;
                case 4:
                    errorMessageDisplayString.append("- Deadline cannot be in the past. Please choose a date in the future");
                case 5:
                    errorMessageDisplayString.append("- Please select a date");
                default:
                    break;
            }
        }

        return errorMessageDisplayString.toString();
    }

    /**
     * Methode that is used in the dateMethods to simulate all the tasks that would be there if a tasks is repeatable.
     *
     * @param ArrayListOfTasks The tasks that are going to be repeated(if they are repeatable).
     * @param end The limit date of where the repeatable tasks are stopped being cloned.
     * @return A ArrayList of all the cloned Repeatable tasks, with new deadlines.
     */
    public static ArrayList<Task> getRepeatTasks(ArrayList<Task> ArrayListOfTasks, long end){
        ArrayList arrayWithAllClones = new ArrayList();


        ArrayList<Task> ArrayListOfRepeat = ArrayListOfTasks.stream()
                .filter(t->t.isRepeatable())
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayListOfRepeat.stream().forEach(x-> System.out.println(x));
        // For each t
            // For each n*timeRepeat + deadline < end
                // Add new task to lost
        // return list
        for(Task T: ArrayListOfRepeat) {
            long inMs = 0L;
            switch (T.getTimeRepeat()){
                case "None":
                    inMs = 0L;
                    break;
                case "Repeat Daily":
                    inMs = 1000*60*60*24L;
                    break;
                case "Repeat Weekly":
                    inMs = 1000*60*60*24*7L;
                    break;
            }
            T.setDeadline(T.getDeadline()+1);// this is to counteract a bug that happens when the deadline is set to 0000:
                for (int i=1; (T.getDeadline() + i * inMs) <= end; i++) {

                    Task temp = new Task.TaskBuilder(T.getUserName(), T.getName())
                            .deadline(T.getDeadline() + i * inMs)
                            .color("#fffffff")
                            .build();
                    arrayWithAllClones.add(temp);
                }

        }
        return arrayWithAllClones;
    }

    public static void nextRepeatableTask(long taskId){
        Task task = TaskService.getTaskByCurrentUser(taskId);
        if(task.isRepeatable()) {
            long inMs = 0L;
            switch (task.getTimeRepeat()) {
                case "None":
                    inMs = 0L;
                    break;
                case "Repeat Daily":
                    inMs = 1000 * 60 * 60 * 24L;
                    break;
                case "Repeat Weekly":
                    inMs = 1000 * 60 * 60 * 24 * 7L;
                    break;
            }
            if (inMs != 0L) {
                Task t = TaskDAO.deserializeTask(task.getUserName(), task.getCategory(), task.getId());
                t.setDeadline(t.getDeadline() + inMs);
                t.setId(t.generateId());
                TaskService.newTask(t);
            }
        }
    }
}


