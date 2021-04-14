package ntnu.idatt1002;

import ntnu.idatt1002.service.NotificationService;
import ntnu.idatt1002.utils.ColorUtil;
import ntnu.idatt1002.utils.DateUtils;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The class Task represents a task.
 * This class provides access methods for all fields contained within.
 * The class also provides hashCode and a equals method as well as a toString method.
 */
public class Task implements Serializable {
    private long id;
    private String name;
    private String userName;
    private String description = "";
    private int priority;
    private long startDate;
    private long deadline;
    private String category;
    private String color;
    private String location;
    private boolean notification1Hour;
    private boolean notification24Hours;
    private boolean notification7Days;
    private ArrayList<String> tags;
    private boolean isRepeatable = false;
    private Long timeRepeat;

    private Task() { /* Empty constructor for builder */}
    /**
     * A constructor for the class Task. Use when there is a deadline and start date for task.
     * @param name
     * @param userName
     * @param description
     * @param deadline
     * @param priority
     * @param startDate
     * @param category
     */
    public Task(String name, String userName, String description, long deadline, int priority, long startDate, String category, String color, String location, boolean notification1Hour, boolean notification24Hours, boolean notification7Days, ArrayList<String> tags, boolean Repeatable, Long timeRepeat) {
        this.name = name;
        this.userName = userName;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.startDate = startDate;
        this.category = category;
        this.color = ColorUtil.getCorrectColorFormat(color);
        this.location = location;
        this.notification1Hour = notification1Hour;
        this.notification24Hours = notification24Hours;
        this.notification7Days = notification7Days;
        if(notification1Hour){
            NotificationService.newNotification(this.name, "This task is due in 1 hour", LocalDateTime.ofInstant(Instant.ofEpochMilli(this.deadline), TimeZone.getDefault().toZoneId()).minusHours(1));
        }
        if(notification24Hours){
            NotificationService.newNotification(this.name, "This task is due in 24 hours", LocalDateTime.ofInstant(Instant.ofEpochMilli(this.deadline), TimeZone.getDefault().toZoneId()).minusHours(24));
        }
        if(notification7Days){
            NotificationService.newNotification(this.name, "This task is due in 7 days", LocalDateTime.ofInstant(Instant.ofEpochMilli(this.deadline), TimeZone.getDefault().toZoneId()).minusDays(7));
        }
        this.tags = tags;
        this.id = generateId();
        this.isRepeatable = isRepeatable;
        this.timeRepeat=timeRepeat;
    }

    /**
     * A constructor for the class Task. Use for the times when there is no deadline or start date for the task
     * @param name
     * @param userName
     * @param description
     * @param deadlineTime
     * @param priority
     * @param asMs
     * @param categoryMenuText
     * @param color
     * @param text
     * @param notification1HourSelected
     * @param notification24HoursSelected
     * @param selected
     * @param tagsList
     * @param repeatable
     * @param category
     */
    public Task(String name, String userName, String description, long deadlineTime, int priority, long asMs, String categoryMenuText, String color, String text, boolean notification1HourSelected, boolean notification24HoursSelected, boolean selected, ArrayList<String> tagsList, boolean repeatable, String category) {
        this.name = name;
        this.userName = userName;
        this.description = description;
        this.priority = priority;
        this.category = category;
    }

    /**
     * A method to get the field name
     * @return the name of the task
     */
    public String getName() {return name;}

    /**
     * A method to get the field user name
     * @return the userName of the user
     */
    public String getUserName() {return userName;}

    /**
     * A method to get the field description
     * @return a description of the task
     */
    public String getDescription() {return description;}

    /**
     * A method to get the field deadline
     * @return the deadline for the task
     */
    public long getDeadline() {return deadline;}

    /**
     * A method to get the field priority
     * @return the priority of the task
     */
    public int getPriority() {return priority;}

    /**
     * A method to get the field start date
     * @return the startDate of the task
     */
    public long getStartDate() {return startDate;}

    /**A method to get the field category
     *
     * @return the category of the task
     */
    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public String getLocation() {
        return location;
    }

