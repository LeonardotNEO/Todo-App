package ntnu.idatt1002;

import java.io.Serializable;
import java.util.Objects;

/**
 * The class Task represents a task.
 * This class provides access methods for all fields contained within.
 * The class also provides hashCode and a equals method as well as a toString method.
 */
public class Task implements Serializable {
    private String name;
    private String userName;
    private String description = "";
    private String deadline;
    private int priority;
    private String startDate;
    private String category;

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
    public Task(String name, String userName, String description, String deadline, int priority, String startDate, String category) {
        this.name = name;
        this.userName = userName;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.startDate = startDate;
        this.category = category;
    }

    /**
     * A constructor for the class Task. Use for the times when there is no deadline or start date for the task
     * @param name
     * @param userName
     * @param description
     * @param priority
     * @param category
     */
    public Task(String name, String userName, String description, int priority, String category) {
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
    public String getDeadline() {return deadline;}

    /**
     * A method to get the field priority
     * @return the priority of the task
     */
    public int getPriority() {return priority;}

    /**
     * A method to get the field start date
     * @return the startDate of the task
     */
    public String getStartDate() {return startDate;}

    /**A method to get the field category
     *
     * @return the category of the task
     */
    public String getCategory() {return category;}

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
    public void setDeadline(String deadline) {this.deadline = deadline;}

    /**
     * A method to set a new priority
     * @param priority
     */
    public void setPriority(byte priority) {this.priority = priority;}

    /**
     * A method to set a new start date.
     * @param startDate
     */
    public void setStartDate(String startDate) {this.startDate = startDate;}

    /**
     * A method to set a new category.
     * @param category
     */
    public void setCategory(String category) {this.category = category;}

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

    /**
     * A method to get a list of all fields in the class as a String
     * @return a String of all fields in the class
     */
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
}
