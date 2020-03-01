package com.daylong.taskmaster;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;
import java.util.List;


// Credit: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-3-dao-roomdatabase
@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks_to_do ORDER BY id")
    List<TaskData> getAllFromTaskList();

    @Query("SELECT * FROM tasks_to_do WHERE state = :state")
    List<TaskData> getHIGHPriorityTasks(String state);

    @Query("SELECT * FROM tasks_to_do WHERE id = :id")
    TaskData getSpecificViaID(long id);

    @Query("SELECT * FROM tasks_to_do WHERE taskname = :taskName")
    TaskData getSpecificViaTaskName(String taskName);

    @Query("SELECT COUNT(id) FROM tasks_to_do")
    int getCountOfTaskList();

    @Insert
    void save(TaskData task);

    @Update
    void update(TaskData task);

    @Delete
    void delete(TaskData task);

    @Query("DELETE FROM tasks_to_do")
    void deleteAllTasks();
}
