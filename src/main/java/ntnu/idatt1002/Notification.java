package ntnu.idatt1002;

import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Placeholder class to get DAO to work
 * @author Markus
 */
public class Notification implements Serializable {
    private String title;
    private String username;
    private String description;
    private boolean checked;
    private String dateActive;
    private String dateDue;

    public Notification(String title, String username, String description, Clock dueClock){
        this.title = title;
        this.username = username;
        this.description = description;
        this.checked = false;
        this.dateActive = Clock.systemDefaultZone().instant().toString();
        this.dateDue = dueClock.instant().toString();
    }

    //GET
    public String getTitle() {
        return title;
    }
    public String getUsername() {
        return username;
    }
    public String getDescription() {
        return description;
    }
    public Boolean getChecked() { return checked; }
    public String getDateActive() { return dateActive; }
    public String getDateDue() { return dateDue; }

    //SET
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setChecked(Boolean value) { this.checked = value; }
    public void setDateDue(String day, String month, String year, String hour, String minute) {
        dateDue = year + "-" + month + "-" + day + "T" + hour + ":" + minute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return title.equals(that.title) && username.equals(that.username) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, username, description);
    }
}
