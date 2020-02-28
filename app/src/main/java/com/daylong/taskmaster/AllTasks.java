package com.daylong.taskmaster;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.amazonaws.amplify.generated.graphql.ListTodosQuery;
import com.amazonaws.amplify.generated.graphql.OnCreateTodoSubscription;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;


public class AllTasks extends AppCompatActivity {

    TaskDatabase dbTasks;
    RecyclerView recyclerView;
    List<TaskData> dataSet;
//    private AWSAppSyncClient awsSyncer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        //
//        awsSyncer = AWSAppSyncClient.builder().context(getApplicationContext()).awsConfiguration(new AWSConfiguration(getApplicationContext())).build();
        //
        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().build();
        //

        this.dataSet = new ArrayList<>();

        this.dataSet = dbTasks.taskDao().getAllFromTaskList();

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSet, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(AllTasks.this));

        for(TaskData item : dataSet){
            Log.i("daylongTheGreat", item.getTaskName());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().build();
        this.dataSet = dbTasks.taskDao().getAllFromTaskList();
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSet, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(AllTasks.this));
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
            Intent goToAddMain = new Intent (this, MainActivity.class);
            this.startActivity(goToAddMain);
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
        }
        return(super.onOptionsItemSelected(item));
    }
}


