package com.daylong.taskmaster;


import androidx.room.RoomDatabase;
import androidx.room.Database;


@Database(entities = {TaskData.class}, version = 6)
public abstract class TaskDatabase extends RoomDatabase{
    public abstract TaskDao taskDao();
}


