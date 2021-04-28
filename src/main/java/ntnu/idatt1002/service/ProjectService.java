package ntnu.idatt1002.service;

import ntnu.idatt1002.model.Task;
import ntnu.idatt1002.dao.ProjectDAO;

import java.util.ArrayList;

/**
 * Class used for handling projects. The class includes methodes for deletion and adding projects, as well as deleting them.
 */
public class ProjectService {

    /**
     * Method communicating with ProjectDAO to fetch the current users projects
     * @return String[] of all project by current user.
     */
    public static String[] getProjectsCurrentUser(){
        return ProjectDAO.list(UserStateService.getCurrentUser().getUsername());
    }

    /**
     * Method returning an arraylist of the current users projects
     * @return Arraylist of strings of all projects by current user
     */
    public static ArrayList<String> getProjectCurrentUserArraylist(){
        ArrayList<String> projects = new ArrayList<>();

        for(String project : getProjectsCurrentUser()){
            projects.add(project);
        }

        return projects;
    }

    /**
     * Method communicating with ProjectDAO to add a new project to the current user
     * @param projectName Name of the new project
     */
    public static void addNewProjectCurrentUser(String projectName){
        ProjectDAO.add(UserStateService.getCurrentUser().getUsername(), projectName);
    }

    /**
     * Method communicating with ProjectDAO to delete a project from the current user
     * When the project is deleted (User can only delete currently selected project), we set currently selected project and project-category so null.
     * @param projectName
     */
    public static void deleteProjectCurrentUser(String projectName){
        ProjectDAO.delete(UserStateService.getCurrentUser().getUsername(), projectName);
        UserStateService.getCurrentUser().setCurrentlySelectedProject(null);
        UserStateService.getCurrentUser().setCurrentlySelectedProjectCategory(null);
    }

    /**
     * Method for validating if the title of a project is valid
     * @param title
     * @return
     */
    public static boolean validateTitle(String title){
        return title.length() > 0 && title.length() < 30;
    }

    /**
     * Method for editing a project under the current user.
     * @param oldname The old name of the project
     * @param newName The new name of the project
     */
    public static void editProject(String oldname, String newName){
        // add project to current user
        ProjectService.addNewProjectCurrentUser(newName);

        // add categories to new project and transfer tasks
        for(String category : CategoryService.getCategoriesByProjectCurrentUser(oldname)){
            CategoryService.addCategoryToCurrentUser(newName, category);

            for(Task task : TaskService.getTasksByCategory(category, oldname)){
                task.setProject(newName);
                TaskService.newTask(task);
            }
        }

        // delete old project
        ProjectService.deleteProjectCurrentUser(oldname);
    }
}
