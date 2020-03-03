package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;


public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Save Username Button
        Button saveUsernameSubmit = findViewById(R.id.saveUsernameButton);
        saveUsernameSubmit.setOnClickListener(e -> {

            EditText userNameEditText = findViewById(R.id.username);
            SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editorIBarelyKnowHer = p.edit();
            editorIBarelyKnowHer.putString("username", userNameEditText.getText().toString());
            editorIBarelyKnowHer.apply();
            Toast.makeText(Settings.this, "Username Updated Successfully", Toast.LENGTH_LONG).show();
            finish();
        });
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
                                    .showSignIn(Settings.this, SignInUIOptions.builder().build(),
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
            Toast.makeText(Settings.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.increase_priority) {
            Toast.makeText(Settings.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);

        } else if (itemId == R.id.decrease_priority) {
            Toast.makeText(Settings.this, "Not Applicable", Toast.LENGTH_SHORT).show();
            return (true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
