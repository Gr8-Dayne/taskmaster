package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        //
        // Update Task Detail Title with Task being looked at
        //
        Intent showTaskDetailsFromTaskDetailPage = getIntent();

        String showTaskName = showTaskDetailsFromTaskDetailPage.getStringExtra("taskName");
        TextView textView1 = findViewById(R.id.taskDetail_Title);
        textView1.setText(showTaskName);

        String showTaskStatus = showTaskDetailsFromTaskDetailPage.getStringExtra("taskState");
        TextView textView2 = findViewById(R.id.taskDetail_State);
        textView2.setText(showTaskStatus);

        String showTaskDescription = showTaskDetailsFromTaskDetailPage.getStringExtra("taskDescription");
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
        }
        return(super.onOptionsItemSelected(item));
    }
}


