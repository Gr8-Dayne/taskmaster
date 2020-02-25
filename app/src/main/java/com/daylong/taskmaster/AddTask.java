package com.daylong.taskmaster;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Credit: Mattäus helped me with the following code
        Button add_Task_Submit = findViewById(R.id.createTask);
        add_Task_Submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View e) {
                TextView item = AddTask.this.findViewById(R.id.submittedTextShow);
                item.setText("TASK CREATED");
            }
        });
    }
}


