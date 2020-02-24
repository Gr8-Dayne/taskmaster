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
        //
        // Update Task Detail Title with Task being looked at
        //
        //

        Intent intent = getIntent();

        String text = intent.getStringExtra(AllTasks.NAME_REPLACE);
//        int numberID = intent.getIntExtra(AllTasks.ID_REPLACE, 0);

        TextView textView1 = findViewById(R.id.taskDetailTitle);
//        TextView textView2 = (TextView) findViewById(R.id.chronologicalTaskID);

        textView1.setText(text);
//        textView2.setText("" + numberID);

//        String taskToView = getIntent().getStringExtra("taskDetailTitle");
//        TextView taskTitleTextView = findViewById(R.id.taskDetailTitle);
//        taskTitleTextView.setText(taskToView);
        //
        //
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
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = getIntent();
//
//        String text = intent.getStringExtra(AllTasks.EXTRA_TEXT);
//        int number = intent.getIntExtra(MainActivity.EXTRA_NUMBER, 0);
//
//        TextView textView1 = findViewById(R.id.taskDetailTitle);
//        TextView textView2 = (TextView) findViewById(R.id.textview2);
//
//        textView1.setText(text);
//        textView2.setText("" + number);
//    }
}
