package com.neuron.genieapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neuron.genieapp.DataModels.ChatMessage;
import com.neuron.genieapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karan on 16/12/16.
 */

public class ChatArray extends ArrayAdapter<ChatMessage> {

    private static final int LEFT_MESSAGE = -1;
    private static final int RIGHT_MESSAGE = 1;

    private TextView messageTextView, timeTextView;
    private List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
    private RelativeLayout wrapper;
    ListView listView;

    @Override
    public void add(ChatMessage object) {
        chatMessages.add(object);
        super.add(object);
    }

    public ChatArray(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public int getCount() {
        return this.chatMessages.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessages.get(index);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return (chatMessages.get(position).left ? LEFT_MESSAGE : RIGHT_MESSAGE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatMessage comment = getItem(position);
        ChatMessage time = getItem(position);

        int type = getItemViewType(position);

        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(type==LEFT_MESSAGE){
                row = inflater.inflate(R.layout.chat_listitem_left, parent, false);
            }
            if(type==RIGHT_MESSAGE){
                row = inflater.inflate(R.layout.chat_listitem_right, parent, false);
            }
        }

        wrapper = (RelativeLayout) row.findViewById(R.id.wrapper);

        messageTextView = (TextView) row.findViewById(R.id.text);
        timeTextView = (TextView) row.findViewById(R.id.text_time);
        listView = (ListView) row.findViewById(R.id.serverListView);

        messageTextView.setText(comment.text);
        timeTextView.setText(comment.time);

        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}
