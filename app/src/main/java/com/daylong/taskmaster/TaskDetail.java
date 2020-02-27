package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.Objects;


public class TaskDetail extends AppCompatActivity {

    TaskDatabase dbTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().build();

        Intent showTaskID = getIntent();

        String showTaskName = showTaskID.getStringExtra("taskName");
        TextView textView1 = findViewById(R.id.taskDetail_Title);
        textView1.setText(showTaskName);

        TaskData taskDataViaTaskName = dbTasks.taskDao().getSpecificViaTaskName(showTaskName);
        Log.i("daylongTheGreat", String.valueOf(taskDataViaTaskName));

        String showTaskStatus = taskDataViaTaskName.getState();
        TextView textView2 = findViewById(R.id.taskDetail_State);
        textView2.setText("Priority: " + showTaskStatus);

        String showTaskDescription = taskDataViaTaskName.getDescription();
        TextView textView3 = findViewById(R.id.taskDetail_Description);
        textView3.setText(showTaskDescription);
    }

    // Allow nav_and_actions to be utilized
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_and_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.widget_to_main) {
            Intent goToAddMain = new Intent (this, MainActivity.class);
            this.startActivity(goToAddMain);
            return (true);

        } else if (itemId == R.id.widget_to_AddTask) {
            Intent goToAddTask = new Intent (this, AddTask.class);
            this.startActivity(goToAddTask);
            return (true);

        } else if (itemId == R.id.widget_to_AllTasks) {
            Intent goToAllTask = new Intent (this, AllTasks.class);
            this.startActivity(goToAllTask);
            return (true);

        } else if (itemId == R.id.widget_to_Settings) {
            Intent goToSettings = new Intent (this, Settings.class);
            this.startActivity(goToSettings);
            return (true);

        } else if (itemId == R.id.delete_this_task) {
            dbTasks.taskDao().deleteAllTasks();
            return (true);
        }
        return(super.onOptionsItemSelected(item));
    }
}


