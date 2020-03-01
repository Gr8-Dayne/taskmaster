package com.daylong.taskmaster;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AllTasks extends AppCompatActivity {

    TaskDatabase dbTasks;
    RecyclerView recyclerView;
    List<TaskData> dataSet = new ArrayList<>();
//    private AWSAppSyncClient awsSyncer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        //
//        awsSyncer = AWSAppSyncClient.builder().context(getApplicationContext()).awsConfiguration(new AWSConfiguration(getApplicationContext())).build();
        //
        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().build();
        //

//        this.dataSet = new ArrayList<>();

        this.dataSet = dbTasks.taskDao().getAllFromTaskList();

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSet, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(AllTasks.this));

        for(TaskData item : dataSet){
            Log.i("daylongTheGreat", item.getTaskName());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().build();
        this.dataSet = dbTasks.taskDao().getAllFromTaskList();
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSet, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(AllTasks.this));
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
            Intent goToMain = new Intent (this, MainActivity.class);
            this.startActivity(goToMain);
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
            Toast.makeText(AllTasks.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.increase_priority) {
            Toast.makeText(AllTasks.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.decrease_priority) {
            Toast.makeText(AllTasks.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);
        }
        return(super.onOptionsItemSelected(item));
    }
}


