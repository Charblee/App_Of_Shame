package com.example.rossc.appofshame;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ChatWindow extends AppCompatActivity implements ChatService.IMessageListener {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_window);
        ChatService service = ChatService.Instance(ChatWindow.this);
        service.registerChatView(this);

        Button sendButton = (Button)findViewById(R.id.chat_send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)findViewById(R.id.chat_send_message);
                // TODO: SendToServer(editText.getText());
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
        trans.add(chat.getId(),frag);
        trans.commit();
    }
}
