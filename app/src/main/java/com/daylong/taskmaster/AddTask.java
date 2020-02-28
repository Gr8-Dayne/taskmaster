package com.daylong.taskmaster;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;


// Credit: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-7-add-note-activity
public class AddTask extends AppCompatActivity {

    private AWSAppSyncClient awsSyncer;
    private TaskDatabase dbTasks;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //
        //
        //
        awsSyncer = AWSAppSyncClient.builder().context(getApplicationContext()).awsConfiguration(new AWSConfiguration(getApplicationContext())).build();
        //
        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().build();
        //
        //
        //

        TextView addTaskCounter = findViewById(R.id.taskCount);
        addTaskCounter.setText("Total Tasks: " + dbTasks.taskDao().getCountOfTaskList());

        editTextTitle = findViewById(R.id.newTaskTitle);
        editTextDescription = findViewById(R.id.newTaskDescription);
        editTextPriority = findViewById(R.id.newTaskState);

        Button normalTaskToDB = findViewById(R.id.createTask);
        normalTaskToDB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View e) {

                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                String priority = editTextPriority.getText().toString();

                if (title.trim().isEmpty() || description.trim().isEmpty() || priority.trim().isEmpty()) {
                    Toast.makeText(AddTask.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;

                } else if (true) {

                    TaskData newTask = new TaskData(title, priority, description);
                    dbTasks.taskDao().save(newTask);

                    Toast.makeText(AddTask.this, "Task saved successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AddTask.this, "Task not saved", Toast.LENGTH_LONG).show();
                }
            }
        });
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
        }
        return(super.onOptionsItemSelected(item));
    }
}


