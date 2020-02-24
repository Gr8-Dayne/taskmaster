package com.daylong.taskmaster;


import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

                EditText userNameEditText = findViewById(R.id.username);

                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                SharedPreferences.Editor editorIBarelyKnowHer = p.edit();

                editorIBarelyKnowHer.putString("username", userNameEditText.getText().toString());

                editorIBarelyKnowHer.apply();
            }
        });
    }
}
