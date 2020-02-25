package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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

        // Redirect to Main
        Button buttonToMain = findViewById(R.id.returnToMain_Button);
        buttonToMain.setOnClickListener(view -> {
        Intent goToMain = new Intent (TaskDetail.this, MainActivity.class);
        TaskDetail.this.startActivity(goToMain);
        });
    }
}


