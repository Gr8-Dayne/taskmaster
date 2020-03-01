package com.daylong.taskmaster;

import java.util.ArrayList;

public class TaskList {

    String name;
    ArrayList<TaskData> tasksToComplete;

    public TaskList(String name, ArrayList<TaskData> tasksToComplete) {

        this.name = name;
        this.tasksToComplete = tasksToComplete;
    }
}
