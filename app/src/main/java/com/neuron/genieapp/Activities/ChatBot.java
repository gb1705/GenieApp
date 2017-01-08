package com.neuron.genieapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import com.neuron.genieapp.Adapters.ChatArray;
import com.neuron.genieapp.AppConfig;
import com.neuron.genieapp.DataModels.ChatMessage;
import com.neuron.genieapp.DataModels.Chats;
import com.neuron.genieapp.DataModels.FlightDataModel;
import com.neuron.genieapp.Database.ChatHistoryDatabase;
import com.neuron.genieapp.ListViewCustomAdapters.FlightCustomAdapter;
import com.neuron.genieapp.NetworkConnection;
import com.neuron.genieapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class ChatBot extends Activity {

    private static ChatArray adapter;
    private EditText chatEditText;
    ImageButton button;
    private static final String TAG = "ChatActivity";
    private ListView chatListView;
    Intent intent;
    private Handler handler;
    String tag_json_obj = "json_obj_req";
    String token1 = "Token ded19acd8c7cfa7b82707e67bb0cb1de84533e0b";
    String reply1;
    String token;
    ArrayList<FlightDataModel> flightDataModels;
    private ChatHistoryDatabase chatHistoryDatabase;
    static ArrayList<HashMap<String, String>> dataList;
    ListView listView;
    private static FlightCustomAdapter flightCustomAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_bot);

        if (savedInstanceState == null) {
            Log.d("MainActivity", "onCreate savedInstanceState null");
            adapter = new ChatArray(getApplicationContext());
        } else {
            Log.d("MainActivity", "onCreate savedInstanceState NOT null");
        }

        chatListView = (ListView) findViewById(R.id.chat_listView);
        chatEditText = (EditText) findViewById(R.id.chat_editText);
        button = (ImageButton) findViewById(R.id.btnSend);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent i = getIntent();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        chatHistoryDatabase = new ChatHistoryDatabase(this);
        getChatFromDatabase();

        dataList = new ArrayList<>();

        //initializing data models
        flightDataModels =  new ArrayList<>();
        //initializing custom listview adapters
        flightCustomAdapter = new FlightCustomAdapter(flightDataModels, getApplicationContext());

        Intent tokenIntent = getIntent();
        token = tokenIntent.getStringExtra("Token");

        handler = new Handler();

        chatListView.setAdapter(adapter);
        scrollMyListViewToBottom();
        chatListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                getQuery();
            }
        });
    }

    public boolean getQuery(){

        String currentTime = DateUtils.formatDateTime(getApplicationContext(),
                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME |
        DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_12HOUR);

        String query =  chatEditText.getText().toString();
        if (!query.isEmpty()) {
            adapter.add(new ChatMessage(false, query, currentTime));
        }
        chatHistoryDatabase.addChat(query, false, currentTime);
        makeServerRequest(query);
        chatEditText.setText("");
        return true;
    }

    public void receiveReply(String reply){

        String currentTime = DateUtils.formatDateTime(getApplicationContext(),
                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME |
                        DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_12HOUR);

        adapter.add(new ChatMessage(true, reply, currentTime));
        chatHistoryDatabase.addChat(reply, true, currentTime);
    }

    private void getChatFromDatabase() {
        List<Chats> chat = chatHistoryDatabase.getChatHistory();

        if (chat.size() > 0){
        for (Chats chats : chat){
            String message =  chats.get_message();
            boolean mboolean = chats.is_boolean();
            String time = chats.get_time();

            adapter.add(new ChatMessage(mboolean, message,time));
            }
        }
    }


    private void makeServerRequest(final String query) {
        if (NetworkConnection.getInstance(this).isOnline()) {
        } else {
            Snackbar.make(chatListView, "Check your internet connection!!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

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
                            processingJSONParsing(response);
                            reply1 =  response.getString("message").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        receiveReply(reply1);
                        Log.e("kkResponse",response.toString());
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

    public void processingJSONParsing(JSONObject response) throws JSONException {

        String message = response.getString("message").toString();
        String action = response.getString("action").toString();

        JSONObject dataJsonObject = new JSONObject(String.valueOf(response));

        if (action == "displayFlight"){
            displayFlight(dataJsonObject);
        }else if (action == "displayUber"){
            displayUber(dataJsonObject);
        }else if (action == "displayHotel"){
            displayHotel(dataJsonObject);
        }else displayMessage(message);
    }

    private void displayHotel(JSONObject dataJsonObject) {}

    private String displayMessage(String message) {
        return null;
    }

    private ListView displayFlight(JSONObject dataJsonObject) {
        // Getting JSON Array node
        try {
            JSONArray dataJsonArray = dataJsonObject.getJSONArray("data");
            // looping through All Contacts
            for (int i = 0; i < dataJsonArray.length(); i++) {

                JSONObject data = dataJsonArray.getJSONObject(i);

                String flight_name = data.getString("flight_name");
                String flight_source = data.getString("source");
                String flight_destination = data.getString("destination");
                String flight_arrival_time = data.getString("");
                String flight_departure_time = data.getString("");
                String flight_duration = data.getString("duration");
                String flight_airline_logo = data.getString("logo_url");
                String flight_seats_left = data.getString("seats_left");
                String flight_price = data.getString("price");

                // tmp hash map for single contact
                HashMap<String, String> flight_data = new HashMap<>();

                // adding each child node to HashMap key => value
                flight_data.put("", flight_name);
                flight_data.put("", flight_source);
                flight_data.put("", flight_destination);
                flight_data.put("", flight_arrival_time);
                flight_data.put("", flight_departure_time);
                flight_data.put("", flight_duration);
                flight_data.put("", flight_seats_left);

                flightDataModels.add
                        (new FlightDataModel(R.drawable.icon_send,
                                flight_arrival_time, flight_duration, flight_price, flight_seats_left));

                adapter.add(new ChatMessage(flightCustomAdapter));

//                listView.setAdapter(flightCustomAdapter);
                // adding data to data list
                dataList.add(flight_data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void displayUber(JSONObject dataJsonObject) {
        // Getting JSON Array node
//        try {
//            JSONArray dataJsonArray = dataJsonObject.getJSONArray("data");
//            // looping through All Contacts
//            for (int i = 0; i < dataJsonArray.length(); i++) {
//
//                JSONObject data = dataJsonArray.getJSONObject(i);
//
//                String flight_name = data.getString("flight_name");
//                String flight_source = data.getString("source");
//                String flight_destination = data.getString("destination");
//                String flight_arrival_time = data.getString("");
//                String flight_departure_time = data.getString("");
//                String flight_duration = data.getString("duration");
//                String flight_airline_logo = data.getString("logo_url");
//                String flight_seats_left = data.getString("seats_left");
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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

    private void scrollMyListViewToBottom() {
        chatListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                chatListView.setSelection(adapter.getCount() - 1);
            }
        });
    }

}
