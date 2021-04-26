package ntnu.idatt1002.model;

import ntnu.idatt1002.utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class representing a notification
 */
public class Notification implements Serializable {
    private final int notifId;
    private String title;
    private String username;
    private String description;
    private boolean checked;
    private boolean active;
    private long dateActive;
    private long dateIssued;

    /**
     * A constructor for the class Notification
     * @param title
     * @param username
     * @param description
     */
    public Notification(String title, String username, String description, LocalDateTime issueDate){
        this.title = title;
        this.username = username;
        this.description = description;
        this.checked = false;
        this.dateActive = DateUtils.getAsMs(LocalDateTime.now());
        this.dateIssued = DateUtils.getAsMs(issueDate);
        this.notifId = this.hashCode();
    }

    public Notification(String title, String username, String description, LocalDateTime issueDate, int id){
        this.title = title;
        this.username = username;
        this.description = description;
        this.checked = false;
        this.dateActive = DateUtils.getAsMs(LocalDateTime.now());
        this.dateIssued = DateUtils.getAsMs(issueDate);
        this.notifId = id;
    }

    /**
     * A method to get the title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * A method to get the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * A method to get the description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * A method to get the checked value
     * @return checked value
     */
    public Boolean getChecked() { return checked; }

    /**
     * A method to get the activation date
     * @return
     */
    public long getDateActive() {
        return dateActive;
    }

    /**
     * A method to get the due date
     * @return
     */
    public long getDateIssued() {
        return dateIssued;
    }

    public boolean getActive() {
        return active;
    }

    /**
     * A method to set the title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * A method to set the username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * A method to set the description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A method to set the checked value
     * @param value
     */
    public void setChecked(Boolean value) { this.checked = value; }


    /**
     * Method to set due date
     * @param date
     */
    public void setDateIssued(long date) {
        dateIssued = date;
    }
    public void setActive(boolean active){
        this.active = active;
    }

    public int getNotifId() {
        return notifId;
    }

    /**
     * A equals method which compares a objects content with this notification-object
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return title.equals(that.title) && username.equals(that.username) && description.equals(that.description);
    }

    /**
     * A method which returns hashCode of the notification object
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, username, description);
    }
}
