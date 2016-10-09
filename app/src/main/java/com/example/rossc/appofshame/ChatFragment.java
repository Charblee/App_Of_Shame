package com.example.rossc.appofshame;

import android.app.ExpandableListActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChatFragment extends Fragment {

    private String _message = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle save) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);
        ((TextView) (view.findViewById(R.id.chat_text_view))).setText(_message);

        view.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                Intent shareMsg = new Intent();
                shareMsg.setAction(Intent.ACTION_SEND);
                shareMsg.putExtra(Intent.EXTRA_TEXT, _message);
                shareMsg.setType("text/plain");
                startActivity(shareMsg);
                return true;
            }
        });

        return view;
    }



    public void setMessage(String message) {
        _message = message;
    }
}