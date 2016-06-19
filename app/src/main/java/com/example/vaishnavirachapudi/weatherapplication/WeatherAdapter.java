package com.example.vaishnavirachapudi.weatherapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vaishnavirachapudi on 3/17/16.
 */
public class WeatherAdapter extends ArrayAdapter<Weather> {
    Context context;
    ArrayList<Weather> weatherList;
    public final static String DEGREE  = "\u00b0";

    public WeatherAdapter(Context context, ArrayList<Weather> weatherList) {
        super(context, R.layout.activity_forecast_adapter, weatherList);
        this.context = context;
        this.weatherList = weatherList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_forecast_adapter, parent, false);
            holder = new ViewHolder();
            holder.image =(ImageView)convertView.findViewById(R.id.imageView);
            holder.time = (TextView)convertView.findViewById(R.id.textView_time);
            holder.conditions = (TextView)convertView.findViewById(R.id.textView_conditions);
            holder.temp = (TextView)convertView.findViewById(R.id.textView_temp);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();
        ImageView image = holder.image;
        TextView time = holder.time;
        TextView conditions = holder.conditions;
        TextView temp = holder.temp;

        time.setText(weatherList.get(position).getTime());
        conditions.setText(weatherList.get(position).getClouds());

        String tempString = weatherList.get(position).getTemperature() + DEGREE + "F";
        temp.setText(tempString);

        Picasso.with(context).load(weatherList.get(position).getIconUrl()).into(image);

        return convertView;
    }
    static class ViewHolder {
        ImageView image;
        TextView time;
        TextView conditions;
        TextView temp;
    }
}


