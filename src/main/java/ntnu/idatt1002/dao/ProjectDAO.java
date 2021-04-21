package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;

import java.io.File;

/**
 * The class {@code ProjectDAO} provides static methods for handling projects under a {@link User} folder.
 * In the storage system projects are stored under the 'Projects' directory, and each project are stored as
 * folders. Under these project folders their respective categories are stored, which containts
 * {@link Task} files.
 */
public final class ProjectDAO {
    private static final String SAVEPATH = "src/main/resources/saves";

    /**
     * Returns a {@link String}[] of all projects under a {@link User} folder.
     * @param username the {@code username} variable in a {@link User}.
     * @return an array of projects. Returns {@code null} if folder is empty.
     */
    public static String[] list(String username){
        File directory = new File(projectsPath(username));
        return directory.list();
    }

    /**
     * Add a new project to a {@link User}. This folder will be created under the
     * 'Projects' folder.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the new project.
     * @return {@code True} if succesfull. {@code False} if directory could not be created.
     * Either the user doesn't exist or a folder with the given name already exists.
     */
    public static boolean add(String username, String project){
        File directory = new File(projectsPath(username) + project);
        return directory.mkdir();
    }

    /**
     * Delete all projects inside a {@link User}. This will empty the 'Projects' folder,
     * all categories inside the projects, and their {@link Task} files.
     * @param username the {@code username} variable in a {@link User}.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete,
     * normally if the user doesn't exist.
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
     * Delete a project in a {@link User} folder with all categories and {@link Task} files within.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project to delete.
     * @return {@code True} if succesfull. {@code False} if some or all elements failed to delete,
     * either the user or the project doesn't exist.
     */
    public static boolean delete(String username, String project){
        if(!exists(username, project)){ return false; }

        File directory = new File(projectsPath(username) + project);
        boolean success = CategoryDAO.deleteByProject(username, project);

        if(!directory.delete()){ success = false; }

        return success;
    }

    /**
     * Check if the project exists.
     * @param username the {@code username} variable in a {@link User}.
     * @param project the name of the project.
     * @return {@code True} or {@code False}.
     */
    static boolean exists(String username, String project){
        if(UserDAO.exists(username)){
            File projectDir = new File(projectsPath(username) + project);
            return projectDir.exists();
        }else{
            return false;
        }
    }

    //Get paths
    private static String projectsPath(String username){
        return (SAVEPATH + "/" + username + "/Projects/");
    }
}
