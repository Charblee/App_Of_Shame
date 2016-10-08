package com.example.rossc.appofshame;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button chat_button = (Button) findViewById(R.id.chat_button);
        chat_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ChatService service = ChatService.Instance(MainActivity.this);
                service.createGroup();
            }
        });

        Button camera_button = (Button) findViewById(R.id.camera_button);
        camera_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);

            }
        });

        Button notDrunkBtn = (Button) findViewById(R.id.no_longer_drunk_btn);
        notDrunkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}


