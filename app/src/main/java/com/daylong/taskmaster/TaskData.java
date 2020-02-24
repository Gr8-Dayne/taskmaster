package com.daylong.taskmaster;


public class TaskData {

    private String taskName;
    private String description;
    private String state;
    private Integer id;

    public TaskData(String taskName, String version, String state, Integer id) {
        this.taskName = taskName;
        this.description = version;
        this.state = state;
        this.id = id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setId(Integer id) {
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

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TaskData{" +
                "taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                ", id=" + id +
                '}';
    }
}
