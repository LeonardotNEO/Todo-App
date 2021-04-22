package ntnu.idatt1002;

import ntnu.idatt1002.service.UserService;
import ntnu.idatt1002.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A class which functions as a user of the application
 * This class contains the basic method of any given user
 *
 * Setters methods automatically changes the savefile of this userobject
 */
public class User implements Serializable {
    private String username;
    private String password;
    private byte[] salt;
    private long dateCreated;

    // settings
    String currentlySelectedCategory = null;
    String currentlySelectedProject = null;
    String currentlySelectedProjectCategory = null;
    String currentlySelectedSort = null;
    String currentlySelectedBackground = "-fx-background-color: #e6e6e6";
    boolean rememberMe = false;
    String theme = null;
    boolean deleteTaskDontShowAgainCheckbox;
    boolean finishTaskDontShowAgainCheckbox;

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
        this.theme = "-fx-color-1: #001021; -fx-color-2: #001933 ; -fx-color-3: #00254d; -fx-color-4: orange;";
    }

    /**
     * Constructor for copying an user object
     * @param user
     */
    public User(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.salt = user.getSalt();
        this.dateCreated = user.getDateCreated();
        this.currentlySelectedCategory = user.getCurrentlySelectedCategory();
        this.currentlySelectedProject = user.getCurrentlySelectedProject();
        this.currentlySelectedProjectCategory = user.getCurrentlySelectedProjectCategory();
        this.currentlySelectedSort = user.currentlySelectedSort;
        this.rememberMe = user.isRememberMe();
        this.theme = user.getTheme();
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
        User userBeforeChanges = new User(this);
        this.username = username;

        UserService.editUser(userBeforeChanges, this);
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
        User userBeforeChanges = new User(this);
        this.password = password;

        UserService.editUser(userBeforeChanges, this);
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
        User userBeforeChanges = new User(this);
        this.salt = salt;

        UserService.editUser(userBeforeChanges, this);
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public String getCurrentlySelectedCategory() {
        return currentlySelectedCategory;
    }

    public void setCurrentlySelectedCategory(String currentlySelectedCategory) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedCategory = currentlySelectedCategory;
        this.currentlySelectedProjectCategory = null;
        this.currentlySelectedProject = null;

        UserService.editUser(userBeforeChanges, this);
    }

    public String getCurrentlySelectedProject() {
        return currentlySelectedProject;
    }

    public void setCurrentlySelectedProject(String currentlySelectedProject) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedProject = currentlySelectedProject;
        this.currentlySelectedCategory = null;

        UserService.editUser(userBeforeChanges, this);
    }

    public String getCurrentlySelectedProjectCategory() {
        return currentlySelectedProjectCategory;
    }

    public void setCurrentlySelectedProjectCategory(String currentlySelectedProjectCategory) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedProjectCategory = currentlySelectedProjectCategory;
        this.currentlySelectedCategory = null;

        UserService.editUser(userBeforeChanges, this);
    }

    public String getCurrentlySelectedSort() {
        return currentlySelectedSort;
    }

    public void setCurrentlySelectedSort(String currentlySelectedSort) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedSort = currentlySelectedSort;

        UserService.editUser(userBeforeChanges, this);
    }

    public String getCurrentlySelectedBackground() {
        return currentlySelectedBackground;
    }

    public void setCurrentlySelectedBackground(String currentlySelectedBackground) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedBackground = currentlySelectedBackground;

        UserService.editUser(userBeforeChanges, this);
    }

    // For deleteTask popup
    public boolean isDeleteTaskDontShowAgainCheckbox() {
        return deleteTaskDontShowAgainCheckbox;
    }

    // For deleteTask popup
    public void setDeleteTaskDontShowAgainCheckbox(boolean checkbox) {
        User userBeforeChanges = new User(this);
        this.deleteTaskDontShowAgainCheckbox = checkbox;

        UserService.editUser(userBeforeChanges, this);
    }

    // For finishTask popup
    public boolean isFinishTaskDontShowAgainCheckbox() {
        return finishTaskDontShowAgainCheckbox;
    }

    // For finishTask popup
    public void setFinishTaskDontShowAgainCheckbox(boolean checkbox) {
        User userBeforeChanges = new User(this);
        this.finishTaskDontShowAgainCheckbox = checkbox;

        UserService.editUser(userBeforeChanges, this);
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        User userBeforeChanges = new User(this);
        this.theme = theme;

        UserService.editUser(userBeforeChanges, this);
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        User userBeforeChanges = new User(this);
        this.rememberMe = rememberMe;

        UserService.editUser(userBeforeChanges, this);
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
