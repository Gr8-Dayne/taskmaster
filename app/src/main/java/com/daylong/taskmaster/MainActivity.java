package com.daylong.taskmaster;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


// Credit: https://stackoverflow.com/questions/33897978/android-convert-edittext-to-string
// Credit: https://www.youtube.com/watch?v=JLwW5HivZg4
// Credit: https://www.youtube.com/watch?v=reSPN7mgshI&feature=youtu.be
// Credit: https://proandroiddev.com/a-guide-to-recyclerview-selection-3ed9f2381504
// Credit: https://guides.codepath.com/android/using-the-recyclerview
// Credit: https://www.journaldev.com/10024/android-recyclerview-android-cardview-example-tutorial
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //
        // Recycler View
        //
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TaskAdapter adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);
        //
        // Recycler View
        //



        // Credit: https://developer.android.com/reference/android/arch/lifecycle/ViewModelProvider
        AllTasks allTasks = ViewModelProviders.of(this).get(AllTasks.class);
        allTasks.getAllTasks().observe(this, new Observer<List<TaskData>>() {
            @Override
            public void onChanged(@Nullable List<TaskData> tasks) {
                adapter.setDataSet(tasks);
            }
        });

        // Redirect to AddTask
        Button buttonTaskAdd = findViewById(R.id.addTaskButton);
        buttonTaskAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent goToAddTask = new Intent (MainActivity.this, AddTask.class);
                MainActivity.this.startActivity(goToAddTask);
            }
        });

        // Redirect to AllTasks
        Button buttonViewAllTasks = findViewById(R.id.viewAllTasks);
        buttonViewAllTasks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent goToAddTask = new Intent (MainActivity.this, AllTasks.class);
                MainActivity.this.startActivity(goToAddTask);
            }
        });

        // Redirect to Settings
        Button buttonToSettings = findViewById(R.id.toSettingsButton);
        buttonToSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent goToSettings = new Intent (MainActivity.this, Settings.class);
                MainActivity.this.startActivity(goToSettings);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        TextView usernameMainTextView = findViewById(R.id.addTaskH1);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String customUsername = sharedPreferences.getString("username", "default");
        usernameMainTextView.setText(customUsername + "'s Tasks");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


