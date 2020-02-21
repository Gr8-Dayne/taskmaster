package com.daylong.taskmaster;


public class TaskData {

    private String taskName;
    private String description;
    private String state;
    private int id;

    public TaskData(String taskName, String version, String state, int id) {
        this.taskName = taskName;
        this.description = version;
        this.state = state;
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public int getId() {
        return id;
    }
}
