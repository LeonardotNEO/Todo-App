package ntnu.idatt1002.dao;

import ntnu.idatt1002.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;

/**
 * Access user objects in storage
 */
public final class UserDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String FILETYPE = ".ser";

    /**
     * Get all users in storage
     * @return an {@code ArrayList} of {@code User} objects
     */
    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>(16);
        File saveDirectory = new File(SAVEPATH);

        //Filter away files
        FilenameFilter filter = (directory1, name) -> !name.endsWith(".ser") && !name.contains(".gitkeep");
        String[] pathnames = saveDirectory.list(filter);

        if(pathnames != null){
            for(String path : pathnames){
                User user = deserializeUser(path);
                users.add(user);
            }
        }

        return users;
    }

    /**
     * Save user to storage
     * @param user {@code User} object
     */
    public static void serializeUser(User user){
        String username = user.getUsername();
        File directory = new File(userDir(username));
        File file = new File(filePath(username));

        /* Try to save user to directory. If it fails it may be because the folder is not created.
        In that instance it will create said folder and try again. */
        for(int i=0; i<2; i++) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(user);

                oos.close();
                fos.close();
                break;
            } catch (FileNotFoundException fnfe) {
                boolean mkdir = directory.mkdir();
                if (!mkdir) { fnfe.printStackTrace(); }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * Get user object from storage
     * @return {@code null} if error occurs
     */
    public static User deserializeUser(String username){
        File file = new File(filePath(username));
        User user = null;
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
     * Delete a user and all its files
     * @return {@code false} if the user folder or some of its elements could not be deleted
     */
    public static boolean deleteUser(String username){
        //Tasks & categories
        CategoryDAO.deleteCategoriesByUser(username);
        File categoryDir = new File(userDir(username) + "Categories");
        boolean success = categoryDir.delete();

        //Notifications
        NotificationDAO.deleteNotifsByUser(username);
        File notifDir = new File(userDir(username) + "Notifications");
        if(!notifDir.delete()){ success = false; }

        //User
        File userDir = new File(userDir(username));
        File userFile = new File(filePath(username));
        if(!userFile.delete() || !userDir.delete()){ success = false; }

        return success;
    }

    /**
     * @return a random salt
     */
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * PBKDF2 method to hash a password with salt
     * @param password password in clear-text
     * @param salt a users personal salt
     * @return {@code null} if an error occured
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
