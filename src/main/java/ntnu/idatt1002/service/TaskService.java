package ntnu.idatt1002.service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.TaskDAO;
import ntnu.idatt1002.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
     */
    public static void newTask(Task newTask) {
        TaskDAO.serialize(newTask);
    }

    /**
     * Method for editing task. this will override previous task object variables
     * @param task Task that is being edited.
     */
    public static void editTask(Task task, long taskId){
        task.setId(taskId);
        TaskDAO.serialize(task);
    }

    /**
     * A method to change the category assigned to the task.
     *
     * @param tasks The Array of tasks with the current category.
     * @param category New category name.
     */
    public static void editCategoryAndProjectOfTasks(ArrayList<Task> tasks, String category, String project){
        if(tasks != null){
            tasks.forEach(task -> editCategoryAndProjectOfTask(task, category, project));
        }
    }

    /**
     * A method to edit the category of a single task.
     *
     * @param task the task object we want to edit.
     * @param category the new category for the task.
     */
    public static void editCategoryAndProjectOfTask(Task task, String category, String project){
        TaskDAO.delete(task);
        setOriginals(task);
        if(project == null){
            task.setCategory(category);
            task.setProject(null);
        } else {
            task.setCategory(category);
            task.setProject(project);
        }

        TaskDAO.serialize(task);
    }

    /**
     * Sets original Project and Category for the given task.
     * Useful for restoring categories and projects.
     *
     * @param task Task to set originals for
     */
    public static void setOriginals(Task task) {
        task.setOriginalProject(task.getProject());
        task.setOriginalCategory(task.getCategory());
    }

    /**
     * Used to set the isRepeatable value of a given task and update the save files at the same time.
     *
     * @param task      Task object to change
     * @param value     boolean new value for task.isRepeatable
     */
    public static void setRepeatable(Task task, boolean value) {
        TaskDAO.delete(task);
        task.setRepeatable(value);
        TaskDAO.serialize(task);
    }

    /**
     * Usedto set the isFinished value and the finishDate of the given task.
     * Will set finish date to 0 if given value is false.
     *
     * @param task      Task object to change
     * @param value     boolean new value for task.isFinished
     */
    public static void setFinished(Task task, boolean value) {
        TaskDAO.delete(task);
        task.setFinished(value);
        if (value) {
            task.setFinishDate(DateUtils.getAsMs(LocalDateTime.now()));
        } else {
            task.setFinishDate(0);
        }
        TaskDAO.serialize(task);
    }

    /**
     * Get tasks that have a given category.
     *
     * @param category The category that the tasks should be in.
     * @return The tasks that were found with the given category.
     */
    public static ArrayList<Task> getTasksByCategory(String category, String project){
        ArrayList<Task> tasksResult = null;

        if(category != null){
            if(project == null){
                if(UserStateService.getCurrentUser().getCurrentlySelectedCategory().equals("All tasks")){
                    ArrayList<String> avoidCategories = new ArrayList<>();
                    avoidCategories.add("Finished tasks");
                    avoidCategories.add("Trash bin");
                    tasksResult = TaskService.getTasksExcludingCategories(getTasksByCurrentUser(),avoidCategories);
                } else {
                    tasksResult = TaskDAO.list(UserStateService.getCurrentUser().getUsername(), category);
                }
            } else {
                tasksResult = TaskDAO.list(UserStateService.getCurrentUser().getUsername(), project, category);
            }
        }

        if(tasksResult == null){
            tasksResult = new ArrayList<>();
        }

        return tasksResult;
    }

    /**
     * Method for getting tasks based on if the task does not contain a specific set of categories.
     *
     * @param tasks the ArrayList of task-objects to check.
     * @param categories the ArrayList of categories to exclude.
     * @return a new ArrayList of task-objects, containing only tasks without the given categories.
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
     * Get all tasks for the user currently logged inn.
     *
     * @return an ArrayList of task-objects by the current user.
     */
    public static ArrayList<Task> getTasksByCurrentUser(){
        return TaskDAO.list(UserStateService.getCurrentUser().getUsername());
    }

    /**
     * A method which will remove a single file from a specified task.
     *
     * @param task the task with a file to be removed.
     * @param filePath the file to be removed.
     * @return the changed task.
     */
    public static Task removeAttachedFile(Task task, String filePath) {
        task.getFilePaths().removeIf(e -> e.equals(filePath));
        return task;
    }

    /**
     * A method which returns a HashMap for all the categories with all tasks in an arraylist.
     *
     * @return a HashMap for all the categories with all tasks in an arraylist.
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
     * A method which returns a list of task from a given category.
     *
     * @param category Name of the category you want to get tasks from.
     * @return List of tasks or null if category is not found.
     */
    public static ArrayList<Task> getCategoryWithTasks(String category) {
        return getCategoriesWithTasks().get(category);
    }

    /**
     * Methode that sorts all the tasks by category.
     *
     * @param CategoryName the category to sort by.
     * @return an ArrayList of task-objects, containing task with the given category.
     */
    public static ArrayList<Task> TaskSortedByCategory(String CategoryName){
        ArrayList<Task> usersTasks = getTasksByCurrentUser();
        return (ArrayList<Task>) usersTasks.stream().filter(t -> t.getCategory().equals(CategoryName));
    }

    /**
     * A method which sorts tasks by their priority.
     * @return an Array of all the tasks sorted by their priority.
     */
    public static ArrayList<Task> getTasksSortedByPriority(ArrayList<Task> tasks){
        tasks.sort((o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority()));
        return tasks;
    }

    /**
     * A method which sorts tasks by their deadline.
     * The task with the earliest deadline will be the first index in the ArrayList.
     * The further away the deadline of the task is, the higher index it has.
     *
     * @return An ArrayList of all tasks in the currently selected category, sorted by date.
     */
    public static ArrayList<Task> getTasksSortedByDate(ArrayList<Task> tasks){
        tasks.sort((o1, o2) -> {
            long task1Date = o1.getDeadline();
            long task2Date = o2.getDeadline();
            if(task1Date == 0) return 1; // If the task got no deadline put it at the end of the list
            if (task1Date > task2Date) {
                return 1;
            }
            return -1;
        });
        return tasks;
    }

    /**
     * Returns an ArrayList of all the tasks sorted by the alphabetical order of the first letter in them.
     * Tasks inside the currently selected category by the user.
     *
     * @return an ArrayList of tasks that is sorted alphabetically by the title of the task. it only sorts the
     */
    public static ArrayList<Task> getTasksSortedAlphabetically(ArrayList<Task> tasks){
        tasks.sort(Comparator.comparing(Task::getName));
        return tasks;
    }

    /**
     * Finds all tasks withing a given interval. It uses all the tasks that the user has currently active.
     *
     * @param start interval start time in ms
     * @param end interval end time in ms
     * @return Lists of all tasks within the given interval
     */
    public static ArrayList<Task> getTasksBetweenDates(long start, long end) {
        return getTasksInDateInterval(getTasksByCurrentUser(),start,end);
    }

    /**
     * Method that returns a list of tasks between a specific set of dates.
     *
     * @param tasks The set of tasks the methode is being preformed on.
     * @param start interval start time in ms.
     * @param end interval end time in ms.
     * @return Lists of all tasks within the given interval.
     */
    public static ArrayList<Task> getTasksInDateInterval(ArrayList<Task> tasks, long start, long end){
        ArrayList<String> unWantedCategories = new ArrayList<>();
        unWantedCategories.add("Finished tasks");
        unWantedCategories.add("Trash bin");
        tasks.addAll(getRepeatTasks(getTasksExcludingCategories(tasks,unWantedCategories),end));
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
     *
     * @param tasks The set of tasks the methode is being preformed on.
     * @param dateLong The date.
     * @return An ArrayList of all the tasks that occurs that date.
     */

    public static ArrayList<Task> getTasksOnGivenDate(ArrayList<Task> tasks, long dateLong){
        return getTasksInDateInterval(tasks,dateLong,dateLong+24*60*60*1000);
    }

    /**
     * Returns an ArrayList of Tasks that have a name or tag that contains the given string.
     * The methode is not case sensitive.
     *
     * @param DesiredName A part(or entire) string that is contained in the task(s) that you want to find.
     * @return An ArrayList of all the tasks that contains the DesiredName in the title or tag.
     */
    public static ArrayList<Task> containsDesiredNameInTitle(String DesiredName){

        ArrayList<String> unWantedCategories = new ArrayList<>();
        unWantedCategories.add("Finished tasks");
        unWantedCategories.add("Trash bin");
        ArrayList<Task> userTasks = getTasksExcludingCategories(getTasksByCurrentUser(),unWantedCategories);
        //Get task name matches
        ArrayList<Task> nameMatch = userTasks.stream()
                .filter(t-> t.getName().toLowerCase().contains(DesiredName.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));

        //Get task tag matches
        ArrayList<Task> tagMatch = new ArrayList<>();
        for (Task task : userTasks){
            for(String tag : task.getTags()){
                if(tag.toLowerCase().contains(DesiredName.toLowerCase())){
                    tagMatch.add(task);
                }
            }
        }

        //Combine results, remove duplicates and return result
        nameMatch.addAll(tagMatch);
        return nameMatch.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Uses TaskDAO and UserStateDAO to get task by id for current user.
     *
     * @param id the id of the singular task.
     * @return the task-object.
     */
    public static Task getTaskByCurrentUser(long id){
        return TaskDAO.deserialize(UserStateService.getCurrentUserUsername(), id);
    }

    /**
     * Communicates with TaskDAO to delete a task.
     *
     * @param task The task that is going to be deleted.
     */
    public static void deleteTask(Task task){
        TaskDAO.delete(task);
    }


    /**
     * Method that validates if task input is correct.
     *
     * @param title Title of task.
     * @param description Description of the task.
     * @return an ArrayList of errorCodes. ErrorCodes can be used i front end to display an errormessage for each scenario.
     */
    public static ArrayList<Integer> validateTaskInput(String title, String description, String priority, long deadlineTime, long repeatTime){
        ArrayList<Integer> errorsCodes = new ArrayList<>();

        if(title.length() < 1 || title.length() > 30){
            errorsCodes.add(1);
        }
        /* TODO: Make better description validation? Restricting description length is not useful.
        if(description.length() > 5000){
            errorsCodes.add(2);
        }*/
        try{
            convertPriorityStringToInt(priority);
        } catch (NumberFormatException nfe) {
            errorsCodes.add(3);
        }
        if(deadlineTime == 0 && repeatTime > 0 ) {
            errorsCodes.add(6);
        } else if (deadlineTime > 0 && deadlineTime < new Date().getTime()) {
            errorsCodes.add(4);
        }
        /*
        if(deadlineTime == 0) {
            errorsCodes.add(5);
        } else if(deadlineTime < new Date().getTime()) {
            errorsCodes.add(4);
        }
        */
        return errorsCodes;
    }

    /**
     * Methode for returning the right error message as a String depending on the error code.
     *
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
                    errorMessageDisplayString.append("- Due time cannot be in the past. Please choose a date in the future\n");
                    break;
                case 5:
                    errorMessageDisplayString.append("- Please select a date\n");
                    break;
                case 6:
                    errorMessageDisplayString.append("- A repeatable task needs a due time\n");
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
        ArrayList<Task> arrayWithAllClones = new ArrayList<>();


        ArrayList<Task> ArrayListOfRepeat = ArrayListOfTasks.stream()
                .filter(Task::isRepeatable)
                .collect(Collectors.toCollection(ArrayList::new));
        for(Task T: ArrayListOfRepeat) {

            T.setDeadline(T.getDeadline()+1);// this is to counteract a bug that happens when the deadline is set to 0000:
                for (int i=1; (T.getDeadline() + i * T.getTimeRepeat()) <= end; i++) {

                    Task temp = new Task.TaskBuilder(T.getUserName(), T.getName())
                            .deadline(T.getDeadline() + i * T.getTimeRepeat())
                            .color(T.getColor())
                            .description(T.getDescription())
                            .category(T.getCategory())
                            .filePaths(T.getFilePaths())
                            .location(T.getLocation())
                            .priority(T.getPriority())
                            .project(T.getProject())
                            .tags(T.getTags())
                            .startDate(T.getStartDate())
                            .build();
                            if(T.isNotification1Hour()){
                                temp.setNotification1Hour(true);
                            }if(T.isNotification24Hours()){
                                temp.setNotification24Hours(true);
                            }if(T.isNotification7Days()){
                                temp.setNotification7Days(true);
                            }

                    arrayWithAllClones.add(temp);
                }

        }
        return arrayWithAllClones;
    }

    /**
     * Methode that generates the next occurrence of a repeatable task. This is used when a repeatable task is deleted.
     * This is so that completing or deleting a repeating task, wont delete the chain of repeatable tasks.
     * the task is saved directly into the save files.
     * @param taskId to find a specific task in the database.
     */
    public static void nextRepeatableTask(long taskId){
        Task T= TaskService.getTaskByCurrentUser(taskId);
        if(T.isRepeatable()) {
            if (T.getTimeRepeat() != 0L) {
                Task t;
                if (T.getProject() == null) {
                    t = TaskDAO.deserialize(T.getUserName(), T.getCategory(), T.getId());
                } else {
                    t = TaskDAO.deserialize(T.getUserName(), T.getProject(), T.getCategory(), T.getId());
                }
                t.setDeadline(t.getDeadline() + T.getTimeRepeat());
                t.setId(t.generateId());
                TaskService.newTask(t);
            }
        }
    }

    /**
     * takes a description of a repeatable task, and turns it into Millis based on what the string is.
     * @param TimeRepeatString The description of the repetition. (Repeat Daily/Repeat Weekly).
     * @return A long representing a week or a day. or 0L if the input is not valid.
     */
    public static long convertTimeRepeatToLong(String TimeRepeatString){
        switch(TimeRepeatString){
            case "Repeat Daily":
                return 1000*60*60*24L;
            case "Repeat Weekly":
                return 1000*60*60*24*7L;
            default:
                return 0L;
        }
    }

    /**
     * Converts a repetition time of a task, into a String which description the timeframe of repetition.
     * @param T the Task which repetition time you want to convert
     * @return a String reflecting the repetition time, or None if the time is invalid.
     */
    public static String convertTimeRepeatToString(Task T){
        if(T.getTimeRepeat() == 1000*60*60*24L){
            return "Repeat Daily";
        }else if(T.getTimeRepeat() == 1000*60*60*24*7L){
            return "Repeat Weekly";
        }
        else{
            return "None";
        }
    }

    public static String convertPriorityIntToString(int priority){
        switch (priority){
            default:
                return "None";
            case 1:
                return "Low";
            case 2:
                return "Medium";
            case 3:
                return "High";

        }
    }
    public static int convertPriorityStringToInt(String Priority){
        switch (Priority) {
            case "Low":
                return 1;
            case "Medium":
                return 2;
            case "High":
                return 3;
            default:
                return 0;
        }
    }
}


