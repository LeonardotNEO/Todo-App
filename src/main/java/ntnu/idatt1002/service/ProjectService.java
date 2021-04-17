package ntnu.idatt1002.service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.CategoryDAO;
import ntnu.idatt1002.dao.ProjectDAO;

public class ProjectService {

    public static String[] getProjectsCurrentUser(){
        return ProjectDAO.list(UserStateService.getCurrentUser().getUsername());
    }

    public static void addNewProjectCurrentUser(String projectName){
        ProjectDAO.add(UserStateService.getCurrentUser().getUsername(), projectName);
    }

    public static void deleteProjectCurrentUser(String projectName){
        ProjectDAO.delete(UserStateService.getCurrentUser().getUsername(), projectName);
        UserStateService.getCurrentUser().setCurrentlySelectedProject("");
        UserStateService.getCurrentUser().setCurrentlySelectedProjectCategory("");
    }

    public static boolean validateTitle(String title){
        if(title.length() > 0 && title.length() < 30){
            return true;
        } else {
            return false;
        }
    }

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
