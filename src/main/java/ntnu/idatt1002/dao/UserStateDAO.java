package ntnu.idatt1002.dao;

import ntnu.idatt1002.User;

import java.io.*;
import java.util.Arrays;

/**
 * The class {@code UserStateDAO} provides static methods for handling the two state files under
 * 'resources/saves/': 'userstate.ser' and 'loggedusers.ser'.
 * 'userstates.ser' keeps track of the programs state: user currently logged in, category
 *  selected, sorting method selected and if the user has pressed 'remember me'.
 *  'loggedusers.ser' contains every user that is logged in, and is used to easily log in and out from
 *  different users at the login screen.
 */
public final class UserStateDAO {
    private static final String SAVEFILE1 = "src/main/resources/saves/userstate.ser";
    private static final String SAVEFILE2 = "src/main/resources/saves/loggedusers.ser";

    /**
     * Returns the {@code username} of the {@link User} currently logged in to the program.
     * @return a {@link String} containing the username. {@code null} if nobody is logged in.
     */
    public static String getUsername(){
        return getUserState(0);
    }

    /**
     * Returns the currently selected category.
     * @return a {@link String} containing the category. {@code null} if no category is selected.
     */
    public static String getSelectedCategory(){
        return getUserState(1);
    }

    /**
     * Returns the currently selected sorting method.
     * @return a {@link String} containing the sorting method. {@code null} if no sorting method is
     * selected.
     */
    public static String getSelectedSort(){
        return getUserState(2);
    }

    /**
     * Check if the 'remember me' button has been checked.
     * @return {@code true} or {@code false}.
     */
    public static boolean getRememberMe(){
        return Boolean.parseBoolean(getUserState(3));
    }

    /**
     * Returns a stored state based on the given argument.
     * @param index <p>0: username<br>
     *              1: selectedCategory<br>
     *              2: selectedSort<br>
     *              3: rememberMe</p>
     * @return a {@link String} containing the state, or {@code null} if the state is not stored.
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
     * Set current state of the program.
     * @param username user currently logged in.
     * @param selectedCategory what category is selected.
     * @param selectedSort what sorting method is selected.
     * @param rememberMe if the user will be remembered to next start up.
     */
    public static void setUserState(String username, String selectedCategory, String selectedSort,
                                    boolean rememberMe){
        String[] values = {username, selectedCategory, selectedSort, String.valueOf(rememberMe)};
        GenericDAO.serialize(values, SAVEFILE1);
    }

    /**
     * Returns a {@link String}[] of the usernames of all {@link User}'s logged in.
     * @return an array of usernames.
     */
    public static String[] getLoggedInUsers(){
        return (String[]) GenericDAO.deserialize(SAVEFILE2);
    }

    /**
     * Set all users who are logged in. This will overwrite previous save.
     * @param usernames a {@link String}[] of all usernames.
     */
    public static void setLoggedInUsers(String[] usernames){
        GenericDAO.serialize(usernames, SAVEFILE2);
    }

    /**
     * Add a user to the list over which users are logged in.
     * @param username the username.
     */
    public static void addLoggedUser(String username){
        String[] oldUsers = getLoggedInUsers();
        String[] newUsers = new String[oldUsers.length + 1];
        System.arraycopy(oldUsers, 0, newUsers, 0, oldUsers.length);
        newUsers[newUsers.length-1] = username;
        setLoggedInUsers(newUsers);
    }

    /**
     * Remove a user from the list over users who are logged in.
     * @param username the username.
     */
    public static void removeFromList(String username){
        String[] oldUsers = getLoggedInUsers();
        Object[] objects = Arrays.stream(oldUsers).filter(str -> !str.equals(username)).toArray();
        String[] newUsers = new String[objects.length];
        for(int i=0; i< newUsers.length; i++){
            newUsers[i] = (String) objects[i];
        }
        setLoggedInUsers(newUsers);
    }

    /**
     * Clear the list over which users who are logged in.
     */
    public static void clearList(){
        setLoggedInUsers(new String[0]);
    }

    /**
     * Check if the user state file exists.
     * @return {@code true} or {@code false}.
     */
    public static boolean fileExists(){
        File file = new File(SAVEFILE1);
        return file.exists();
    }
}
