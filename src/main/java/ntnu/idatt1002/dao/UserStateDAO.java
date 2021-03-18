package ntnu.idatt1002.dao;

import java.io.*;

/**
 * Simple class to get or set which user are logged in
 */
public final class UserStateDAO {
    private static final String SAVEFILE = "src/main/resources/saves/userstate.ser";

    /**
     * Returns which user is logged in
     * @return {@code null} if no user is logged in
     */
    public static String getUserState(){
        File file = new File(SAVEFILE);
        String userstate = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            userstate = (String) ois.readObject();

            ois.close();
            fis.close();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return userstate;
    }

    /**
     * Set which user is logged in
     * @param username the user to log in, or {@code null} to log out current user
     */
    public static void setUserState(String username){
        File file = new File(SAVEFILE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(username);

            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
