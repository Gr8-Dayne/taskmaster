package com.daylong.taskmaster;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


// ViewAdapter has the job of telling the RecycleView what to display at each row (ForEach?)
// RecycleView asks for things from ViewAdapter
// ViewAdapter needs to know: the contents/data at each index AND total length

// Credit: https://codingwithmitch.com/
// Credit: https://guides.codepath.com/android/using-the-recyclerview
// Credit: https://www.journaldev.com/10024/android-recyclerview-android-cardview-example-tutorial
public class MainActivity extends AppCompatActivity {

    static View.OnClickListener myHomePageOnClickListener;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView recyclerView;
    private static ArrayList<TaskData> data;
    private static ArrayList<Integer> removedItems;

    //
    // Have it so only the important Tasks display on Main Page
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myHomePageOnClickListener = new MyOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<TaskData>();
        for (int i = 0; i < HardCodedTasks.taskNameArray.length; i++) {
            data.add(new TaskData(HardCodedTasks.taskNameArray[i], HardCodedTasks.descriptionArray[i], HardCodedTasks.stateArray[i], HardCodedTasks.id[i]));
        }

        removedItems = new ArrayList<Integer>();
        adapter = new MyTaskRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);



        // Redirect to Task Detail
//        Button buttonToTaskDetail = findViewById(R.id.activity_task_detail);
//
//        buttonToTaskDetail.setOnClickListener(new View.OnClickListener() {
//
//            // Display Task Title on Task Detail Activity
//            @Override
//            public void onClick(View view) {
//
//                Intent goToDetailPage = new Intent (MainActivity.this, TaskDetail.class);
//                MainActivity.this.startActivity(goToDetailPage);
//
//                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                SharedPreferences.Editor editorIBarelyKnowHer = p.edit();
//                editorIBarelyKnowHer.putString("taskOne", "Task One Details");
//                editorIBarelyKnowHer.apply();
//            }
//        });



        // Redirect to AddTask
        Button buttonTaskAdd = findViewById(R.id.addTaskButton);
        buttonTaskAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent goToAddTask = new Intent (MainActivity.this, AddTask.class);
                MainActivity.this.startActivity(goToAddTask);
            }
        });

        // Redirect to AllTasks
        Button buttonViewAllTasks = findViewById(R.id.viewAllTasks);
        buttonViewAllTasks.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent goToAddTask = new Intent (MainActivity.this, AllTasks.class);
                MainActivity.this.startActivity(goToAddTask);
            }
        });

        // Redirect to Settings
        Button buttonToSettings = findViewById(R.id.toSettingsButton);
        buttonToSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent goToSettings = new Intent (MainActivity.this, Settings.class);
                MainActivity.this.startActivity(goToSettings);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView usernameMainTextView = findViewById(R.id.addTaskH1);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String customUsername = sharedPreferences.getString("username", "default");
        if (customUsername != null) {
            usernameMainTextView.setText(customUsername + "'s Tasks");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }



        //
        //
        //
        //
        //
        @Override
        public void onClick(View v) {

            //
            //
            // I need to ensure that TaskDetail gives me the information about the Task I click on
            // How can I transfer a Task Data Instance between classes?
            //
            //

//            TextView taskTitleInDepth = findViewById(R.id.taskDetailTitle);
//            TextView taskDescriptionInDepth = findViewById(R.id.taskDetailDescription);
//            TextView taskIDInDepth = findViewById(R.id.chronologicalTaskID);
//
//            int selectedItemPosition = recyclerView.getChildPosition(v);
//
//            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);
//
//            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
//
//            String selectedName = (String) textViewName.getText();
//
//            int selectedItemId = -1;
//
//            for (int i = 0; i < HardCodedTasks.nameArray.length; i++) {
//                if (selectedName.equals(HardCodedTasks.nameArray[i])) {
//                    selectedItemId = HardCodedTasks.id[i];
//                }
//            }
//
//            removedItems.add(selectedItemId);
//            data.remove(selectedItemPosition);
//            adapter.notifyItemRemoved(selectedItemPosition);
//
//            if (customUsername != null) {
//                taskIDInDepth.setText("Task Number: " + );
//            }

        }
        //
        //
        //
        //
        //



        private void removeItem(View v) {

            int selectedItemPosition = recyclerView.getChildPosition(v);

            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForPosition(selectedItemPosition);

            TextView textViewName = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);

            String selectedName = (String) textViewName.getText();

            int selectedItemId = -1;

            for (int i = 0; i < HardCodedTasks.taskNameArray.length; i++) {
                if (selectedName.equals(HardCodedTasks.taskNameArray[i])) {
                    selectedItemId = HardCodedTasks.id[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }
    }

    private void addRemovedItemToList() {

        int addItemAtListPosition = 3;

        data.add(addItemAtListPosition, new TaskData(HardCodedTasks.taskNameArray[removedItems.get(0)], HardCodedTasks.descriptionArray[removedItems.get(0)], HardCodedTasks.descriptionArray[removedItems.get(0)], HardCodedTasks.id[removedItems.get(0)]));

        adapter.notifyItemInserted(addItemAtListPosition);

        removedItems.remove(0);
    }
}


