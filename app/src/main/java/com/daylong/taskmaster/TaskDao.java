package com.daylong.taskmaster;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;


@Dao
public interface TaskDao {

    @Query("SELECT * FROM taskdata ORDER BY id DESC")
    List<TaskData> getAllFromTaskDataList();

    @Query("SELECT * FROM taskdata WHERE id = :id")
    TaskData getSpecific(long id);

    @Insert
    void save(TaskData item);

}
