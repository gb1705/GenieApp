package com.neuron.genieapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.neuron.genieapp.Adapters.ChatArrayAdapter;
import com.neuron.genieapp.AppConfig;
import com.neuron.genieapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChatBotActivity extends Activity {
    private static final String TAG = "ChatActivity";
    private ChatArrayAdapter adp;
    private ListView list;
    private EditText chatText;
    private ImageButton send;
    Intent intent;
    private boolean side = false;
    private Handler handler;
//    String url = "http://172.168.0.90:8000/api/chat/";
    String tag_json_obj = "json_obj_req";
    String token1 = "Token ded19acd8c7cfa7b82707e67bb0cb1de84533e0b";
    View view;
    String reply1;
    String token;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent i = getIntent();

        setContentView(R.layout.activity_chat_bot);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Intent tokenIntent = getIntent();
        token = tokenIntent.getStringExtra("Token");

//        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();

        send = (ImageButton) findViewById(R.id.btn);
        list = (ListView) findViewById(R.id.listview);

        adp = new ChatArrayAdapter(getApplicationContext(), R.layout.chat);
        list.setAdapter(adp);
        chatText = (EditText) findViewById(R.id.chat_t);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                hideSoftKeyboard();

                receiveMessage();
            }
        });
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adp);
        adp.registerDataSetObserver(new DataSetObserver() {
            public void OnChanged(){
                super.onChanged();
                list.setSelection(adp.getCount() -1);
            }
        });
    }
    private boolean receiveMessage(){
//        adp.add(new ChatMessage(side, chatText.getText().toString()));

        String query = chatText.getText().toString();

        makeServerRequest(query);
        chatText.setText("");
        return true;
    }

    private boolean replyMessage(String reply){
//        adp.add(new ChatMessage(!side, reply));
        chatText.setText("");
        return true;
    }

    private void makeServerRequest(final String query) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-type", "application/json; charset=utf-8");
        headers.put("location", "1");
        headers.put("text", query);
        headers.put("session_id","1");

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL_CHATBOT,
                new JSONObject(headers),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String reply = response.toString();

                        try {
                            reply1 =  response.getString("message").toString();
                            Toast.makeText(ChatBotActivity.this, reply1, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        replyMessage(reply1);
                        Log.e("kkResponse",response.toString());
//                        Toast.makeText(ChatBotActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("kkError",error.toString());

            }
        })
        {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", token1);
                return headers;
            }
        };
        // Adding request to request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(objectRequest);
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}