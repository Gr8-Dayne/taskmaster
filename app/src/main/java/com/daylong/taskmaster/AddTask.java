package com.daylong.taskmaster;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class AddTask extends AppCompatActivity {

    public static final String EXTRA_TITLE = "EXTRA_SAVE_TITLE";
    public static final String EXTRA_DESCRIPTION = "EXTRA_SAVE_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "EXTRA_SAVE_PRIORITY";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTitle = findViewById(R.id.newTaskTitle);
        editTextDescription = findViewById(R.id.newTaskDescription);
        editTextPriority = findViewById(R.id.newTaskState);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_trash);
        setTitle("No step on snek");

//        Button add_Task_Submit = findViewById(R.id.createTask);
//        add_Task_Submit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View e) {
//                TextView item = AddTask.this.findViewById(R.id.submittedTextShow);
//                item.setText("TASK CREATED");
//            }
//        });
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String priority = editTextPriority.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty() || priority.trim().isEmpty()) {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_task: saveTask();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}


