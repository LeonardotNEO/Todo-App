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

    public static void newUser(User user){
        userStorage.serialize(user);
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
        private static final String[] DIRECTORIES = {"Categories", "Projects", "Notifications", "Images"};

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
            }

            GenericDAO.serialize(user, userFile(username));
        }

        static User deserialize(String username){
            return (User) GenericDAO.deserialize(userFile(username));
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
