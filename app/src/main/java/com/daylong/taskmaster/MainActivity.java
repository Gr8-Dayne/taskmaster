package com.daylong.taskmaster;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.amazonaws.amplify.generated.graphql.CreateTodoMutation;
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
import type.CreateTodoInput;


// Credit: https://stackoverflow.com/questions/33897978/android-convert-edittext-to-string
// Credit: https://www.youtube.com/watch?v=JLwW5HivZg4
// Credit: https://www.youtube.com/watch?v=reSPN7mgshI&feature=youtu.be
// Credit: https://proandroiddev.com/a-guide-to-recyclerview-selection-3ed9f2381504
// Credit: https://guides.codepath.com/android/using-the-recyclerview
// Credit: https://www.journaldev.com/10024/android-recyclerview-android-cardview-example-tutorial
public class MainActivity extends AppCompatActivity {

//    TaskDatabase dbTasks;
    RecyclerView recyclerView;
    List<TaskData> dataSet;
    private AWSAppSyncClient awsSyncer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        //
        awsSyncer = AWSAppSyncClient.builder().context(getApplicationContext()).awsConfiguration(new AWSConfiguration(getApplicationContext())).build();
        //
//        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().build();
        //

        addHardCodedTask();

        this.dataSet = new ArrayList<>();

//        this.dataSet = dbTasks.taskDao().getAllFromTaskList();

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSet, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();

        TextView usernameMainTextView = findViewById(R.id.addTaskH1);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String customUsername = sharedPreferences.getString("username", "default");
        usernameMainTextView.setText(customUsername + "'s Tasks");

        //
        awsSyncer = AWSAppSyncClient.builder().context(getApplicationContext()).awsConfiguration(new AWSConfiguration(getApplicationContext())).build();
        //
//        dbTasks = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "tasks").allowMainThreadQueries().build();
        //

        addHardCodedTask();

        this.dataSet = new ArrayList<>();

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSet, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }



    //
    //
    //
        // Class Demo
//    public void runBuyableItemCreateMutation(String name, float price){
//
//        // I need to know shelf ids.
//        awsSyncer.query(ListShelfsQuery.builder().build()).enqueue(new GraphQLCall.Callback<ListShelfsQuery.Data>() {
//
//            @Override
//            public void onResponse(@Nonnull Response<ListShelfsQuery.Data> response) {
//                ListShelfsQuery.Item item = response.data().listShelfs().items().get(1);
//
//                // old code goes here
//                CreateBuyableItemInput createBuyableItemInput = CreateBuyableItemInput.builder().title(name).buyableItemShelfId(item.id()).price((double) price).build();
//
//                awsSyncer.mutate(CreateBuyableItemMutation.builder().input(createBuyableItemInput).build()).enqueue(addMutationCallback);
//            }
//            @Override
//            public void onFailure(@Nonnull ApolloException e) {
//            }
//        });
//    }



    // Mattäus' Code
//    public void createTodoMutation(TaskData task)
//    {
//        CreateTodoInput createTodoInput = CreateTodoInput.builder()
//                .taskName(task.getTaskName())
//                .description(task.getDescription())
//                .state(task.getState())
//                .build();
//        awsSyncer.mutate(CreateTodoMutation.builder().input(createTodoInput).build()).enqueue(createMutationCallback);
//    }
//
//    private GraphQLCall.Callback<CreateTodoMutation.Data>createMutationCallback = new GraphQLCall.Callback<CreateTodoMutation.Data>() {
//
//        @Override
//        public void onResponse(@Nonnull Response<CreateTodoMutation.Data> response) {
//            Log.i("daylongTheGreat", "Added Task");
//        }
//
//        @Override
//        public void onFailure(@Nonnull ApolloException e) {
//            Log.e("daylongTheGreat", e.toString());
//        }
//    };


    // Add things to Amplify
    public void addHardCodedTask(){
        CreateTodoInput hardCodedTodoInput = CreateTodoInput.builder()
                .taskName("Do Things Right")
                .state("Urgent")
                .description("Doing it right")
                .build();
        awsSyncer.mutate(CreateTodoMutation.builder().input(hardCodedTodoInput).build())
                .enqueue(addHardCodedTaskCallback);
    }

    private GraphQLCall.Callback<CreateTodoMutation.Data> addHardCodedTaskCallback = new GraphQLCall.Callback<CreateTodoMutation.Data>() {

        @Override
        public void onResponse(@Nonnull Response<CreateTodoMutation.Data> response) {
            Log.i("daylongTheGreat", "-----TASK ADDED SUCCESSFULLY-----");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("daylongTheGreat", e.toString());
        }
    };

    // Get things from Amplify
//    public void getTasksFromAmplify(){
//        awsSyncer.query(ListTodosQuery.builder().build()).responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK).enqueue(tasksCallBack);
//    }
//
//    private GraphQLCall.Callback<ListTodosQuery.Data> tasksCallBack = new GraphQLCall.Callback<ListTodosQuery.Data>() {
//
//        @Override
//        public void onResponse(@Nonnull Response<ListTodosQuery.Data> response) {
//            assert response.data() != null;
//            Log.i("daylongTheGreat", Objects.requireNonNull(Objects.requireNonNull(response.data().listTodos()).items()).toString());
//        }
//
//        @Override
//        public void onFailure(@Nonnull ApolloException e) {
//            Log.e("daylongTheGreat", e.toString());
//        }
//    };

    // Subscriptions
//    private void subscribeToAmplifyList(){
//        OnCreateTodoSubscription potatoSubscription = OnCreateTodoSubscription.builder().build();
//        AppSyncSubscriptionCall subWatch = awsSyncer.subscribe(potatoSubscription);
//        subWatch.execute(subCallback);
//    }
//
//    private AppSyncSubscriptionCall.Callback subCallback = new AppSyncSubscriptionCall.Callback() {
//
//        @Override
//        public void onResponse(@Nonnull Response response) {
//            assert response.data() != null;
//            Log.i("daylongTheGreat", response.data().toString());
//        }
//
//        @Override
//        public void onFailure(@Nonnull ApolloException e) {
//            Log.e("daylongTheGreat", e.toString());
//        }
//
//        @Override
//        public void onCompleted() {
//            Log.i("daylongTheGreat", "Subscription completed");
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

        } else if (itemId == R.id.delete_this_task) {
            return (true);

        } else if (itemId == R.id.increase_priority) {
            return (true);
        }
        return(super.onOptionsItemSelected(item));
    }
}


