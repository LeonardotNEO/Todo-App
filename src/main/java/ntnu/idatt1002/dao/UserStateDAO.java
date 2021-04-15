package ntnu.idatt1002.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Access what state the program is in, like which user is logged in and what category is selected
 */
public final class UserStateDAO {
    private static final String SAVEFILE1 = "src/main/resources/saves/userstate.ser";
    private static final String SAVEFILE2 = "src/main/resources/saves/loggedusers.ser";

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
            userstate = (String[]) GenericDAO.deserialize(SAVEFILE1);
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
        GenericDAO.serialize(values, SAVEFILE1);
    }

    /**
     * Get all users who are logged in
     */
    public static String[] getLoggedInUsers(){
        return (String[]) GenericDAO.deserialize(SAVEFILE2);
    }

    /**
     * Set all users who are logged in. Will overwrite previous save.
     */
    public static void setLoggedInUsers(String[] usernames){
        GenericDAO.serialize(usernames, SAVEFILE2);
    }

    /**
     * Add user to list over which users are logged in
     */
    public static void addLoggedUser(String username){
        String[] oldUsers = getLoggedInUsers();
        String[] newUsers = new String[oldUsers.length + 1];
        System.arraycopy(oldUsers, 0, newUsers, 0, oldUsers.length);
        newUsers[newUsers.length-1] = username;
        setLoggedInUsers(newUsers);
    }

    /**
     * Remove user from list over users who are logged in
     */
    public static void remove(String username){
        String[] oldUsers = getLoggedInUsers();
        String[] newUsers = (String[]) Arrays.stream(oldUsers).filter(str -> !str.equals(username)).toArray();
        setLoggedInUsers(newUsers);
    }

    /**
     * Clear list over which users who are logged in
     */
    public static void removeAll(){
        setLoggedInUsers(new String[0]);
    }

    /**
     * Check if user state file exists
     * @return true or false
     */
    public static boolean fileExists(){
        File file = new File(SAVEFILE1);
        return file.exists();
    }
}
