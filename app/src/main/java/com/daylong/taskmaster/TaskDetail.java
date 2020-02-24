package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
        TextView textView1 = findViewById(R.id.taskDetailTitle);
        textView1.setText(showTaskName);

        String showTaskStatus = showTaskDetailsFromTaskDetailPage.getStringExtra("taskState");
        TextView textView2 = findViewById(R.id.taskState);
        textView2.setText(showTaskStatus);

        String showTaskDescription = showTaskDetailsFromTaskDetailPage.getStringExtra("taskDescription");
        TextView textView3 = findViewById(R.id.taskDetailDescription);
        textView3.setText(showTaskDescription);

//        String numberID = showTaskDetailsFromTaskDetailPage.getStringExtra("taskID");
//        TextView textView4 = findViewById(R.id.chronologicalTaskID);
//        textView4.setText(numberID);
        //
        //
        //


        // Redirect to Main
        Button buttonToMain = findViewById(R.id.returnToMain);
        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent goToMain = new Intent (TaskDetail.this, MainActivity.class);
            TaskDetail.this.startActivity(goToMain);
            }
        });
    }
}


