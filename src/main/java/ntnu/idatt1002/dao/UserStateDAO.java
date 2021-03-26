package ntnu.idatt1002.dao;

import java.io.*;

/**
 * Access what state the program is in, like which user is logged in and what category is selected
 */
public final class UserStateDAO {
    private static final String SAVEFILE = "src/main/resources/saves/userstate.ser";

    /**
     * Get which user is logged in
     * @return String of username, {@code null} if value is not stored
     */
    public static String getUsername(){
        return getUserState(0);
    }

    /**
     * Get which category is selected by user
     * @return String of selected category, {@code null} if value is not stored
     */
    public static String getSelectedCategory(){
        return getUserState(1);
    }

    /**
     * Get which sorting method is selected by user
     * @return String of selected sorting, {@code null} if value is not stored
     */
    public static String getSelectedSort(){
        return getUserState(2);
    }

    /**
     * Get if rememberMe is true or false
     * @return String of true or false, {@code null} if value is not stored
     */
    public static String getRememberMe(){
        return getUserState(3);
    }

    /**
     * Returns a saved state
     * @param index <p>0: username<br>
     *              1: selectedCategory<br>
     *              2: selectedSort</p>
     *              3: rememberMe</p>
     * @return String with chosen value, {@code null} if value is not stored
     */
    private static String getUserState(int index){
        File file = new File(SAVEFILE);
        String[] userstate = null;
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            userstate = (String[]) ois.readObject();

            ois.close();
            fis.close();
        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        if(userstate != null && index >= 0 && index <= 3) {
            return userstate[index];
        }else{
            return null;
        }
    }

    /**
     * Set current state
     * @param username user currently logged in
     * @param selectedCategory what category is selected
     * @param selectedSort what sorting method is selected
     */
    public static void setUserState(String username, String selectedCategory, String selectedSort, String rememberMe){
        File file = new File(SAVEFILE);
        String[] values = {username, selectedCategory, selectedSort, rememberMe};
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(values);

            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Check if user state file exists
     * @return true or false
     */
    public static boolean fileExists(){
        File file = new File(SAVEFILE);
        return file.exists();
    }
}
