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
    private String description;
    private int priority;
    private long startDate;
    private long deadline;
    private String project;
    private String category;
    private String color;
    private String location;
    private boolean notification1Hour;
    private boolean notification24Hours;
    private boolean notification7Days;
    private ArrayList<String> tags;
    private boolean isRepeatable = false;
    private Long timeRepeat;
    private ArrayList<String> filePaths;

    /**
     * The constructor for this class.
     * This constructor is empty, to prevent one from initialize this class without the use of builder.
     */
    private Task() {}

    /**
     * A method to get the field name.
     *
     * @return the name of the task.
     */
    public String getName() {return name;}

    /**
     * A method to get the field user name.
     *
     * @return the userName of the user.
     */
    public String getUserName() {return userName;}

    /**
     * A method to get the field description.
     *
     * @return a description of the task.
     */
    public String getDescription() {return description;}

    /**
     * A method to get the field deadline.
     *
     * @return the deadline for the task.
     */
    public long getDeadline() {return deadline;}

    /**
     * A method to get the field priority.
     *
     * @return the priority of the task.
     */
    public int getPriority() {return priority;}

    /**
     * A method to get the field start date.
     *
     * @return the startDate of the task.
     */
    public long getStartDate() {return startDate;}


    /**
     * A method to get the field category.
     *
     * @return
     */
    public String getProject() {
        return project;
    }

    /**A method to get the field category
     *
     * @return the category of the task.
     */
    public String getCategory() {return category;}

    /**
     * A method to get the field color.
     *
     * @return the color of the task.
     */
    public String getColor() {return color;}

    /**
     * A method to get the field location.
     *
     * @return the location field stored in the task.
     */
    public String getLocation() {return location;}

    /**
     * A method to get the field id
     *
     * @return the id field stored in the task.
     */
    public long getId() {return id;}

    /**
     * A method to get the field tags.
     *
     * @return the tags field stored in the task.
     */
    public ArrayList<String> getTags() {return tags;}

    /**
     * A method to get the field isRepeatable.
     *
     * @return the value of the isRepeatable field stored in the task.
     */
    public boolean isRepeatable() { return isRepeatable; }

    /**
     * A method to get the field getTimeRepeat.
     *
     * @return the getTimeRepeat field stored in the task.
     */
    public long getTimeRepeat() {return timeRepeat;}

    /**
     * A method to set a new id for the task.
     *
     * @param id the new id for the task.
     */
    public void setId(long id) {this.id = id;}

    /**
     * A method to get the list of file paths in the task.
     *
     * @return the list of file paths contained in the task.
     */
    public ArrayList<String> getFilePaths() {return filePaths;}

    /**
     * A method to set a new name for the task.
     *
     * @param name the new name for the task.
     */
    public void setName(String name) {this.name = name;}

    /**
     * A method to set a new description.
     *
     * @param description the new description for the task.
     */
    public void setDescription(String description) {this.description = description; }

    /**
     * A method to set a new deadline.
     *
     * @param deadline the new deadline for the task.
     */
    public void setDeadline(long deadline) {this.deadline = deadline;}

    /**
     * A method to set a new priority.
     *
     * @param priority the new priority for the task.
     */
    public void setPriority(byte priority) {this.priority = priority;}

    /**
     * A method to set a new start date.
     *
     * @param startDate the new startDate for the task.
     */
    public void setStartDate(long startDate) {this.startDate = startDate;}

    public void setProject(String project) {this.project = project;}

    /**
     * A method to set a new category.
     *
     * @param category the new category for the task.
     */
    public void setCategory(String category) {this.category = category;}

    /**
     * A method to set a new color.
     *
     * @param color the new color for the task.
     */
    public void setColor(String color) {this.color = color;}

    /**
     * A method to set a new location.
     *
     * @param location the new location for the task.
     */
    public void setLocation(String location) {this.location = location;}

    /**
     * A method to set a new list of tags.
     *
     * @param tags the new list of tags for the task.
     */
    public void setTags(ArrayList<String> tags) {this.tags = tags;}

    /**
     * A method to set a new username.
     *
     * @param username the new username for the task.
     */
    public void setUserName(String username) {this.userName = username;}

    /**
     * A method to set a new list of file paths.
     *
     * @param filePaths the new list of file paths.
     */
    public void setFilePaths(ArrayList<String> filePaths) {this.filePaths = filePaths;}

    /**
     * A method to get the value of the field notification1Hour.
     *
     * @return the value of the field notification1Hour.
     */
    public boolean isNotification1Hour() {return notification1Hour;}

    /**
     * A method to change the value of the notification1Hour field.
     *
     * @param notification1Hour the new value for the notification1Hour field.
     */
    public void setNotification1Hour(boolean notification1Hour) {this.notification1Hour = notification1Hour;}

    /**
     * A method to get the value of the field notification24Hours.
     *
     * @return the value of the field notification24Hours.
     */
    public boolean isNotification24Hours() {return notification24Hours;}

    /**
     * A method to change the value of the notification24Hours field.
     *
     * @param notification24Hours the new value of the notification24Hours field.
     */
    public void setNotification24Hours(boolean notification24Hours) {this.notification24Hours = notification24Hours;}

    /**
     * A method to get the value of the field notification7Days.
     *
     * @return the value of the field notification7Days.
     */
    public boolean isNotification7Days() {return notification7Days;}

    /**
     * A method to change the value of the notification7Days field.
     *
     * @param notification7Days the new value of the notification7Days field.
     */
    public void setNotification7Days(boolean notification7Days) {this.notification7Days = notification7Days;}

    /**
     * A method to change the value of the repeatable field.
     *
     * @param repeatable the new value of the repeatable field.
     */
    public void setRepeatable(boolean repeatable) {
        isRepeatable = repeatable;
    }

    /**
     * A method to set the amount of times to repeats.
     *
     * @param timeRepeat the new amount of repeat times.
     */
    public void setTimeRepeat(long timeRepeat) {
        this.timeRepeat = timeRepeat;
    }

    /**
     * Checks to see if all fields in the object up for comparison is equal to the fields in Task.
     *
     * @param o the object which we want to compare to this Task object.
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
     * A method to get hashCode for the class.
     *
     * @return a unique int for the class.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, userName, description, deadline, priority, startDate, category);
    }

    /**
     * A method to get a list of all fields in the class as a String.
     *
     * @return a String of all fields in the class.
     */
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
                ", filePaths=" + filePaths +
                '}';
    }

    /**
     * A method to generate a unique id for this task-object.
     *
     * @return the generated id.
     */
    public long generateId(){
        Random random = new Random();
        String hashCodeString = Long.toString(Math.abs(this.hashCode()));
        String randomString = Long.toString(Math.abs(random.nextInt(1000)));
        String finalString = hashCodeString + randomString;
        long finalLong = Long.parseLong(finalString);
        return finalLong;
    }

    /**
     * Builder class that is used to create a Task.
     */
    public static class TaskBuilder {
        private long id;
        private String title;
        private String userName;
        private String description;
        private int priority;
        private String project;
        private String category;
        private String color;
        private String location;
        private ArrayList<String> tags;
        private ArrayList<String> filePaths;

        //Dates
        private long startDate;
        private long deadline;

        // Repetable
        private boolean isRepeatable = false;
        private long timeRepeat;

        // Notifications
        private boolean notification1Hour;
        private boolean notification24Hours;
        private boolean notification7Days;

        /**
         * Constructor for the class TaskBuilder.
         * This constructors requires the bare minimum data required to create a task.
         *
         * @param userName Name of the user creating the task.
         * @param title Title of the task.
         */
        public TaskBuilder(String userName, String title) {
            this.userName = userName;
            this.title = title;
        }

        /**
         * Method to set the description.
         *
         * @param description The tasks description.
         * @return the TaskBuilder.
         */
        public TaskBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Method to set the priority.
         * The priority goes from 0-x.
         * 0 is the lowest and x is the highest.
         *
         * @param priority priority in int.
         * @return the TaskBuilder
         */
        public TaskBuilder priority(int priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Method to set the project
         * @return the TaskBuilder
         */
        public TaskBuilder project(String project) {
            this.project = project;
            return this;
        }

        /**
         * Method to set the category
         * @param category name of category
         * @return the TaskBuilder
         */
        public TaskBuilder category(String category) {
            this.category = category;
            return this;
        }

        /**
         * Method to set if the task is repeatable and how often it should repeat.
         * A repeatable task will create itself when the deadline runs out.
         *
         * @param isRepeatable true / false depending on if it should be repeatable.
         * @param timeRepeat of often it should repeat in ms.
         * @return the TaskBuilder.
         */
        public TaskBuilder repeatable(Boolean isRepeatable, long timeRepeat) {
            this.isRepeatable = isRepeatable;
            this.timeRepeat = timeRepeat;
            return this;
        }

        /**
         * Method to set the task deadline.
         * The deadline is ms since 1/1/1970 UTC +1.
         *
         * @param deadline deadline in ms since 1/1/1970 UTC +1.
         * @return the deadline.
         */
        public TaskBuilder deadline(long deadline) {
            this.deadline = deadline;
            return this;
        }

        /**
         * Method to set the task startDate.
         * The startDate is ms since 1/1/1970 UTC +1.
         *
         * @param startDate startDate in ms since 1/1/1970 UTC +1.
         * @return the TaskBuilder.
         */
        public TaskBuilder startDate(long startDate) {
            this.startDate = startDate;
            return this;
        }

        /**
         * Method to set the task color.
         * The color should be in a hex format e.g #ffffff (white).
         *
         * @param color hex color.
         * @return the TaskBuilder.
         */
        public TaskBuilder color(String color) {
            this.color = color;
            return this;
        }

        /**
         * Method to set the tags.
         * The list contains a list of string that represents a tag.
         *
         * @param tags Arraylist of string.
         * @return the TaskBuilder.
         */
        public TaskBuilder tags(ArrayList<String> tags) {
            this.tags = tags;
            return this;
        }

        /**
         * Method to set the file paths.
         * The list contains a list of string that represents a file path.
         *
         * @param filePaths Arraylist of string.
         * @return the TaskBuilder.
         */
        public TaskBuilder filePaths(ArrayList<String> filePaths) {
            this.filePaths = filePaths;
            return this;
        }

        /**
         * Method to set the location.
         *
         * @param location String representing the location.
         * @return the TaskBuilder.
         */
        public TaskBuilder location(String location) {
            this.location = location;
            return this;
        }

        /*
        * These 3 methods can not be put into the product. It should be written into an loop that checks x minite etc.
        * this could also be used by the repeatable tasks
        */
        public TaskBuilder notification7Days() {
            this.notification7Days = true;
            NotificationService.newNotification(this.title, "This task is due in 7 days", LocalDateTime.ofInstant(Instant.ofEpochMilli(this.deadline), TimeZone.getDefault().toZoneId()).minusDays(7));
            return this;
        }
        public TaskBuilder notification24Hours() {
            this.notification24Hours = true;
            NotificationService.newNotification(this.title, "This task is due in 24 hours", LocalDateTime.ofInstant(Instant.ofEpochMilli(this.deadline), TimeZone.getDefault().toZoneId()).minusHours(24));
            return this;
        }
        public TaskBuilder notification1Hour() {
            this.notification1Hour = true;
            NotificationService.newNotification(this.title, "This task is due in 1 hour", LocalDateTime.ofInstant(Instant.ofEpochMilli(this.deadline), TimeZone.getDefault().toZoneId()).minusHours(1));
            return this;
        }

        /**
         * Method that creates a task and returns it.
         * If a value is not set it will default to. 0, false, null depending on the variable type.
         *
         * @return A task Object.
         */
        public Task build() {
            Task task = new Task();

            // Set values for task object
            task.name = this.title;
            task.userName = this.userName;
            task.description = this.description;
            task.priority = this.priority;
            task.startDate = this.startDate;
            task.project = this.project;
            task.category = this.category;
            task.color = this.color;
            task.deadline = this.deadline;
            task.location = this.location;
            task.notification1Hour = this.notification1Hour;
            task.notification7Days = this.notification7Days;
            task.notification24Hours = this.notification24Hours;
            task.tags = this.tags;
            task.filePaths = this.filePaths;
            task.isRepeatable = this.isRepeatable;
            task.timeRepeat = this.timeRepeat;

            // generate id
            task.id = task.generateId();

            return task;
        }
    }

}
