package com.daylong.taskmaster;


import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {TaskData.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}

