package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);


        // Update Task Detail Title with Task being looked at
//        String taskToView = getIntent().getStringExtra("taskDetailTitle");
//        TextView taskTitleTextView = findViewById(R.id.taskDetailTitle);
//        taskTitleTextView.setText(taskToView);


        // Redirect to Main
        Button buttonToMain = findViewById(R.id.returnToMain);
        buttonToMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            Intent goToMain = new Intent (TaskDetail.this, MainActivity.class);
            TaskDetail.this.startActivity(goToMain);
            }
        });


        // Credit: https://developer.android.com/training/data-storage/shared-preferences
//        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
//        int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue);


        // Modify Task Detail Title
        TextView taskTitleTextView = findViewById(R.id.taskDetailTitle);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String customTaskTitle = sharedPreferences.getString("taskOne", "Task Details");

        if (customTaskTitle != null) {
            taskTitleTextView.setText(customTaskTitle);
        }
    }
}
