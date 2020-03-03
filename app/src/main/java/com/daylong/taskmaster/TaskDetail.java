package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.ListTodosQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
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


public class TaskDetail extends AppCompatActivity {

    TaskDatabase dbTasks;
    List<TaskData> dataSet = new ArrayList<>();
    private AWSAppSyncClient awsSyncer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

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

        Intent showTaskID = getIntent();

        String showTaskName = showTaskID.getStringExtra("taskName");
        TextView textView1 = findViewById(R.id.taskDetail_Title);
        textView1.setText(showTaskName);

        getTasksFromAmplify();

//        dataSet.get((int) dbTasks.taskDao().getSpecificViaTaskName(showTaskName).getId());

        TaskData taskDataViaTaskName = dbTasks.taskDao().getSpecificViaTaskName(showTaskName);
        Log.i("daylongTheGreat", String.valueOf(taskDataViaTaskName));

        String showTaskStatus = taskDataViaTaskName.getPriority();
        TextView textView2 = findViewById(R.id.taskDetail_State);
        textView2.setText("Priority: " + showTaskStatus);

        String showTaskDescription = taskDataViaTaskName.getDescription();
        TextView textView3 = findViewById(R.id.taskDetail_Description);
        textView3.setText(showTaskDescription);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("daylongTheGreat", "onResult: " + userStateDetails.getUserState());
                        if (userStateDetails.getUserState().toString().equals("SIGNED_OUT")) {
                            AWSMobileClient
                                    .getInstance()
                                    .showSignIn(TaskDetail.this, SignInUIOptions.builder().build(),
                                            new Callback<UserStateDetails>() {
                                                @Override
                                                public void onResult(UserStateDetails result) {
                                                    Log.d("daylongTheGreat", "onResult: " + result.getUserState());
                                                    switch (result.getUserState()){
                                                        case SIGNED_IN:
                                                            Log.i("INIT", "logged in!");
                                                            break;
                                                        case SIGNED_OUT:
                                                            Log.i("daylongTheGreat", "ERROR: User did not choose to sign-in");
                                                            break;
                                                        default:
                                                            AWSMobileClient.getInstance().signOut();
                                                            break;
                                                    }
                                                }
                                                @Override
                                                public void onError(Exception e) {
                                                }
                                            });
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        Log.e("daylongTheGreat", "_____ERROR_____ " + e.toString());
                    }
                }
        );
    }

    //
    public void getTasksFromAmplify(){
        awsSyncer.query(ListTodosQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK).enqueue(getTasksCallBack);
    }

    // Credit: https://frontrowviews.com/Home/Event/Play/5e1fa720eee6db204c80779e#
    private GraphQLCall.Callback<ListTodosQuery.Data> getTasksCallBack = new GraphQLCall.Callback<ListTodosQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListTodosQuery.Data> response) {
            Log.i("daylongTheGreat", response.data().listTodos().items().toString());
            if(dataSet.size() == 0 || response.data().listTodos().items().size() != dataSet.size()){
                dataSet.clear();
                for(ListTodosQuery.Item item : response.data().listTodos().items()){
                    TaskData a = new TaskData(item.name(), item.priority(), item.description());
                    dataSet.add(a);
                }
//                Handler handlerForMainThread = new Handler(Looper.getMainLooper()){
//                    @Override
//                    public void handleMessage(Message inputMessage){
//                        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
//                        recyclerView.getAdapter().notifyDataSetChanged();
//                    }
//                };
//                handlerForMainThread.obtainMessage().sendToTarget();
            }
        }
        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("daylongTheGreat", e.toString());
        }
    };
    //

    // Allow nav_and_actions to be utilized
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_and_actions, menu);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        Intent showTaskID = getIntent();
        String showTaskName = showTaskID.getStringExtra("taskName");
        TaskData taskDataViaTaskName = dbTasks.taskDao().getSpecificViaTaskName(showTaskName);

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

            dbTasks.taskDao().delete(taskDataViaTaskName);
            Toast.makeText(TaskDetail.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            finish();
            return (true);

        } else if (itemId == R.id.increase_priority) {

            TaskData taskWithUpdatedPriority = new TaskData(taskDataViaTaskName.getName(), "HIGH", taskDataViaTaskName.getDescription());
            taskWithUpdatedPriority.setId(taskDataViaTaskName.getId());
            dbTasks.taskDao().update(taskWithUpdatedPriority);
            Toast.makeText(TaskDetail.this, "Task Priority Increased", Toast.LENGTH_SHORT).show();
            finish();
            return (true);

        } else if (itemId == R.id.decrease_priority) {

            TaskData taskWithUpdatedPriority = new TaskData(taskDataViaTaskName.getName(), "LOW", taskDataViaTaskName.getDescription());
            taskWithUpdatedPriority.setId(taskDataViaTaskName.getId());
            dbTasks.taskDao().update(taskWithUpdatedPriority);
            Toast.makeText(TaskDetail.this, "Task Priority Lowered", Toast.LENGTH_SHORT).show();
            finish();
            return (true);

        } else if (itemId == R.id.logout_button) {
            Toast.makeText(TaskDetail.this, "Logging Out User", Toast.LENGTH_SHORT).show();
            AWSMobileClient.getInstance().signOut();
            finish();
        }
        return(super.onOptionsItemSelected(item));
    }
}


