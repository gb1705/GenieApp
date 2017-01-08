package com.neuron.genieapp.Adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neuron.genieapp.DataModels.ChatMessage;
import com.neuron.genieapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan on 12/05/2016.
 */
public class ChatArrayAdapter extends ArrayAdapter<ChatMessage>{
    private TextView chatText;
    private List<ChatMessage> MessageList = new ArrayList<ChatMessage>();
    private LinearLayout layout;
    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);}
        public void add(ChatMessage object) {
            // TODO Auto-generated method stub

            MessageList.add(object);
            super.add(object);
        }

    public int getCount()
    {
        return this.MessageList.size();
    }

    public ChatMessage getItem(int index){

        return this.MessageList.get(index);
    }

    public View getView(int position,View ConvertView, ViewGroup parent){

        View v = ConvertView;
        if(v==null){

            LayoutInflater inflater = (LayoutInflater)
                    this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v =inflater.inflate(R.layout.chat, parent,false);

        }

        layout = (LinearLayout)v.findViewById(R.id.Message1);
        ChatMessage messageobj = getItem(position);
        chatText =(TextView)v.findViewById(R.id.SingleMessage);



//        if(position % 2 == 0) {
//            chatText.setText(messageobj.message);
//            chatText.setTextColor(R.color.primary);
//
//        }
//        else{
//            chatText.setText(messageobj.message);
//            chatText.setTextColor(R.color.colorAccent);
//        }

//        chatText.setBackgroundResource(messageobj.left ? R.color.colorPrimary :R.color.colorAccent);
//        chatText.setTextColor(messageobj.left ? R.color.colorPrimary :R.color.colorAccent);
        layout.setGravity(messageobj.left? Gravity.LEFT:Gravity.RIGHT);
//        chatText.setText(messageobj.message);
        return v;
    }

//    public Bitmap decodeToBitmap(byte[] decodedByte) {
//        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
//    }
}