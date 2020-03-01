package com.daylong.taskmaster;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.amazonaws.amplify.generated.graphql.CreateTodoMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import type.CreateTodoInput;


// Credit: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-7-add-note-activity
public class AddTask extends AppCompatActivity {

    private TaskDatabase dbTasks;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;
    private AWSAppSyncClient awsSyncer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        //
        //
        //
        awsSyncer = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        //
        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        //
        //
        //

        TextView addTaskCounter = findViewById(R.id.taskCount);

        try {
            addTaskCounter.setText("Total Tasks: " + dbTasks.taskDao().getCountOfTaskList());
        } catch (Exception e) {
            Toast.makeText(AddTask.this, "Total Task Count ERROR", Toast.LENGTH_SHORT).show();
        }

        editTextTitle = findViewById(R.id.newTaskTitle);
        editTextDescription = findViewById(R.id.newTaskDescription);
        editTextPriority = findViewById(R.id.newTaskState);

        Button normalTaskToDB = findViewById(R.id.createTask);
        normalTaskToDB.setOnClickListener(e -> {

            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();
            String priority = editTextPriority.getText().toString();

            if (title.trim().isEmpty() || description.trim().isEmpty() || priority.trim().isEmpty()) {
                Toast.makeText(AddTask.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;

            } else if (true) {

                try {
                    TaskData newTask = new TaskData(title, priority, description);

                    dbTasks.taskDao().save(newTask);
//                    addTaskToDynomo(newTask);

                    Toast.makeText(AddTask.this, "Task saved successfully", Toast.LENGTH_LONG).show();
                    finish();
                } catch (Exception d) {
                    Toast.makeText(AddTask.this, "Task not saved", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }

    // Add things to Amplify
    public void addTaskToDynomo(TaskData newTask){

        CreateTodoInput createTodoInput = CreateTodoInput.builder()
                .name(newTask.getName())
                .priority(newTask.getPriority())
                .description(newTask.getDescription())
                .build();

        awsSyncer.mutate(CreateTodoMutation.builder().input(createTodoInput).build())
                .enqueue(addTaskCallback);
    }

    private GraphQLCall.Callback<CreateTodoMutation.Data> addTaskCallback = new GraphQLCall.Callback<CreateTodoMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateTodoMutation.Data> response) {
            Log.i("daylongTheGreat", "-----ADD TASK CLICKED-----");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("daylongTheGreat", "_____ERROR_____ " + e.toString());
        }
    };

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
            Toast.makeText(AddTask.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.increase_priority) {
            Toast.makeText(AddTask.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.decrease_priority) {
            Toast.makeText(AddTask.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);
        }
        return(super.onOptionsItemSelected(item));
    }
}


