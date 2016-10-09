package com.example.rossc.appofshame;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

    public interface IMessageListener
    {
        void messageRecieved(String message_content);
    }

    private static final String TAG = ChatService.class.getSimpleName();
    private static ChatService _instance;
    private static String _url;
    private static Context _context;
    private static RequestQueue _queue;
    private static int _groupId;
    private static String _lastMessage = "0";
    private static IMessageListener _chatView;

    private static final String POST_MSG = "/message/{chat_id}/text";
    private static final String GET_MSG = "/text/{chat_id}/{message_id}";
    private static final String POLL_MSGS = "/new_messages/{chat_id}";
    private static final String CREATE_GROUP = "/create_group";

    private static final String CHAT_ID_PLACEHOLDER = "{chat_id}";
    private static final String MSG_ID_PLACEHOLDER = "{message_id}";

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

    public void registerChatView(IMessageListener chatView)
    {
        _chatView = chatView;
    }

    public void joinGroup(int groupId)
    {
        Log.v(TAG, "Join Group: "+ groupId);
        _lastMessage = "0";
        _groupId = groupId;
        pollForMessages();
    }

    public void createGroup(final IGroupCreated callback)
    {
        Log.v(TAG, "Create Group.");
        _lastMessage = "0";
        // Create group.
        StringBuilder url = new StringBuilder(_url);
        url.append(CREATE_GROUP);

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET,
                url.toString(),
                (JSONObject) null,
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

    public void pollForMessages()
    {
        Log.v(TAG, "Poll.");

        StringBuilder url = new StringBuilder(_url);
        url.append(POLL_MSGS.replace(CHAT_ID_PLACEHOLDER, ""+_groupId));

        // Poll for new messages.

        JSONObject json = null;
        try
        {
            json = new JSONObject();
            json.put("since", ""+_lastMessage);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        Log.v(TAG, json.toString());


        JsonArrayRequest getNewMessages = new JsonArrayRequest(
                Request.Method.POST,
                url.toString(),
                json.toString(),
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.v(TAG, "Poll response: "+response);
                        for(int i = 0; i < response.length(); i++)
                        {
                            try
                            {
                                JSONObject o =response.getJSONObject(i);
                                int msgNum = o.getInt("message_id");
                                getMessage(msgNum);
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        try
                        {
                            throw error;
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
        _queue.add(getNewMessages);
    }

    public void sendMessage(String message)
    {
        Log.v(TAG, "Send message: " + message);
        // Send message.

        JSONObject json = null;
        try
        {
            json = new JSONObject();
            json.put("msg",message);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        StringBuilder url = new StringBuilder(_url);
        url.append(POST_MSG.replace(CHAT_ID_PLACEHOLDER,""+_groupId));

        JsonObjectRequest postMsg = new JsonObjectRequest(
                Request.Method.POST,
                url.toString(),
                json,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        // Check for a new message (like the one just posted)
                        //pollForMessages();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        try
                        {
                            throw error;
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        // Check for a new message (like the one just posted)
                        //pollForMessages();
                    }
                });
        _queue.add(postMsg);
    }

    public void getMessage(int msgID)
    {
        Log.v(TAG, "Get message: " + msgID);
        // Get Message.

        StringBuilder url = new StringBuilder(_url);
        url.append(GET_MSG.replace(MSG_ID_PLACEHOLDER, ""+msgID)
                .replace(CHAT_ID_PLACEHOLDER,""+_groupId));

        JsonObjectRequest getMsg = new JsonObjectRequest(
                Request.Method.GET,
                url.toString(),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.v(TAG,"Got message: " + response);

                        try
                        {
                            String msg_content = response.getString("text");
                            //_lastMessage = response.getString("post_time");
                            // TODO: Dump messages to subscriber.
                            if (_chatView != null)
                            {
                                _chatView.messageRecieved(msg_content);
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        try
                        {
                            throw error;
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
        _queue.add(getMsg);
    }
}
