package ntnu.idatt1002;

import java.io.Serializable;
import java.time.Clock;
import java.util.Objects;

/**
 * Class representing a notification
 */
public class Notification implements Serializable {
    private String title;
    private String username;
    private String description;
    private boolean checked;
    private boolean active;
    private String dateActive;
    private String dateDue;

    /**
     * A constructor for the class Notification
     * @param title
     * @param username
     * @param description
     * @param dueClock
     */
    public Notification(String title, String username, String description, Clock dueClock){
        this.title = title;
        this.username = username;
        this.description = description;
        this.checked = false;
        this.dateActive = Clock.systemDefaultZone().instant().toString();
        this.dateDue = dueClock.instant().toString();
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
    public String getDateActive() { return dateActive; }

    /**
     * A method to get the due date
     * @return
     */
    public String getDateDue() { return dateDue; }
    public boolean getActive() { return active; }

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
     * A method to set the due date
     * @param day
     * @param month
     * @param year
     * @param hour
     * @param minute
     */
    public void setDateDue(String day, String month, String year, String hour, String minute) {
        dateDue = year + "-" + month + "-" + day + "T" + hour + ":" + minute;
    }
    public void setActive(boolean active){
        this.active = active;
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
