package ntnu.idatt1002.dao;

import ntnu.idatt1002.Task;
import ntnu.idatt1002.User;

import java.io.File;
import java.util.ArrayList;
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
        return userStorage.deserialize(username);
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
     * Call this method when the current user is edited. This method rename the {@code username} variables
     * of all the users tasks, rename the {@link User} objects username and its folder.
     * @param newUser the new user object.
     */
    public static void editUser(User oldUser, User newUser){
        if(oldUser.getUsername().equals(newUser.getUsername())){
            userStorage.editObject(oldUser, newUser);
        }else{
            currentUser = newUser.getUsername();
            userStorage.editName(oldUser, newUser);
            CommonDAO.edit(currentUser);
        }
    }

    /**
     * Delete a user from storage. This will delete all the users content as well.
     * @param username the users username.
     * @return {@code true} or {@code false}.
     */
    public static boolean deleteUser(String username){
        return true;
    }

    /**
     * Call this method on login. This will set the current user in CommonDAO and populate its
     * variables.
     */
    public static void read(String username){
        currentUser = username;
        CommonDAO.setTasks(taskStorage.list(username));
    }

    /**
     * Call this method on logout. This will delete all elements removed since last call, create all new
     * elements and overwrite all changed elements.
     */
    public static void write(){

    }

    private static final class userStorage{
        private static final String[] DIRECTORIES = {"Projects", "Notifications", "Images"};

        static ArrayList<User> list(){
            File dir = new File(SAVEPATH);
            String[] userDirs = dir.list();
            ArrayList<User> users = new ArrayList<>();

            if(userDirs != null) {
                for (String username : userDirs) {
                    users.add((User) GenericDAO.deserialize(userFile(username)));
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

        //Get paths
        private static String userFile(String username){
            return (SAVEPATH + username + "/" + username + FILETYPE);
        }
    }

    private static final class taskStorage{
        /**
         * Get a HashMap with projects, categories and tasks for a {@link User}.
         * @param username the users username.
         * @return a HashMap.
         */
        static HashMap<String, HashMap<String, ArrayList<Task>>> list(String username){
            return null;
        }
    }
}
