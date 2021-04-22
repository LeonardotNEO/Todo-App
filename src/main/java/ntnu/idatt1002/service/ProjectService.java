package ntnu.idatt1002.service;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.dao.CommonDAO;

public class ProjectService {

    public static String[] getProjectsCurrentUser(){
        return CommonDAO.listProjects();
    }

    public static void addNewProjectCurrentUser(String projectName){
        CommonDAO.addProject(projectName);
    }

    public static void deleteProjectCurrentUser(String projectName){
        CommonDAO.deleteProject(projectName);
        UserStateService.getCurrentUser().setCurrentlySelectedProject(null);
        UserStateService.getCurrentUser().setCurrentlySelectedProjectCategory(null);
    }

    public static boolean validateTitle(String title){
        return title.length() > 0 && title.length() < 30;
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
