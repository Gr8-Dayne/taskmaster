package com.daylong.taskmaster;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class AllTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        //
        // Recycler View
        //
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        TaskAdapter adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);
        //
        // Recycler View
        //

        // Credit: https://developer.android.com/reference/android/arch/lifecycle/ViewModelProvider
        AndroidVM androidVM = ViewModelProviders.of(this).get(AndroidVM.class);
        androidVM.getAllTasks().observe(this, new Observer<List<TaskData>>() {
            @Override
            public void onChanged(@Nullable List<TaskData> tasks) {
                adapter.setDataSet(tasks);
            }
        });
    }
}


