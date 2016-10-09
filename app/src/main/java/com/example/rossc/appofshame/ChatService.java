package com.example.rossc.appofshame;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatService
{
    public interface IGroupCreated
    {
        void onGroupJoined(int groupNumber);
    }

    private static final String TAG = ChatService.class.getSimpleName();
    private static ChatService _instance;
    private static String _url;
    private static Context _context;
    private static RequestQueue _queue;
    private static int _groupId;
    private static String _lastMessage = "0";

    private static final String POST_MSG = "/message/{chat_id}/text";
    private static final String GET_MSG = "/text/{chat_id}/{message_id}";
    private static final String POLL_MSGS = "/new_messages/{chat_id}";
    private static final String CREATE_GROUP = "/create_group";

    private static final String CHAT_ID_PLACEHOLDER = "{chat_id}";

    public static ChatService Instance(Context c)
    {
        if (_instance == null)
        {
            _instance = new ChatService();
            _context = c;
            _queue = Volley.newRequestQueue(_context);
        }
        return _instance;
    }

    private ChatService()
    {
        Log.v(TAG, "Created ChatService.");
        _url = "http://10.18.3.206:8080";
    }

    public void joinGroup(int groupId)
    {
        Log.v(TAG, "Join Group: "+ groupId);
        _groupId = groupId;
    }

    public void pollForMessages()
    {
        Log.v(TAG, "Poll.");

        StringBuilder url = new StringBuilder(_url);
        url.append(POLL_MSGS.replace(CHAT_ID_PLACEHOLDER, ""+_groupId));

        // TODO: poll for new messages.
        JsonArrayRequest getNewMessages = new JsonArrayRequest(
                Request.Method.GET,
                url.toString(),
                null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });
    }

    public void createGroup(final IGroupCreated callback)
    {
        Log.v(TAG, "Create Group.");
        // TODO: Create group.
        StringBuilder url = new StringBuilder(_url);
        url.append(CREATE_GROUP);

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url.toString(),
                null,
                new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.v(TAG, "Create group res: " + response);
                try{
                    _groupId = response.getInt("chat_id");
                    // Show on screen.
                    callback.onGroupJoined(_groupId);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "Create group err: " + error.getMessage());
                try
                {
                    throw error;
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        _queue.add(req);
    }

    public void sendMessage(String message)
    {
        Log.v(TAG, "Send message: " + message);
        // TODO: Send message.
    }
}
