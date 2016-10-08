package com.example.rossc.appofshame;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class SetupActivity extends AppCompatActivity {

    private String _emergencyText;
    private int _hour = -1;
    private int _minute = -1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_activity);

        TextView endTime = (TextView)findViewById(R.id.end_time);
        displayTime();

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Time Picker
                TimePickerDialog timePicker = new TimePickerDialog(SetupActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        _hour = hour;
                        _minute = minute;

                        displayTime();
                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);
                timePicker.setTitle("Drunk count down");
                timePicker.show();
            }
        });

        TextView emergency = (TextView)findViewById(R.id.emergency_contact);
        emergency.setText("Click to set contact");
        emergency.setOnClickListener(new View.OnClickListener() {

            final int CONTACT_PICKER_RESULT = 1001;

            @Override
            public void onClick(View view) {
                //Contact Picker
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CONTACT_PICKER_RESULT);
            }
        });

        Button gitDrunk = (Button)findViewById(R.id.git_drunk);
        gitDrunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayTime()
    {
        TextView endTime = (TextView)findViewById(R.id.end_time);

        if(_hour != -1 && _minute != -1)
        {

            StringBuilder time = new StringBuilder();
            time.append((_hour%12 == 0)?12:_hour%12); //INLINE IFS!!!
            time.append(":");
            if(_minute<10)
            {
                time.append("0");
            }
            time.append(_minute);
            if(_hour>=12)
            {
                time.append(" pm");
            }
            else
            {
                time.append(" am");
            }
            endTime.setText(time.toString());
        }
        else
        {
            endTime.setText("Click to set time");
        }
    }
}
