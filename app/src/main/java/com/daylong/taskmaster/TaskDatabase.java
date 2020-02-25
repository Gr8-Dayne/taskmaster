package com.daylong.taskmaster;


import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {TaskData.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
    private static TaskDatabase instance;

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class, "tasks_to_do").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase x) {
            super.onCreate(x);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private TaskDao taskDao;
        private PopulateDbAsyncTask(TaskDatabase x) {
            taskDao = x.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.save(new TaskData("Laundry", "Dire", "Wash and Dry dirty clothes."));
            taskDao.save(new TaskData("Oil Change", "Apocalyptic", "Somehow manage to crawl under your lowered car and have hot car juices bleed all over you. Then give up and go drinking."));
            taskDao.save(new TaskData("Clean Rifles", "In Progress", "Just whenever you feel like it, it's not like they can get so dirty they won't shoot........"));
            return null;
        }
    }
}