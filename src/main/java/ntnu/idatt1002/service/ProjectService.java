package ntnu.idatt1002.service;

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
    }

    public static boolean validateTitle(String title){
        if(title.length() > 0 && title.length() < 30){
            return true;
        } else {
            return false;
        }
    }
}
