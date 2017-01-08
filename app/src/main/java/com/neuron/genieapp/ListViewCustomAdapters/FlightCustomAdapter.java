package com.neuron.genieapp.ListViewCustomAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neuron.genieapp.DataModels.FlightDataModel;
import com.neuron.genieapp.R;

import java.util.ArrayList;

/**
 * Created by Karan on 27/12/16.
 */

public class FlightCustomAdapter extends ArrayAdapter<FlightDataModel> implements View.OnClickListener{

    private ArrayList<FlightDataModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtFlightTime;
        TextView txtFlightDuration;
        TextView txtFlightPrice;
        TextView txtFlightSeatsLeft;
        ImageView info;
    }

    public FlightCustomAdapter(ArrayList<FlightDataModel> data, Context context) {
        super(context, R.layout.flight_list_row, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        FlightCustomAdapter flightdataModel=(FlightCustomAdapter)object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +flightdataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
       FlightDataModel flightDataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.flight_list_row, parent, false);
            viewHolder.txtFlightTime = (TextView) convertView.findViewById(R.id.flight_time);
            viewHolder.txtFlightDuration = (TextView) convertView.findViewById(R.id.flight_duration);
            viewHolder.txtFlightPrice = (TextView) convertView.findViewById(R.id.flight_price);
            viewHolder.txtFlightSeatsLeft = (TextView) convertView.findViewById(R.id.flight_seats_left);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.flight_image);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.info.setImageResource(flightDataModel.getFlightImage());
        viewHolder.txtFlightTime.setText(flightDataModel.getFlightTime());
        viewHolder.txtFlightDuration.setText(flightDataModel.getFlightDuration());
        viewHolder.txtFlightPrice.setText(flightDataModel.getFlightPrice());
        viewHolder.txtFlightSeatsLeft.setText(flightDataModel.getFlightSeatsLeft());

        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}