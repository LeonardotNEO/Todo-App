package ntnu.idatt1002.dao;

import ntnu.idatt1002.User;

import java.io.File;

public final class ProjectDAO {
    private static final String SAVEPATH = "src/main/resources/saves";

    /**
     * Get all projects from a user
     * @return {@code String[]} of all projects. {@code null} if empty
     */
    public static String[] list(String username){
        File directory = new File(projectsPath(username));
        return directory.list();
    }

    /**
     * Add new project to user
     * @return {@code false} if folder could not be created
     */
    public static boolean add(String username, String project){
        File directory = new File(projectsPath(username) + project);
        return directory.mkdir();
    }

    /**
     * Delete all projects for a user
     * @return {@code false} if some the projects could not be deleted
     */
    public static boolean deleteByUser(String username){
        if(!UserDAO.exists(username)){ return false; }

        boolean success = true;
        String[] projects = list(username);

        for(String project : projects){
            if(!delete(username, project)){
                success = false;
            }
        }

        return success;
    }

    /**
     * Delete a project and all its categories
     * @return {@code false} if the project could not be deleted
     */
    public static boolean delete(String username, String project){
        if(!exists(username, project)){ return false; }

        File directory = new File(projectsPath(username) + project);
        boolean success = CategoryDAO.deleteByProject(username, project);

        if(!directory.delete()){ success = false; }

        return success;
    }

    /**
     * Check if given project exists
     * @return true or false
     */
    static boolean exists(String username, String project){
        if(UserDAO.exists(username)){
            File projectDir = new File(projectsPath(username) + project);
            return projectDir.exists();
        }else{
            return false;
        }
    }

    /**
     * Get projects directory
     */
    private static String projectsPath(String username){
        return (SAVEPATH + "/" + username + "/Projects/");
    }
}
