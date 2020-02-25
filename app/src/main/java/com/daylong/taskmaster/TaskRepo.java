package com.daylong.taskmaster;


import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;


public class TaskRepo {

    private TaskDao taskDao;
    private LiveData<List<TaskData>> allTaskData;

    // Credit: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-4-repository
    // Credit: https://www.youtube.com/watch?v=HhmA9S53XV8&feature=youtu.be
    public TaskRepo(Application application) {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTaskData = taskDao.getAllFromTaskDataList();
    }

    // Getters for Async Tasks
    public void save(TaskData task) {
        new InsertTaskDataAsyncTask(taskDao).execute(task);
    }

    public void update(TaskData task) {
        new UpdateTaskDataAsyncTask(taskDao).execute(task);
    }

    public void delete(TaskData task) {
        new DeleteTaskDataAsyncTask(taskDao).execute(task);
    }

    public void deleteAllTasks() {
        new DeleteAllTaskDataAsyncTask(taskDao).execute();
    }

    public LiveData<List<TaskData>> getAllTaskData() {
        return allTaskData;
    }


    // Asynchronous Tasks
    private static class InsertTaskDataAsyncTask extends AsyncTask<TaskData, Void, Void> {

        private TaskDao taskDao;

        private InsertTaskDataAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskData... task) {
            taskDao.save(task[0]);
            return null;
        }
    }

    private static class UpdateTaskDataAsyncTask extends AsyncTask<TaskData, Void, Void> {
        private TaskDao taskDao;
        private UpdateTaskDataAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(TaskData... tasks) {
            taskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskDataAsyncTask extends AsyncTask<TaskData, Void, Void> {
        private TaskDao taskDao;
        private DeleteTaskDataAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(TaskData... tasks) {
            taskDao.delete(tasks[0]);
            return null;
        }
    }

    private static class DeleteAllTaskDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;
        private DeleteAllTaskDataAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllTasks();
            return null;
        }
    }
}


