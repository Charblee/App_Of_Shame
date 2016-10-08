package com.example.rossc.appofshame;

import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

            }
        });

        Button camera_button = (Button) findViewById(R.id.camera_button);
        camera_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);

            }
            String mCurrentPhotoPath;
            private File createImageFile() throws IOException {
            // Create an image file name
                String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss"). format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = File.createTempFile(
                        imageFileName, /* prefix */
                        ".jpg", /*suffix */
                        storageDir /* directory */
                );

                mCurrentPhotoPath = "file:" + image.getAbsolutePath();
                return image;
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


