package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Save Username
        Button saveUsernameSubmit = findViewById(R.id.saveUsernameButton);
        saveUsernameSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View e) {
                TextView item = Settings.this.findViewById(R.id.usernameShowSubmit);
                item.setText("USERNAME SAVED");
            }
        });

        // Redirect to TaskDetail
        Button buttonGoToTaskDetail = findViewById(R.id.redirectToTaskDetail);
        buttonGoToTaskDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent goToTaskDetail = new Intent (Settings.this, TaskDetail.class);
                Settings.this.startActivity(goToTaskDetail);
            }
        });
    }

    //
    //
    // Credit: 02/11/2020 class demo
    public void viewUsername(View v) {
        Intent i = new Intent(this, TaskDetail.class);
//        EditText userNameEditText = findViewById(R.id.username);
//        i.putExtra("newInput", userNameEditText.getText().toString());
        i.putExtra("newInput", "theMan");
        startActivity(i);
    }
}