    public long getId() {
        return id;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public boolean isRepeatable() { return isRepeatable; }

    public Long getTimeRepeat() {
        return timeRepeat;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * A method to set a new name for the task
     * @param name
     */
    public void setName(String name) {this.name = name;}

    /**
     * A method to set a new description
     * @param description
     */
    public void setDescription(String description) {this.description = description; }

    /**
     * A method to set a new deadline
     * @param deadline
     */
    public void setDeadline(long deadline) {this.deadline = deadline;}

    /**
     * A method to set a new priority
     * @param priority
     */
    public void setPriority(byte priority) {this.priority = priority;}

    /**
     * A method to set a new start date.
     * @param startDate
     */
    public void setStartDate(long startDate) {this.startDate = startDate;}

    /**
     * A method to set a new category.
     * @param category
     */
    public void setCategory(String category) {this.category = category;}

    public void setColor(String color) {
        this.color = color;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public boolean isNotification1Hour() {
        return notification1Hour;
    }

    public void setNotification1Hour(boolean notification1Hour) {
        this.notification1Hour = notification1Hour;
    }

    public boolean isNotification24Hours() {
        return notification24Hours;
    }

    public void setNotification24Hours(boolean notification24Hours) {
        this.notification24Hours = notification24Hours;
    }

    public boolean isNotification7Days() {
        return notification7Days;
    }

    public void setNotification7Days(boolean notification7Days) {
        this.notification7Days = notification7Days;
    }

    public void setRepeatable(boolean repeatable) {
        isRepeatable = repeatable;
    }

    public void setTimeRepeat(Long timeRepeat) {
        this.timeRepeat = timeRepeat;
    }

    /**
     * Checks to see if all fields in the object up for comparison is equal to the fields in Task.
     * @param o
     * @return whenever or not the object o is equal to the class Task.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return priority == task.priority &&
                Objects.equals(name, task.name) &&
                Objects.equals(userName, task.userName) &&
                Objects.equals(description, task.description) &&
                Objects.equals(deadline, task.deadline) &&
                Objects.equals(startDate, task.startDate) &&
                Objects.equals(category, task.category);
    }

    /**
     * A method to get hashCode for the class
     * @return a unique int for the class
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, userName, description, deadline, priority, startDate, category);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                ", category='" + category + '\'' +
                ", color='" + color + '\'' +
                ", location='" + location + '\'' +
                ", notification1Hour=" + notification1Hour +
                ", notification24Hours=" + notification24Hours +
                ", notification7Days=" + notification7Days +
                ", tags=" + tags +
                ", isRepeatable=" + isRepeatable +
                ", timeRepeat=" + timeRepeat +
                '}';
    }

    /**
     * A method to get a list of all fields in the class as a String
     * @return a String of all fields in the class
     */
    /*
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", description='" + description + '\'' +
                ", deadline='" + deadline + '\'' +
                ", priority=" + priority +
                ", startDate='" + startDate + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
    */

    public long generateId(){
        Random random = new Random();
        String hashCodeString = Long.toString(Math.abs(this.hashCode()));
        String randomString = Long.toString(Math.abs(random.nextInt(1000)));
        String finalString = hashCodeString + randomString;
        long finalLong = Long.parseLong(finalString);
        return finalLong;
    }

    public static class TaskBuilder {
        private long id;
        private String title;
        private String userName;
        private String description;
        private int priority;
        private String category;
        private String color;
        private String location;
        private ArrayList<String> tags;

        //Dates
        private long startDate;
        private long deadline;

        // Repetable
        private boolean isRepeatable = false;
        private Long timeRepeat;

        // Notifications
        private boolean notification1Hour;
        private boolean notification24Hours;
        private boolean notification7Days;

        public TaskBuilder(String userName, String title) {
            this.userName = userName;
            this.title = title;
        }

        public TaskBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public TaskBuilder category(String category) {
            this.category = category;
            return this;
        }

        public TaskBuilder repeatable(Boolean isRepeatable, Long timeRepeat) {
            this.isRepeatable = isRepeatable;
            this.timeRepeat = timeRepeat;
            return this;
        }

        public TaskBuilder deadline(long deadline) {
            this.deadline = deadline;
            return this;
        }

        public TaskBuilder startDate(long startDate) {
            this.startDate = startDate;
            return this;
        }

        public TaskBuilder color(String color) {
            this.color = color;
            return this;
        }

        public TaskBuilder tags(ArrayList<String> tags) {
            this.tags = tags;
            return this;
        }

        public Task build() {
            Task task = new Task();

            // Set values for task object
            task.name = this.title;
            task.userName = this.userName;
            task.description = this.description;
            task.priority = this.priority;
            task.startDate = this.startDate;
            task.category = this.category;
            task.color = this.color;
            task.location = this.location;
            task.notification1Hour = this.notification1Hour;
            task.notification7Days = this.notification7Days;
            task.notification24Hours = this.notification24Hours;
            task.tags = this.tags;
            task.isRepeatable = this.isRepeatable;
            task.timeRepeat = this.timeRepeat;

            // generate id
            task.id = task.generateId();

            return task;
        }
    }

}
