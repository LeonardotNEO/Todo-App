package ntnu.idatt1002.dao;

import ntnu.idatt1002.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;

/**
 * The class {@code UserDAO} provides static methods for handling {@link User} objects.
 * In the storage system each user is saved as folders inside the 'resources/saves/' directory.
 * Inside each user folder the {@link User} object gets serialized, along with the directories
 * 'Categories', 'Notifications', 'Projects' and 'Images'.
 */
public final class UserDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILETYPE = ".ser";
    private static final String[] DIRECTORIES = {"Categories","Notifications","Projects","Images"};

    /**
     * Returns an {@link ArrayList} of all {@link User}'s stored.
     * @return an {@link ArrayList}.
     */
    public static ArrayList<User> list(){
        ArrayList<User> users = new ArrayList<>();
        File saveDirectory = new File(SAVEPATH);

        //Get directories within user folder
        File[] dirPaths = saveDirectory.listFiles((File::isDirectory));

        //Deserialize all users in array
        if(dirPaths != null){
            for(File path : dirPaths){
                User user = deserialize(path.getName());
                users.add(user);
            }
        }

        return users;
    }

    /**
     * Serialize a {@link User} object to storage. The file will be stored under the user folder.
     * If the user is new all necessary directories will be created. It's important that the user's
     * {@code password} variable is the hashed version and not the plaintext, since this will be stored
     * in the computers harddrive.
     * @param user the {@link User} object.
     */
    public static void serialize(User user){
        String username = user.getUsername();
        File userDir = new File(userDir(username));

        //Make directories if the user is new
        if(!userDir.exists()){
            boolean result = userDir.mkdir();
            for(String directory : DIRECTORIES){
                File dir = new File(userDir(username) + directory);
                result = dir.mkdir();
            }
        }

        GenericDAO.serialize(user, filePath(username));
    }

    /**
     * Get a {@link User} object from storage with the given username.
     * @param username the username.
     * @return a {@link User} object, will be {@code null} if the user doesn't exist.
     */
    public static User deserialize(String username){
        User user = null;
        if(exists(username)){
            user = (User) GenericDAO.deserialize(filePath(username));
        }
        return user;
    }

    /**
     * Delete a {@link User} from storage with all its contents.
     * @param username the username.
     * @return {@code True} if succesfull. {@code False} if some or all of the files/directories couldn't
     * be deleted, normally since the user doesn't exist.
     */
    public static boolean delete(String username){
        if(!exists(username)){ return false; }

        boolean result;                         //Variable to deal with delete() return

        //Method calls
        result = CategoryDAO.deleteByUser(username);
        result = ProjectDAO.deleteByUser(username);
        result = TaskDAO.deleteByUser(username);
        result = NotificationDAO.deleteByUser(username);
        result = ImageDAO.deleteByUser(username);

        //Directories
        for(String directory : DIRECTORIES){
            File dir = new File(userDir(username) + directory);
            result = dir.delete();
        }

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
     * Check if the given {@link User} exists in storage.
     * @param username the username.
     * @return {@code true} or {@code false}.
     */
    static boolean exists(String username){
        File userDir = new File(userDir(username));
        return userDir.exists();
    }

    //PASSWORD SALT & HASHING

    /**
     * Returns a {@code byte[]} to be passed into the {@link #hashPassword(String, byte[])} method as salt.
     * This needs to be stored in a {@link User} object along with the hashed password for later
     * verification.
     * @return a {@code byte[]} with 16 random bytes.
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    //SALT & HASH METHODS
    //-------------------------
    /**
     * Returns a {@link String} with the hashed password using the given plaintext and salt.
     * Salt can be generated with the {@link #generateSalt()} method.
     * This method uses PBKDF2.
     * @param password plaintext password.
     * @param salt {@code salt} for a {@link User}.
     * @return a {@link String} containing the hashed password, or {@code null} if an error occured.
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

    //Get paths
    private static String userDir(String username){
        return (SAVEPATH + "/" + username + "/");
    }

    private static String filePath(String username){
        return (userDir(username) + username + FILETYPE);
    }
}
