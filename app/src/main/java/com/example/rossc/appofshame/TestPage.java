package com.example.rossc.appofshame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_page);

        final Button left_button = (Button) findViewById(R.id.left_button);
        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestPage.this);

                alertDialogBuilder.setTitle("Nope! You're still drunk!");

                alertDialogBuilder
                        .setMessage("")
                        .setCancelable(false)
                        .setPositiveButton("My B.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                TestPage.this.finish();
                            }
                        });
                alertDialogBuilder.show();
            }
        });
        Button right_button = (Button) findViewById(R.id.right_button);
        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TestPage.this);

                alertDialogBuilder.setTitle("Congratulations! You're sober...enough.");

                alertDialogBuilder
                        .setMessage("")
                        .setCancelable(false)
                        .setPositiveButton("Swag.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getApplicationContext(), LaunchActivity.class);
                                startActivity(intent);
                                TestPage.this.finish();
                            }
                        });
                alertDialogBuilder.show();

            }
        });
    }
}