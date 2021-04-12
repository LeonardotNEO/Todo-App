package ntnu.idatt1002.dao;

import ntnu.idatt1002.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;

/**
 * Static class to access user objects in storage
 */
public final class UserDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILETYPE = ".ser";
    private static final String[] DIRECTORIES = {"Categories","Notifications","Projects"};

    /**
     * Get a list of all users in storage
     * @return an {@code ArrayList} of {@code User} objects
     */
    public static ArrayList<User> list(){
        ArrayList<User> users = new ArrayList<>();
        File saveDirectory = new File(SAVEPATH);

        //Get directories within user folder
        File[] dirPaths = saveDirectory.listFiles((File::isDirectory));

        //Deserialize all users in array
        if(dirPaths != null){
            for(File path : dirPaths){
                System.out.println(path.getName());
                User user = deserialize(path.getName());
                users.add(user);
            }
        }

        return users;
    }

    /**
     * Save user to storage. Will overwrite if equal user already is stored.
     * @param user {@code User} object
     */
    public static void serialize(User user){
        String username = user.getUsername();
        File userDir = new File(userDir(username));

        //Make directories if the user is new
        if(!userDir.exists()){
            boolean result = userDir.mkdir();
            for(String directory : DIRECTORIES){
                File dir = new File(userDir(username) + directory);
                result = dir.delete();
            }
        }

        GenericDAO.serialize(user, filePath(username));
    }

    /**
     * Get user object from storage.
     * @return {@code User} object, {@code null} if user could not be found.
     */
    public static User deserialize(String username){
        User user = null;
        if(exists(username)){
            user = (User) GenericDAO.deserialize(filePath(username));
        }
        return user;
    }

    /**
     * Delete a user and all its files.
     * @return {@code false} if the user folder could not be deleted
     */
    public static boolean delete(String username){
        boolean result;                         //Variable to deal with delete() return
        //User files and directory
        File userDir = new File(userDir(username));
        File[] files = userDir.listFiles();

        if(files != null){
            for(File file : files){
                result = file.delete();
            }
        }

        return userDir.delete();
    }

    /**
     * Check if given user exists in storage
     * @return true or false
     */
    static boolean exists(String username){
        File userDir = new File(userDir(username));
        return userDir.exists();
    }

    //PASSWORD SALT & HASHING
    /**
     * Generate a random salt to store in a user.
     * @return a random salt.
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * PBKDF2 method to hash a password with salt. Needs equal salt and equal password to produce
     * the same has.
     * @param password password in clear-text
     * @param salt a users personal salt
     * @return Hashed password in String format, {@code null} if an error occured.
     */
    public static String hashPassword(String password, byte[] salt){
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            //Convert byte[] to String
            StringBuilder stringBuilder = new StringBuilder();
            for(byte iByte : hash){
                stringBuilder.append(Integer.toString((iByte & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuilder.toString();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //PRIVATE FUNCTIONS
    /**
     * Get user directory
     */
    private static String userDir(String username){
        return (SAVEPATH + "/" + username + "/");
    }

    /**
     * Get file path
     */
    private static String filePath(String username){
        return (userDir(username) + username + FILETYPE);
    }
}
