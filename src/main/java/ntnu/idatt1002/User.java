package ntnu.idatt1002;

import ntnu.idatt1002.service.TaskService;
import ntnu.idatt1002.service.UserService;
import ntnu.idatt1002.service.UserStateService;
import ntnu.idatt1002.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A class which functions as a user of the application
 * This class contains the basic method of any given user
 */
public class User implements Serializable {
    private String username;
    private String password;
    private byte[] salt;
    private long dateCreated;

    // settings
    String currentlySelectedCategory = "";
    String currentlySelectedSort = "";
    boolean rememberMe = false;
    String theme = "";

    public User(){}

    /**
     * A constructor for the user class which only needs a username
     * @param username
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * A constructor for the user class which needs all the necessary information on a user
     * @param username
     * @param password
     * @param salt
     */
    public User(String username, String password, byte[] salt){
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.dateCreated = DateUtils.getAsMs(LocalDate.now());
    }

    /**
     * A method to get the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * A method to set the username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
        UserService.editUser(UserStateService.getCurrentUser(), this);
    }

    /**
     * A method to get the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * A method to set the password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
        UserService.editUser(UserStateService.getCurrentUser(), this);
    }

    /**
     * A method to get the salt
     * @return the salt
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * A method to set the salt
     * @param salt
     */
    public void setSalt(byte[] salt) {
        this.salt = salt;
        UserService.editUser(UserStateService.getCurrentUser(), this);
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public String getCurrentlySelectedCategory() {
        return currentlySelectedCategory;
    }

    public void setCurrentlySelectedCategory(String currentlySelectedCategory) {
        this.currentlySelectedCategory = currentlySelectedCategory;
        UserService.editUser(UserStateService.getCurrentUser(), this);
    }

    public String getCurrentlySelectedSort() {
        return currentlySelectedSort;
    }

    public void setCurrentlySelectedSort(String currentlySelectedSort) {
        this.currentlySelectedSort = currentlySelectedSort;
        UserService.editUser(UserStateService.getCurrentUser(), this);
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
        UserService.editUser(UserStateService.getCurrentUser(), this);
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
        UserService.editUser(UserStateService.getCurrentUser(), this);
    }

    /** // Hvor skal dette?
     * A equals method which compares a objects content with this user-object
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    /**
     * A method which returns hashCode of the user object
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
