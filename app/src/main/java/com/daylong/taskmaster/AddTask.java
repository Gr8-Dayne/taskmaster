package com.daylong.taskmaster;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.amazonaws.amplify.generated.graphql.CreateTaskListMutation;
import com.amazonaws.amplify.generated.graphql.CreateTodoMutation;
import com.amazonaws.amplify.generated.graphql.ListTaskListsQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.ResultListener;
import com.amplifyframework.storage.result.StorageUploadFileResult;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

import javax.annotation.Nonnull;

import type.CreateTaskListInput;
import type.CreateTodoInput;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


// Credit: https://codinginflow.com/tutorials/android/room-viewmodel-livedata-recyclerview-mvvm/part-7-add-note-activity
public class AddTask extends AppCompatActivity {

//    private TaskDatabase dbTasks;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPriority;
    private AWSAppSyncClient awsSyncer;
    private String imageURL;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));
        String[] permissions = {READ_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, 1);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                try {
                    Amplify.addPlugin(new AWSS3StoragePlugin());
                    Amplify.configure(getApplicationContext());
                    Log.i("StorageQuickstart", "All set and ready to go!");
                } catch (Exception e) {
                    Log.e("StorageQuickstart", e.getMessage());
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("StorageQuickstart", "Initialization error.", e);
            }
        });

        //
        //
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
        //
        //

//        runTaskListCreateMutation("Dayne");
//        runTaskListCreateMutation("Great Dayne");

        TextView addTaskCounter = findViewById(R.id.taskCount);

//        try {
//            addTaskCounter.setText("Total Tasks: " + dbTasks.taskDao().getCountOfTaskList());
//        } catch (Exception e) {
            Toast.makeText(AddTask.this, "Task Counter ERROR", Toast.LENGTH_SHORT).show();
//        }

        editTextTitle = findViewById(R.id.newTaskTitle);
        editTextDescription = findViewById(R.id.newTaskDescription);
        editTextPriority = findViewById(R.id.newTaskState);

        Button normalTaskToDB = findViewById(R.id.createTask);

        normalTaskToDB.setOnClickListener(e -> {

            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();
            String priority = editTextPriority.getText().toString();

            if (title.trim().isEmpty() || description.trim().isEmpty() || priority.trim().isEmpty()) {
                Toast.makeText(AddTask.this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;

            } else if (true) {

                try {
                    TaskData newTask = new TaskData(title, priority, description);
                    newTask.setImgURL(this.imageURL);

                    //
//                    dbTasks.taskDao().save(newTask);
                    addTaskToDynomo(newTask);
                    //

                    Toast.makeText(AddTask.this, "Task saved successfully", Toast.LENGTH_LONG).show();
                    return;
                } catch (Exception d) {
                    Toast.makeText(AddTask.this, "Task not saved", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        Button pixButton = findViewById(R.id.attach_file);

        pixButton.setOnClickListener(e -> {

            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, 47);

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);

        if(requestCode == 47 && resultCode == Activity.RESULT_OK){
            if(resultData != null){
                Log.i("daylongTheGreat", "URI from image pic is: " + resultData.getData().toString());
                Uri fileToS3 = resultData.getData();
                uploadFile(fileToS3);
            }
        }

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
                                    .showSignIn(AddTask.this, SignInUIOptions.builder().build(),
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
    //
    //
    // Add things to Amplify
    public void addTaskToDynomo(TaskData newTask){

        CreateTodoInput createTodoInput = CreateTodoInput.builder()
                .name(newTask.getName())
                .priority(newTask.getPriority())
                .description(newTask.getDescription())
                .build();

        awsSyncer.mutate(CreateTodoMutation.builder().input(createTodoInput).build())
                .enqueue(addTaskCallback);
    }

//    public void addTaskToDynomo(TaskData newTask){
//        awsSyncer.query(ListTaskListsQuery.builder().build())
//                .enqueue(new GraphQLCall.Callback<ListTaskListsQuery.Data>() {
//
//                    @Override
//                    public void onResponse(@Nonnull Response<ListTaskListsQuery.Data> response) {
//
//                        ListTaskListsQuery.Item item = response.data().listTaskLists().items().get(1);
//
//                        CreateTodoInput createTodoInput = CreateTodoInput.builder()
//                                .name(newTask.getName())
//                                .priority(newTask.getPriority())
//                                .description(newTask.getDescription())
//                                .todoTaskListId(item.id())
//                                .build();
//
//                        awsSyncer.mutate(CreateTodoMutation.builder().input(createTodoInput).build())
//                                .enqueue(addTaskCallback);
//                    }
//                    @Override
//                    public void onFailure(@Nonnull ApolloException e) {
//                    }
//                });
//    }

    private GraphQLCall.Callback<CreateTodoMutation.Data> addTaskCallback = new GraphQLCall.Callback<CreateTodoMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateTodoMutation.Data> response) {
            Log.i("daylongTheGreat", "-----ADD TASK CLICKED-----");
        }
        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("daylongTheGreat", "_____ERROR_____ " + e.toString());
        }
    };


    private void uploadFile(Uri fileURI) {

        String pathForPic = getPath(fileURI);
        Log.i("daylongTheGreat", "pathForPic: " + pathForPic);

        File picFile = new File(pathForPic);
        Log.i("daylongTheGreat", "picFile: " + picFile);

        String url = "https://bucketfortasks123331-taskenv.s3-us-west-2.amazonaws.com/";
        String key = "public/" + java.util.UUID.randomUUID().toString();

        this.imageURL = url + key;


//        TransferObserver uploadObserver = ;

//        File sampleFile = new File(getApplicationContext().getFilesDir(), "sample.txt");
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter(sampleFile));
//            writer.append("Yeet!");
//            writer.close();
//        } catch(Exception e) {
//            Log.e("daylongTheGreat", e.getMessage());
//        }

        Amplify.Storage.uploadFile(key, picFile.getAbsolutePath(), new ResultListener<StorageUploadFileResult>() {

                @Override
                public void onResult(StorageUploadFileResult result) {
                    Log.i("daylongTheGreat", "Successfully uploaded: " + result.getKey());
                }

                @Override
                public void onError(Throwable error) {
                    Log.e("daylongTheGreat", "ERROR", error);
                }
            }
        );
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }


    // Subscription
//    public void runTaskListCreateMutation(String name){
//        CreateTaskListInput createShelfInput = CreateTaskListInput.builder()
//                .name(name)
//                .build();
//        awsSyncer.mutate(CreateTaskListMutation.builder().input(createShelfInput).build())
//                .enqueue(addTaskListCallback);
//    }

    // Allow nav_and_actions to be utilized
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_and_actions, menu);
        return true;
    }

    private GraphQLCall.Callback<CreateTaskListMutation.Data> addTaskListCallback = new GraphQLCall.Callback<CreateTaskListMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateTaskListMutation.Data> response) {
            Log.i("daylongTheGreat", "Added Shelf");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("daylongTheGreat", e.toString());
        }
    };
    //
    //
    //

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
            Toast.makeText(AddTask.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.increase_priority) {
            Toast.makeText(AddTask.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.decrease_priority) {
            Toast.makeText(AddTask.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.logout_button) {
            Toast.makeText(AddTask.this, "Logging Out User", Toast.LENGTH_SHORT).show();
            AWSMobileClient.getInstance().signOut();
            finish();
        }
        return(super.onOptionsItemSelected(item));
    }
}


