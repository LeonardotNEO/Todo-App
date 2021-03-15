package ntnu.idatt1002.dao;

import ntnu.idatt1002.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;

public final class UserDAO {
    private static final String SAVEPATH = "resources/saves/";
    private static final String FILETYPE = ".ser";

    /**
     * Get all users in storage
     * @return an {@code ArrayList} of {@code User} objects
     */
    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>(16);
        File saveDirectory = new File(SAVEPATH);
        String[] pathnames = saveDirectory.list();

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
    private static void serializeUser(User user){
        File file = new File(SAVEPATH + user.getUsername() + FILETYPE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(user);

            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Get user object from storage
     * @param username case-sensitive username
     * @return {@code null} if error occurs
     */
    private static User deserializeUser(String username){
        File file = new File(SAVEPATH + username + FILETYPE);
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
}
