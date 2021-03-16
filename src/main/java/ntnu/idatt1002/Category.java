package ntnu.idatt1002;

import java.util.ArrayList;
import java.util.Objects;

public class Category {
    private String name;
    private ArrayList<Task> taskList;

    public Category(String name) {
        this.name = name;

    }public String getName() {
        return name;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public void addTaskList(ArrayList<Task> taskList) {
        for (Task task: taskList) {
            this.taskList.add(task);
        }
    }

    public void removeTask(Task task) {
        taskList.remove(taskList.indexOf(task));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name) && Objects.equals(taskList, category.taskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, taskList);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", taskList=" + taskList +
                '}';
    }
}
