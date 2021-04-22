package ntnu.idatt1002.dao;

import ntnu.idatt1002.User;

import java.io.File;
import java.util.ArrayList;

public final class Storage {
    private static final String SAVEPATH = "src/main/resources/saves/";
    private static final String FILETYPE = ".ser";

    private static User currentUser;

    public static ArrayList<User> readUsers(){
        return userStorage.list();
    }

    public static User getUser(String username){
        return userStorage.deserialize(username);
    }

    public static void read(){

    }

    public static void write(){

    }

    private static final class userStorage{
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

        static User deserialize(String username){
            return (User) GenericDAO.deserialize(userFile(username));
        }

        //Get paths
        private static String userFile(String username){
            return (SAVEPATH + username + "/" + username + FILETYPE);
        }
    }
}
