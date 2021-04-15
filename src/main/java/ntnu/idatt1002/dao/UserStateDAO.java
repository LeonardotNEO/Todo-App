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
     * @return true or false, {@code null} if value is not stored
     */
    public static boolean getRememberMe(){
        return Boolean.parseBoolean(getUserState(3));
    }

    /**
     * Returns a saved state
     * @param index <p>0: username<br>
     *              1: selectedCategory<br>
     *              2: selectedSort<br>
     *              3: rememberMe</p>
     * @return String with chosen value, {@code null} if value is not stored
     */
    private static String getUserState(int index){
        String[] userstate = null;

        if(fileExists()){
            userstate = (String[]) GenericDAO.deserialize(SAVEFILE);
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
     * @param rememberMe if user will be remembered to next start up
     */
    public static void setUserState(String username, String selectedCategory, String selectedSort,
                                    boolean rememberMe){
        String[] values = {username, selectedCategory, selectedSort, String.valueOf(rememberMe)};
        GenericDAO.serialize(values, SAVEFILE);
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
