package com.daylong.taskmaster;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class TaskData {

    @PrimaryKey(autoGenerate = true)
    long id;

    private String taskName;
    private String state;
    private String description;

    public TaskData(String taskName, String state, String description) {
        this.taskName = taskName;
        this.state = state;
        this.description = description;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "TaskData{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
