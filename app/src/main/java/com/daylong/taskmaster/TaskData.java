package com.daylong.taskmaster;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "tasks_to_do")
public class TaskData {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String taskName;
    private String state;
    private String description;

    public TaskData(String taskName, String state, String description) {
        this.taskName = taskName;
        this.state = state;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
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
