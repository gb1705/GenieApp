package com.neuron.genieapp.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.neuron.genieapp.R;

/**
 * Created by Karan on 6/12/16.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private int mWidth;
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.ic_chat_black_24dp, R.drawable.ic_face_black_24dp,
            R.drawable.ic_call_black_24dp, R.drawable.ic_call_black_24dp,

    };

    // Constructor
    public GridViewAdapter(Context c, int width){
        mContext = c;
        mWidth = width;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
//        imageView.setLayoutParams(new GridView.LayoutParams(144, 144));
//        imageView.setBackgroundResource(R.drawable.grid_items_border);
        return imageView;
    }

}