package com.example.rossc.appofshame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SetupActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_activity);

        Button emergency_button = (Button)findViewById(R.id.select_chat_friends);
        emergency_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //contact picker
            }
        });

        Button git_drunk = (Button)findViewById(R.id.git_drunk);
        git_drunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
