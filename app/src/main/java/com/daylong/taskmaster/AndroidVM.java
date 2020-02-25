package com.daylong.taskmaster;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;


public class AndroidVM extends AndroidViewModel {

    private TaskRepo repository;
    private LiveData<List<TaskData>> allTasks;

    public AndroidVM(@NonNull Application application) {
        super(application);
        repository = new TaskRepo(application);
        allTasks = repository.getAllTaskData();
    }

    public void save(TaskData task) {
        repository.save(task);
    }

    public void update(TaskData task) {
        repository.update(task);
    }

    public void delete(TaskData task) {
        repository.delete(task);
    }

    public void deleteAllTaskData() {
        repository.deleteAllTasks();
    }

    public LiveData<List<TaskData>> getAllTasks() {
        return allTasks;
    }
}


