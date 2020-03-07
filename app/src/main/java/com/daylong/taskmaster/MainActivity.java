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
import com.amazonaws.amplify.generated.graphql.CreateTaskListMutation;
import com.amazonaws.amplify.generated.graphql.CreateTodoMutation;
import com.amazonaws.amplify.generated.graphql.ListTodosQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import type.CreateTaskListInput;
import type.CreateTodoInput;


// Credit: https://frontrowviews.com/Home/Event/Play/5e1929feeee6d91a185440d6
// Credit: https://aws-amplify.github.io/docs/android/storage
// Credit: https://github.com/codefellows/seattle-java-401d9/commit/8f5939819ea796a4a748ce0723b2ccccd8e1d9e6
public class MainActivity extends AppCompatActivity {

//    TaskDatabase dbTasks;
    RecyclerView recyclerView;
    List<TaskData> dataSetMain = new ArrayList<>();
    private AWSAppSyncClient awsSyncer;

//    getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

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

//        try {
//            this.dataSetMain = dbTasks.taskDao().getHIGHPriorityTasks("HIGH");
//        } catch (Exception e) {
//            Log.i("daylongTheGreat", String.valueOf(dataSetMain));
//        }

        recyclerView = findViewById(R.id.my_Main_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSetMain, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        getTasksFromAmplify();

        for (TaskData item : dataSetMain) {
            Log.i("daylongTheGreat", item.getName() + " " + item.getPriority());
        }

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("daylongTheGreat", "onResult: " + userStateDetails.getUserState());
                        if (userStateDetails.getUserState().toString().equals("SIGNED_OUT")) {
                            AWSMobileClient
                                    .getInstance()
                                    .showSignIn(MainActivity.this, SignInUIOptions.builder().build(),
                                    new Callback<UserStateDetails>() {
                                        @Override
                                        public void onResult(UserStateDetails result) {
                                            Log.d("daylongTheGreat", "onResult: " + result.getUserState());
                                            switch (result.getUserState()){
                                                case SIGNED_IN:
                                                    uploadWithTransferUtility();
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

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("daylongTheGreat", "onResult: " + userStateDetails.getUserState());
                        if (userStateDetails.getUserState().toString().equals("SIGNED_OUT")) {
                            AWSMobileClient
                                    .getInstance()
                                    .showSignIn(MainActivity.this, SignInUIOptions.builder().build(),
                                            new Callback<UserStateDetails>() {
                                                @Override
                                                public void onResult(UserStateDetails result) {
                                                    Log.d("daylongTheGreat", "onResult: " + result.getUserState());
                                                    switch (result.getUserState()){
                                                        case SIGNED_IN:
                                                            Log.i("INIT", "LOGGED IN");
                                                            break;
                                                        case SIGNED_OUT:
                                                            Log.i("daylongTheGreat", "_____ERROR: NO SIGN IN_____");
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

//        this.dataSetMain = dbTasks.taskDao().getHIGHPriorityTasks("HIGH");
        recyclerView = findViewById(R.id.my_Main_recycler_view);
        recyclerView.setAdapter(new TaskAdapter(dataSetMain, getApplication()));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        getTasksFromAmplify();
    }

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


    //
    //
    // Demo code
    public void uploadWithTransferUtility() {

        TransferUtility transferUtility = TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();

        File file = new File(getApplicationContext().getFilesDir(), "sample.txt");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append("S3 is crazy.");
            writer.close();
        }
        catch(Exception e) {
            Log.e("daylongTheGreat", e.getMessage());
        }

        TransferObserver uploadObserver = transferUtility.upload(
                        "public/sample.txt",
                        new File(getApplicationContext().getFilesDir(),"sample.txt"));
        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;
                Log.d("daylongTheGreat", "ID:" + id + " bytesCurrent: " + bytesCurrent + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }
        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }
        Log.d("daylongTheGreat", "Bytes Transferred: " + uploadObserver.getBytesTransferred());
        Log.d("daylongTheGreat", "Bytes Total: " + uploadObserver.getBytesTotal());
    }
    //
    //
    //


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

        } else if (itemId == R.id.logout_button) {
            Toast.makeText(MainActivity.this, "Logging Out User", Toast.LENGTH_SHORT).show();
            AWSMobileClient.getInstance().signOut();
            finish();
        }
        return(super.onOptionsItemSelected(item));
    }
}


