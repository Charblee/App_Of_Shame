package com.example.rossc.appofshame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SobrietyVerification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sobriety_verification);

        Button you_drunk_AF_button = (Button) findViewById(R.id.you_drunk_AF_button);
        you_drunk_AF_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TestPage.class);
                startActivity(intent);
            }
        });
    }

}
