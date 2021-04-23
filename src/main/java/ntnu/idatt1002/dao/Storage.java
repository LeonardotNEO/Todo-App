package ntnu.idatt1002.dao;

import ntnu.idatt1002.Notification;
import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public final class Storage {
    private static final String SAVEPATH = "src/main/resources/saves/";
    private static final String FILETYPE = ".ser";

    private static String currentUser;

    /**
     * Read all {@link User} objects from storage.
     * @return an {@code ArrayList} of users.
     */
    public static ArrayList<User> listUsers(){
        return userStorage.list();
    }

    /**
     * Get a user object from storage.
     * @param username the users username.
     * @return a {@link User} object with corresponding username.
     */
    public static User getUser(String username){
        if(userExists(username)) {
            return userStorage.deserialize(username);
        }else{
            return null;
        }
    }

    /**
     * Call this method when a new user is registered. This method will serialize the user object
     * and create all neccesary directories within its folder.
     * @param user the new {@link User} object.
     */
    public static void newUser(User user){
        userStorage.serialize(user);
    }

    /**
     * Call this method when the current user is edited. This method renames the {@code username} variables
     * of all the users tasks and notifications, and rename the {@link User} objects username and its folder.
     * @param newUser the new user object.
     */
    public static void editUser(User oldUser, User newUser){
        if(oldUser.getUsername().equals(newUser.getUsername())){
            userStorage.editObject(oldUser, newUser);
        }else{
            currentUser = newUser.getUsername();
            userStorage.editName(oldUser, newUser);
            CommonDAO.edit(currentUser);
            NotificationDAO.edit(currentUser);
        }
    }

    /**
     * Delete a user from storage. This will delete all the users content as well.
     * @param username the users username.
     * @return {@code true} or {@code false}.
     */
    public static boolean deleteUser(String username){
        return userStorage.delete(username);
    }

    /**
     * Check if given user exists in storage
     * @param username the username to check.
     * @return {@code true} or {@code false}.
     */
    static boolean userExists(String username){
        for(User user : listUsers()){
            if(user.getUsername().equals(username)){ return true; }
        }
        return false;
    }

    /**
     * Call this method on login. This will set the current user in CommonDAO and populate its
     * variables.
     */
    public static void read(String username){
        currentUser = username;
        CommonDAO.setTasks(taskStorage.list(username));
        NotificationDAO.setNotifs(notifStorage.list(username));
    }

    /**
     * Call this method on logout. This will delete all elements removed since last call and create all new
     * elements added since last call.
     */
    public static void write(){
        //Delete removed projects
        String[] newProjects = CommonDAO.listAllProjects();
        String[] oldProjects = taskStorage.listProjects(currentUser);
        for(String project : oldProjects){
            if(Arrays.stream(newProjects).noneMatch(str -> str.equals(project))){
                taskStorage.deleteByProject(currentUser, project);
            }
        }

        //Delete removed categories
        oldProjects = taskStorage.listProjects(currentUser);
        for(String project : oldProjects){
            if(Arrays.asList(newProjects).contains(project)){
                String[] newCategories = CommonDAO.listCategories(project);
                String[] oldCategories = taskStorage.listCategories(currentUser, project);
                for(String category : oldCategories){
                    if(Arrays.stream(newCategories).noneMatch(str -> str.equals(category))){
                        taskStorage.deleteByCategory(currentUser, project, category);
                    }
                }
            }
        }

        //Delete removed tasks
        ArrayList<Task> newTasks = CommonDAO.listTasks();
        ArrayList<Task> oldTasks = taskStorage.listTasks(currentUser);
        for(Task task : oldTasks){
            if(!newTasks.contains(task)){ taskStorage.delete(currentUser, task);}
        }

        //Add new projects
        for(String project : newProjects){
            if(Arrays.stream(oldProjects).noneMatch(str -> str.equals(project))){
                taskStorage.addProject(currentUser, project);
            }
        }

        //Add new categories
        for(String project : newProjects){
            String[] newCategories = CommonDAO.listCategories(project);
            String[] oldCategories = taskStorage.listCategories(currentUser, project);
            for(String category : newCategories){
                if(Arrays.stream(oldCategories).noneMatch(str -> str.equals(category))){
                    taskStorage.addCategory(currentUser, project, category);
                }
            }
        }

        //Add new tasks
        oldTasks = taskStorage.listTasks(currentUser);
        for(Task task : newTasks){
            if(!oldTasks.contains(task)){ taskStorage.serialize(currentUser, task);}
        }
    }

    private static final class userStorage{
        private static final String[] DIRECTORIES = {"Projects", "Notifications", "Images"};

        static ArrayList<User> list(){
            File dir = new File(SAVEPATH);
            File[] userDirs = dir.listFiles(File::isDirectory);
            ArrayList<User> users = new ArrayList<>();

            if(userDirs != null) {
                for (File username : userDirs) {
                    users.add((User) GenericDAO.deserialize(userFile(username.getName())));
                }
            }

            return users;
        }

        static void serialize(User user){
            String username = user.getUsername();
            File userDir = new File(SAVEPATH + username);

            //If user is new, create directories
            if(!userDir.exists()){
                userDir.mkdir();
                for(String dirName : DIRECTORIES){
                    File directory = new File(userDir.getPath() + "/" + dirName);
                    directory.mkdir();
                }
                new File(userDir + "/Projects/Standard/").mkdir();
            }

            GenericDAO.serialize(user, userFile(username));
        }

        static User deserialize(String username){
            return (User) GenericDAO.deserialize(userFile(username));
        }

        static void editName(User oldUser, User newUser){
            File oldUserDir = new File(SAVEPATH + oldUser.getUsername());
            oldUserDir.renameTo(new File(SAVEPATH + newUser.getUsername()));
            editObject(oldUser, newUser);
        }

        static void editObject(User oldUser, User newUser){
            File userFile = new File(userFile(oldUser.getUsername()));
            userFile.delete();
            serialize(newUser);
        }

        static boolean delete(String username){
            if(!userExists(username)){ return false; }

            //Method calls
            taskStorage.deleteByUser(username);
            notifStorage.deleteByUser(username);
            ImageDAO.deleteByUser(username);

            //Directories
            for(String directory : DIRECTORIES){
                File dir = new File(userDir(username) + directory);
                dir.delete();
            }

            //User files and directory
            File userDir = new File(userDir(username));
            File[] files = userDir.listFiles();

            if(files != null){
                for(File file : files){
                    file.delete();
                }
            }

            return userDir.delete();
        }

        //Get paths
        private static String userDir(String username){
            return (SAVEPATH + username + "/");
        }

        private static String userFile(String username){
            return (userDir(username) + username + FILETYPE);
        }
    }

    private static final class taskStorage{
        private static final String PREFIX = "task";

        /**
         * Get a HashMap with projects, categories and tasks for a {@link User}.
         * @param username the users username.
         * @return a HashMap.
         */
        static HashMap<String, HashMap<String, ArrayList<Task>>> list(String username){
            HashMap<String, HashMap<String, ArrayList<Task>>> projects = new HashMap<>();

            File projectsDir = new File(projectsDir(username));
            String[] projs = projectsDir.list();
            if(projs != null){
                for(String project : projs){
                    projects.put(project, list(username, project));
                }
            }

            return projects;
        }

        /**
         * Get a HashMap with categories and tasks for a {@link User}.
         * @param username the users username.
         * @param project the project name.
         * @return a HashMap.
         */
        private static HashMap<String, ArrayList<Task>> list(String username, String project){
            HashMap<String, ArrayList<Task>> categories = new HashMap<>();

            File projectDir = new File(projectsDir(username) + project + "/");
            String[] cats = projectDir.list();
            if(cats != null){
                for(String cat : cats){
                    categories.put(cat, list(username, project, cat));
                }
            }

            return categories;
        }

        /**
         * Get an ArrayList with tasks for a {@link User}.
         * @param username the users username.
         * @param project the project name.
         * @param category the category name.
         * @return an ArrayList.
         */
        private static ArrayList<Task> list(String username, String project, String category){
            ArrayList<Task> tasks = new ArrayList<>();

            File categoryDir = new File(projectsDir(username) + project + "/" + category + "/");
            File[] filepaths = categoryDir.listFiles();
            if(filepaths != null){
                for(File file : filepaths){
                    tasks.add((Task) GenericDAO.deserialize(file.getPath()));
                }
            }

            return tasks;
        }

        /**
         * Get an array of project names for a user.
         * @param username the users username.
         * @return an array of strings.
         */
        static String[] listProjects(String username){
            File projectsDir = new File(projectsDir(username));
            return projectsDir.list();
        }

        /**
         * Get an array of category names for a users project.
         * @param username the users username.
         * @param project the project name.
         * @return an array of strings.
         */
        static String[] listCategories(String username, String project){
            File projectDir = new File(projectsDir(username) + project);
            return projectDir.list();
        }

        /**
         * Get an arraylist of all tasks for a user.
         * @param username the users username.
         * @return an arraylist of task objects.
         */
        static ArrayList<Task> listTasks(String username){
            ArrayList<Task> allTasks = new ArrayList<>();

            for(String project : listProjects(username)){
                for(String category : listCategories(username, project)){
                    File categoryDir = new File(categoryDir(username, project, category));
                    File[] filepaths = categoryDir.listFiles();
                    if(filepaths != null){
                        for(File file : filepaths){
                            allTasks.add((Task) GenericDAO.deserialize(file.getPath()));
                        }
                    }
                }
            }

            return allTasks;
        }

        /**
         * Make a new project for a user.
         * @param username the users username.
         * @param project the project name.
         */
        static void addProject(String username, String project){
            File projectDir = new File(projectsDir(username) + project);
            projectDir.mkdir();
        }

        /**
         * Make a new category for a users project.
         * @param username the users username.
         * @param project the project name.
         * @param category the category name.
         */
        static void addCategory(String username, String project, String category){
            File categoryDir = new File(categoryDir(username, project, category));
            categoryDir.mkdir();
        }

        /**
         * Serialize a task object to a users folder.
         * @param username the users username.
         * @param task the task object.
         */
        static void serialize(String username, Task task){
            GenericDAO.serialize(task, filepath(username, task));
        }

        static void deleteByUser(String username){
            File projectsDir = new File(projectsDir(username));
            String[] projects = projectsDir.list();
            if(projects != null) {
                for (String project : projects) {
                    deleteByProject(username, project);
                    File dir = new File(projectsDir.getPath() + "/" + project);
                    dir.delete();
                }
            }
        }

        static void deleteByProject(String username, String project){
            File projectDir = new File(projectsDir(username) + project);
            String[] categories = projectDir.list();
            if(categories != null){
                for(String category : categories){
                    deleteByCategory(username, project, category);
                    File dir = new File(projectDir.getPath() + "/" + category);
                    dir.delete();
                }
            }
            projectDir.delete();
        }

        static void deleteByCategory(String username, String project, String category){
            File categoryDir = new File(categoryDir(username, project, category));
            File[] tasks = categoryDir.listFiles();
            if(tasks != null){
                for(File task : tasks){
                    task.delete();
                }
            }
            categoryDir.delete();
        }

        static void delete(String username, Task task){
            File file = new File(filepath(username, task));
            file.delete();
        }

        private static String projectsDir(String username){
            return (SAVEPATH + username + "/Projects/");
        }

        private static String categoryDir(String username, String project, String category){
            return (projectsDir(username) + project + "/" + category + "/");
        }

        private static String filepath(String username, Task task){
            String project = (task.getProject() == null ? "Standard" : task.getProject());
            String category = task.getCategory();
            long id = task.getId();
            return (categoryDir(username, project, category) + PREFIX + id + FILETYPE);
        }
    }

    private static final class notifStorage{
        /**
         * Get an ArrayList with notifications for a {@link User}.
         * @param username the users username.
         * @return an ArrayList.
         */
        static ArrayList<Notification> list(String username){
            ArrayList<Notification> notifs = new ArrayList<>();

            File notifDir = new File(SAVEPATH + username + "/Notifications/");
            File[] filepaths = notifDir.listFiles();
            if(filepaths != null){
                for(File file : filepaths){
                    notifs.add((Notification) GenericDAO.deserialize(file.getPath()));
                }
            }

            return notifs;
        }

        static void deleteByUser(String username){
            File notifDir = new File(SAVEPATH + username + "/Notifications/");
            File[] filepaths = notifDir.listFiles();
            if(filepaths != null){
                for(File file : filepaths){
                    file.delete();
                }
            }
        }
    }
}
