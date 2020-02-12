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
        // Credit: 02/11/2020 class demo
        String usernameToView = getIntent().getStringExtra("newInput");
        TextView usernameTextView = findViewById(R.id.taskDetailTitle);
        usernameTextView.setText(usernameToView);
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
