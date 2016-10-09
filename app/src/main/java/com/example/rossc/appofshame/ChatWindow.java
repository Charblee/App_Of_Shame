package com.example.rossc.appofshame;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ChatWindow extends AppCompatActivity implements ChatService.IGroupCreated {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);
        ChatService service = ChatService.Instance(ChatWindow.this);
        service.createGroup(ChatWindow.this);

            addChatFragment("awsddfasfasdfasdasdf");

            for(int i=0;i<50;i++)
            {
                addChatFragment(""+i);
            }

    }

    @Override
    public void onGroupJoined(int groupNumber)
    {
        AlertDialog.Builder teamDialog = new AlertDialog.Builder(ChatWindow.this);
        teamDialog.setTitle("Group ID");
        teamDialog.setMessage("" + groupNumber);
        teamDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        teamDialog.show();
    }

    public void addChatFragment(String message)
    {
        //Setup
        LinearLayout chat = (LinearLayout) findViewById(R.id.chat_view);
        FragmentManager man = getFragmentManager();
        FragmentTransaction trans = man.beginTransaction();

        //put fragment in view

        ChatFragment frag = new ChatFragment();
        frag.setMessage(message);
        trans.add(chat.getId(),frag);
        trans.commit();
    }
}
