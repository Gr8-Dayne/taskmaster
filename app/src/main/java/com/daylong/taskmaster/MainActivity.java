package com.daylong.taskmaster;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.amazonaws.amplify.generated.graphql.CreateTodoMutation;
import com.amazonaws.amplify.generated.graphql.ListTodosQuery;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;
import type.CreateTodoInput;


// Credit: https://stackoverflow.com/questions/33897978/android-convert-edittext-to-string
// Credit: https://www.youtube.com/watch?v=JLwW5HivZg4
// Credit: https://www.youtube.com/watch?v=reSPN7mgshI&feature=youtu.be
// Credit: https://proandroiddev.com/a-guide-to-recyclerview-selection-3ed9f2381504
// Credit: https://guides.codepath.com/android/using-the-recyclerview
// Credit: https://www.journaldev.com/10024/android-recyclerview-android-cardview-example-tutorial
public class MainActivity extends AppCompatActivity {

    TaskDatabase dbTasks;
    RecyclerView recyclerView;
    List<TaskData> dataSetMain = new ArrayList<>();
    private AWSAppSyncClient awsSyncer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        //
        awsSyncer = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        //
//        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks")
//                .allowMainThreadQueries()
//                .fallbackToDestructiveMigration()
//                .build();
        //

        try {
            this.dataSetMain = dbTasks.taskDao().getHIGHPriorityTasks("HIGH");
        } catch (Exception e) {
            Log.i("daylongTheGreat", String.valueOf(dataSetMain));
        }

        recyclerView = findViewById(R.id.my_Main_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSetMain, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        getTasksFromAmplify();

        for (TaskData item : dataSetMain) {
            Log.i("daylongTheGreat", item.getName() + " " + item.getPriority());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        // Username Display
        TextView usernameMainTextView = findViewById(R.id.addTaskH1);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String customUsername = sharedPreferences.getString("username", "default");
        usernameMainTextView.setText(customUsername + "'s Tasks");
        //

        //
        awsSyncer = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        //
        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        //

        this.dataSetMain = dbTasks.taskDao().getHIGHPriorityTasks("HIGH");
        recyclerView = findViewById(R.id.my_Main_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSetMain, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    //
    //
    //
    // Get things from Amplify
    public void getTasksFromAmplify(){
        awsSyncer.query(ListTodosQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK).enqueue(getTasksCallBack);
    }

    // Credit: https://frontrowviews.com/Home/Event/Play/5e1fa720eee6db204c80779e#
    private GraphQLCall.Callback<ListTodosQuery.Data> getTasksCallBack = new GraphQLCall.Callback<ListTodosQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListTodosQuery.Data> response) {
            Log.i("daylongTheGreat", response.data().listTodos().items().toString());

            if(dataSetMain.size() == 0 || response.data().listTodos().items().size() != dataSetMain.size()){

                dataSetMain.clear();

                for(ListTodosQuery.Item item : response.data().listTodos().items()){

                    TaskData a = new TaskData(item.name(), item.priority(), item.description());

                    dataSetMain.add(a);
                }

                Handler handlerForMainThread = new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message inputMessage){
                        RecyclerView recyclerView = findViewById(R.id.my_Main_recycler_view);
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                };
                handlerForMainThread.obtainMessage().sendToTarget();
            }
        }
        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("daylongTheGreat", e.toString());
        }
    };

    // Subscription
//    public void runShelfCreateMutation(String name){
//        CreateTaskListInput createShelfInput = CreateShelfInput.builder()
//                .name(name)
//                .build();
//        awsAppSyncClient.mutate(CreateShelfMutation.builder().input(createShelfInput).build())
//                .enqueue(addShelfCallback);
//
//    }
//
//    private GraphQLCall.Callback<CreateShelfMutation.Data> addShelfCallback = new GraphQLCall.Callback<CreateShelfMutation.Data>() {
//        @Override
//        public void onResponse(@Nonnull Response<CreateShelfMutation.Data> response) {
//            Log.i(TAG, "Added Shelf");
//        }
//
//        @Override
//        public void onFailure(@Nonnull ApolloException e) {
//            Log.e(TAG, e.toString());
//        }
//    };
    //
    //
    //



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

    // Allow nav_and_actions to be utilized
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_and_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.widget_to_main) {
            Intent goToMain = new Intent (this, MainActivity.class);
            this.startActivity(goToMain);
            return (true);

        } else if (itemId == R.id.widget_to_AddTask) {
            Intent goToAddTask = new Intent (this, AddTask.class);
            this.startActivity(goToAddTask);
            return (true);

        } else if (itemId == R.id.widget_to_AllTasks) {
            Intent goToAllTask = new Intent (this, AllTasks.class);
            this.startActivity(goToAllTask);
            return (true);

        } else if (itemId == R.id.widget_to_Settings) {
            Intent goToSettings = new Intent (this, Settings.class);
            this.startActivity(goToSettings);
            return (true);

        } else if (itemId == R.id.delete_this_task) {
            Toast.makeText(MainActivity.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.increase_priority) {
            Toast.makeText(MainActivity.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.decrease_priority) {
            Toast.makeText(MainActivity.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);
        }
        return(super.onOptionsItemSelected(item));
    }
}


