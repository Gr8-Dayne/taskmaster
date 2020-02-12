package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Redirect to Task Detail 1
        Button buttonToTaskDetailOne = findViewById(R.id.taskOne);
        buttonToTaskDetailOne.setOnClickListener(new View.OnClickListener() {

            // Display Task Title on Task Detail Activity
            @Override
            public void onClick(View view) {

                Intent i = new Intent (MainActivity.this, TaskDetail.class);
                MainActivity.this.startActivity(i);

//                EditText taskOneTitleEditText = findViewById(R.id.taskDetailTitle);
//                i.putExtra("taskOne", "Task One Title");
//                startActivity(i);

                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editorIBarelyKnowHer = p.edit();
                editorIBarelyKnowHer.putString("taskOne", "Task One Details");
                editorIBarelyKnowHer.apply();
            }
        });

        // Redirect to Task Detail 2
        final Button buttonToTaskDetailTwo = findViewById(R.id.taskTwo);
        buttonToTaskDetailTwo.setOnClickListener(new View.OnClickListener() {

            // Display Task Title on Task Detail Activity
            @Override
            public void onClick(View view) {

                Intent goToTaskDetail = new Intent (MainActivity.this, TaskDetail.class);
                MainActivity.this.startActivity(goToTaskDetail);

                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editorIBarelyKnowHer = p.edit();
                editorIBarelyKnowHer.putString("taskTwo", "Task Two Details");
                editorIBarelyKnowHer.apply();


                // Credit: https://developer.android.com/training/data-storage/shared-preferences
//                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPref.edit();
//                editor.putInt(getString(R.string.taskTwo), buttonToTaskDetailTwo.getText());
//                editor.commit();


            }
        });

        // Redirect to Task Detail 3
        Button buttonToTaskDetailThree = findViewById(R.id.taskThree);
        buttonToTaskDetailThree.setOnClickListener(new View.OnClickListener() {

            // Display Task Title on Task Detail Activity
            @Override
            public void onClick(View view) {

                Intent goToTaskDetail = new Intent (MainActivity.this, TaskDetail.class);
                MainActivity.this.startActivity(goToTaskDetail);

                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editorIBarelyKnowHer = p.edit();
                editorIBarelyKnowHer.putString("taskThree", "Task Three Details");
                editorIBarelyKnowHer.apply();
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

    @Override
    protected void onResume() {
        super.onResume();

        // Credit: Class 27 Demo
        TextView usernameMainTextView = findViewById(R.id.addTaskH1);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String customUsername = sharedPreferences.getString("username", "default");

        if (customUsername != null) {
            usernameMainTextView.setText(customUsername + "'s Tasks");
        }
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


