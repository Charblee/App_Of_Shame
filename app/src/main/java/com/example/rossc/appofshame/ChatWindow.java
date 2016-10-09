package com.example.rossc.appofshame;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends AppCompatActivity implements ChatService.IMessageListener {

    private ArrayList<ChatFragment> fragments = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);
        final ChatService service = ChatService.Instance(ChatWindow.this);
        service.registerChatView(this);

        Button sendButton = (Button)findViewById(R.id.chat_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)findViewById(R.id.chat_send_message);
                // TODO: SendToServer(editText.getText());
                String msg = editText.getText().toString();
                service.sendMessage(msg);
                editText.setText("");
            }
        });

        FloatingActionButton refreshBtn = (FloatingActionButton) findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clearFragments();
                service.pollForMessages();
            }
        });
    }

    @Override
    public void messageRecieved(String message_content)
    {
        //Setup
        LinearLayout chat = (LinearLayout) findViewById(R.id.chat_view);
        FragmentManager man = getFragmentManager();
        FragmentTransaction trans = man.beginTransaction();

        //put fragment in view

        ChatFragment frag = new ChatFragment();
        frag.setMessage(message_content);
        trans.add(chat.getId(), frag);
        trans.commit();

        fragments.add(frag);
        ScrollView msgView = (ScrollView) findViewById(R.id.scrollView2);
        msgView.fullScroll(View.FOCUS_DOWN);
    }

    public void clearFragments()
    {
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        for(ChatFragment frag: fragments)
        {
            trans.remove(frag);
        }
        trans.commit();
        fragments = new ArrayList<>();
    }
}
