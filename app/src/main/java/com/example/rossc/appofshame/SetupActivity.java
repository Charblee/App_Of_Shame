package com.example.rossc.appofshame;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
public class SetupActivity extends AppCompatActivity {

    //TODO: Add support to get an emergency contact's phone number
    private String _contactName = "";
    private int _hour = -1;
    private int _minute = -1;
    private final int CONTACT_PICKER_RESULT = 1001;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_activity);

        TextView endTime = (TextView) findViewById(R.id.end_time);
        displayTime();

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Time Picker
                TimePickerDialog timePicker = new TimePickerDialog(SetupActivity.this,R.style.DatePickerStyle, new TimePickerDialog.OnTimeSetListener() {
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

        TextView emergency = (TextView) findViewById(R.id.emergency_contact);
        emergency.setText("Click to set contact");
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Contact Picker
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CONTACT_PICKER_RESULT);
            }
        });

        Button gitDrunk = (Button) findViewById(R.id.git_drunk);
        gitDrunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:

                    //TODO: Fix or disable emergency contact phone number

                    Uri result = data.getData();
                    Cursor cursor = getContentResolver().query(result,null,null,null,null);
                    cursor.moveToFirst();
                    int name = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    _contactName = cursor.getString(name);
                    displayName();
                    break;
                default:
                    break;
            }
        }
    }

    private void displayTime() {
        TextView endTime = (TextView) findViewById(R.id.end_time);

        if (_hour != -1 && _minute != -1) {

            StringBuilder time = new StringBuilder();
            time.append((_hour % 12 == 0) ? 12 : _hour % 12); //inline ifs for the win!!!
            time.append(":");
            if (_minute < 10) {
                time.append("0");
            }
            time.append(_minute);
            if (_hour >= 12) {
                time.append(" pm");
            } else {
                time.append(" am");
            }
            endTime.setText(time.toString());
        } else {
            endTime.setText("Click to set time");
        }
    }

    private void displayName() {
        TextView emergency = (TextView) findViewById(R.id.emergency_contact);
        if (!_contactName.isEmpty()) {
            emergency.setText(_contactName );
        }
        else
        {
            emergency.setText("Click to set contact");
        }
    }
}
