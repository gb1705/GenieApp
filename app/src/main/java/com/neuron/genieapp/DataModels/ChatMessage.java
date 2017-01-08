package com.neuron.genieapp.DataModels;

import com.neuron.genieapp.ListViewCustomAdapters.FlightCustomAdapter;

/**
 * Created by Karan on 12/05/2016.
 */
public class ChatMessage {
    public boolean left;
    public String text;
    public String time;
    public int imageView;
    public FlightCustomAdapter listView;

    public ChatMessage(boolean left, String text, String time) {
        super();
        this.left = left;
        this.text = text;
        this.time= time;
    }

    public ChatMessage(boolean left, int mImage) {
        this.left = left;
        this.imageView = mImage;
    }

    public ChatMessage(FlightCustomAdapter listView){
        this.listView = listView;
    }
}
