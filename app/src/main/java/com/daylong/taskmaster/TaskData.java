package com.daylong.taskmaster;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "tasks_to_do")
public class TaskData {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String priority;
    private String description;

    public TaskData(String name, String priority, String description) {
        this.name = name;
        this.priority = priority;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    @NonNull
    @Override
    public String toString() {
        return "TaskData{" +
                "id=" + id +
                ", taskName='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}



//
// For priority == boolean
//
//    @PrimaryKey(autoGenerate = true)
//    private long id;
//
//    private String taskName;
//    private boolean priority;
//    private String description;
//
//    public TaskData(String taskName, boolean priority, String description) {
//        this.taskName = taskName;
//        this.priority = priority;
//        this.description = description;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public String getTaskName() {
//        return taskName;
//    }
//
//    public boolean getPriority() {
//        return priority;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    @Override
//    public String toString() {
//        return "TaskData{" +
//                "id=" + id +
//                ", taskName='" + taskName + '\'' +
//                ", description='" + description + '\'' +
//                ", priority='" + priority + '\'' +
//                '}';
//    }


