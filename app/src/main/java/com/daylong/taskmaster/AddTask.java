package com.daylong.taskmaster;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;
import androidx.room.Room;
import java.util.List;


@Entity
public class AddTask extends AppCompatActivity {

    List<TaskData> listOfTasks;
    TaskDatabase theTaskening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        theTaskening = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "outstanding_tasks").allowMainThreadQueries().build();

        this.listOfTasks = theTaskening.taskDao().getAllFromTaskDataList();
        for(TaskData task : listOfTasks){
            Log.i("daylongMainActivity", task.getTaskName() + task.getState() + task.getDescription());
        }

        Button add_Task_Submit = findViewById(R.id.createTask);
        add_Task_Submit.setOnClickListener(e -> {

            // Gets new Task Name
            EditText inputtedTaskName = findViewById(R.id.newTaskTitleInput);
            String newTaskNameString = inputtedTaskName.getText().toString();

            // Gets new Task Priority
            EditText inputtedTaskPriority = findViewById(R.id.newTaskPriority);
            String newTaskPriorityString = inputtedTaskPriority.getText().toString();

            // Gets new Task Description
            EditText inputtedTaskDescription = findViewById(R.id.newTaskDescriptionInput);
            String newTaskDescriptionString = inputtedTaskDescription.getText().toString();

            // Creates new Task, then it's added to the master list
            TaskData newTask = new TaskData(newTaskNameString, newTaskPriorityString, newTaskDescriptionString);
            this.listOfTasks.add(0, newTask);
            theTaskening.taskDao().save(newTask);

            Log.i("daylongAddTask", "Look at this!\n" + this.listOfTasks.size());

            RecyclerView recyclerView = findViewById(R.id.my_recycler_view);

            recyclerView.getAdapter().notifyItemInserted(0);

            recyclerView.getLayoutManager().scrollToPosition(0);



            TextView item = AddTask.this.findViewById(R.id.submittedTextShow);
            item.setText("TASK CREATED AND SAVED");
        });
    }
}


