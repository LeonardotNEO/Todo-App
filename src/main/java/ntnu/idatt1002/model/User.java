package ntnu.idatt1002.model;

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
    boolean permanentDeleteDontShowAgainCheckbox;

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
     * @param username the users name
     * @param password pre-determined password
     * @param salt Genreated salt
     */
    public User(String username, String password, byte[] salt){
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.dateCreated = DateUtils.getAsMs(LocalDate.now());
        this.theme = "-fx-color-1: #001021; -fx-color-2: #001933 ; -fx-color-3: #00254d; -fx-text-hover-color: orange; -fx-color-text-color: white";
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

    /**
     * Returns the date which the user was first initialized.
     * @return a long representing the date.
     */
    public long getDateCreated() {
        return dateCreated;
    }

    /**
     * Methode to get the currently selected category.
     * @return the currently selected category.
     */
    public String getCurrentlySelectedCategory() {
        return currentlySelectedCategory;
    }

    /**
     * Methode for setting the currently selected category.
     * @param currentlySelectedCategory Name of the currently selected category.
     */
    public void setCurrentlySelectedCategory(String currentlySelectedCategory) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedCategory = currentlySelectedCategory;
        this.currentlySelectedProjectCategory = null;
        this.currentlySelectedProject = null;

        UserService.editUser(userBeforeChanges, this);
    }

    /**
     * Methode to get the currently selected project.
     * @return the currently selected project.
     */
    public String getCurrentlySelectedProject() {
        return currentlySelectedProject;
    }
    /**
     * Methode for setting the currently selected project.
     * @param currentlySelectedProject Name of the currently selected project.
     */
    public void setCurrentlySelectedProject(String currentlySelectedProject) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedProject = currentlySelectedProject;
        this.currentlySelectedCategory = null;

        UserService.editUser(userBeforeChanges, this);
    }
    /**
     * Methode to get the currently selected project category.
     * @return the currently selected project category.
     */
    public String getCurrentlySelectedProjectCategory() {
        return currentlySelectedProjectCategory;
    }
    /**
     * Methode for setting the currently selected project category.
     * @param currentlySelectedProjectCategory Name of the currently selected project category.
     */
    public void setCurrentlySelectedProjectCategory(String currentlySelectedProjectCategory) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedProjectCategory = currentlySelectedProjectCategory;
        this.currentlySelectedCategory = null;

        UserService.editUser(userBeforeChanges, this);
    }
    /**
     * Methode to get the currently selected sort.
     * @return the currently selected sort.
     */
    public String getCurrentlySelectedSort() {
        return currentlySelectedSort;
    }
    /**
     * Methode for setting the currently selected sort.
     * @param currentlySelectedSort Name of the currently selected sort.
     */
    public void setCurrentlySelectedSort(String currentlySelectedSort) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedSort = currentlySelectedSort;

        UserService.editUser(userBeforeChanges, this);
    }
    /**
     * Methode to get the currently selected background.
     * @return the currently selected background.
     */
    public String getCurrentlySelectedBackground() {
        return currentlySelectedBackground;
    }
    /**
     * Methode for setting the currently selected background.
     * @param currentlySelectedBackground Name of the currently selected background.
     */
    public void setCurrentlySelectedBackground(String currentlySelectedBackground) {
        User userBeforeChanges = new User(this);
        this.currentlySelectedBackground = currentlySelectedBackground;

        UserService.editUser(userBeforeChanges, this);
    }

    /**
     * Methode to get the isDeletedTaskDontShowAgainCheckbox boolean.
     * @return isDeletedTaskDontShowAgainCheckbox boolean.
     */
    public boolean isDeleteTaskDontShowAgainCheckbox() {
        return deleteTaskDontShowAgainCheckbox;
    }
    /**
     * Methode to set the isDeletedTaskDontShowAgainCheckbox boolean.
     * @param checkbox boolean.
     */
    public void setDeleteTaskDontShowAgainCheckbox(boolean checkbox) {
        User userBeforeChanges = new User(this);
        this.deleteTaskDontShowAgainCheckbox = checkbox;

        UserService.editUser(userBeforeChanges, this);
    }


    /**
     * Methode to get the isFinishTaskDontShowAgainCheckbox boolean.
     * @return isFinishTaskDontShowAgainCheckbox boolean.
     */
    public boolean isFinishTaskDontShowAgainCheckbox() {
        return finishTaskDontShowAgainCheckbox;
    }

    /**
     * Methode to set the isFinishTaskDontShowAgainCheckbox boolean.
     * @param checkbox boolean.
     */
    public void setFinishTaskDontShowAgainCheckbox(boolean checkbox) {
        User userBeforeChanges = new User(this);
        this.finishTaskDontShowAgainCheckbox = checkbox;
        UserService.editUser(userBeforeChanges, this);
    }
    /**
     * Methode to get the permanentDeleteDontShowAgainCheckbox boolean.
     * @return permanentDeleteDontShowAgainCheckbox boolean.
     */
    public boolean isPermanentDeleteDontShowAgainCheckbox(){return permanentDeleteDontShowAgainCheckbox;}

    /**
     * Methode to set the permanentDeleteDontShowAgainCheckbox boolean.
     * @param permanentDeleteDontShowAgainCheckbox boolean.
     */
    public void setPermanentDeleteDontShowAgainCheckbox(boolean permanentDeleteDontShowAgainCheckbox) {
        User userBeforeChanges = new User(this);
        this.permanentDeleteDontShowAgainCheckbox = permanentDeleteDontShowAgainCheckbox;
        UserService.editUser(userBeforeChanges, this);
    }
    /**
     * Methode to get the theme boolean.
     * @return theme string.
     */
    public String getTheme() {
        return theme;
    }
    /**
     * Methode to set the theme.
     * @param theme string.
     */
    public void setTheme(String theme) {
        User userBeforeChanges = new User(this);
        this.theme = theme;

        UserService.editUser(userBeforeChanges, this);
    }
    /**
     * Methode to get the isRememberMe boolean.
     * @return isRememberMe boolean.
     */
    public boolean isRememberMe() {
        return rememberMe;
    }
    /**
     * Methode to set the RememberMe boolean.
     * @param rememberMe boolean.
     */
    public void setRememberMe(boolean rememberMe) {
        User userBeforeChanges = new User(this);
        this.rememberMe = rememberMe;

        UserService.editUser(userBeforeChanges, this);
    }

    /**
     * A equals method which compares a objects content with this user-object.
     * @param o object.
     * @return a boolean depending on the objects being compared are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    /**
     * A method which returns hashCode of the user object.
     * @return a hash of the username.
     */
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}
