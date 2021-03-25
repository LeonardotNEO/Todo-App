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

    /**
     * Get a list of all users in storage
     * @return an {@code ArrayList} of {@code User} objects
     */
    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>(16);
        File saveDirectory = new File(SAVEPATH);

        //Filter away files
        FilenameFilter filter = (directory1, name) -> !name.endsWith(".ser") && !name.contains(".gitkeep");
        String[] pathnames = saveDirectory.list(filter);

        //Remaining files should be user folders
        if(pathnames != null){
            for(String path : pathnames){
                User user = deserializeUser(path);
                users.add(user);
            }
        }

        return users;
    }

    /**
     * Save user to storage. Will overwrite if equal user already is stored.
     * @param user {@code User} object
     */
    public static void serializeUser(User user){
        String username = user.getUsername();
        File directory = new File(userDir(username));
        File file = new File(filePath(username));

        //Make directories if they're not existing
        if(!directory.exists()){
            boolean success;
            success = directory.mkdir() &&
            new File(directory.getPath() + "/Categories").mkdir() &&
            new File(directory.getPath() + "/Notifications").mkdir();

            if(!success){ System.out.println("Error occured"); }
        }

        //Write to file
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(user);

            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    /**
     * Get user object from storage.
     * @param username non case-sensitive username
     * @return User object with given username, {@code null} if user could not be found.
     */
    public static User deserializeUser(String username){
        File file = new File(filePath(username));
        User user = null;

        if(!file.exists()){
            return null;
        }
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            user = (User) ois.readObject();

            ois.close();
            fis.close();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Delete a user and all its files.
     * @param username non case-sensitive username
     * @return {@code false} if the user folder or some of its elements could not be deleted
     */
    public static boolean deleteUser(String username){
        if(!userExists(username)){ return false; }

        //Tasks & categories
        boolean success = CategoryDAO.deleteCategoriesByUser(username);
        File categoryDir = new File(userDir(username) + "Categories");
        if(!categoryDir.delete()){ success = false; }

        //Notifications
        if(!NotificationDAO.deleteNotifsByUser(username)){ success = false; }
        File notifDir = new File(userDir(username) + "Notifications");
        if(!notifDir.delete()){ success = false; }

        //User
        File userDir = new File(userDir(username));
        File userFile = new File(filePath(username));
        if(!userFile.delete() || !userDir.delete()){ success = false; }

        return success;
    }

    /**
     * Check if given user exists in storage
     * @param username non case-sensitive username
     * @return true or false
     */
    static boolean userExists(String username){
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

    //PRIVATE STRING FUNCTIONS
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
