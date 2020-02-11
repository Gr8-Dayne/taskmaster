package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button add_Task_Submit = findViewById(R.id.createTask);
        add_Task_Submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View e) {
                TextView item = AddTask.this.findViewById(R.id.submittedTextShow);
                item.setText("SUBMITTED!");
            }
        });
    }
}
