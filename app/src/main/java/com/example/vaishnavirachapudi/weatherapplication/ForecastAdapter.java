package com.example.vaishnavirachapudi.weatherapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vaishnavirachapudi on 3/19/16.
 */
public class ForecastAdapter extends ArrayAdapter<Forecast> {
    Context context;
    ArrayList<Forecast> forecastList;
    public final static String DEGREE  = "\u00b0";
    DatabaseDataManager manager;
    Cities city;
    Notes note =null;

    public ForecastAdapter(Context context, ArrayList<Forecast> forecastList, Cities city ) {
        super(context, R.layout.activity_forecast_adapter, forecastList);
        this.context = context;
        this.forecastList = forecastList;
        manager = new DatabaseDataManager(this.context);
        this.city = city;
        for (int i=0;i<forecastList.size();i++)
        {
            Log.d("demo",forecastList.get(i).toString());
        }
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
            holder.imageView =(ImageView) convertView.findViewById(R.id.bookmark_imageView);
            convertView.setTag(holder);
        }

        holder = (ViewHolder)convertView.getTag();
        ImageView imageView = holder.imageView;
        ImageView image = holder.image;
        TextView time = holder.time;
        TextView conditions = holder.conditions;
        TextView temp = holder.temp;

        time.setText(forecastList.get(position).getDate());
        conditions.setText(forecastList.get(position).getClouds());

        String tempString = forecastList.get(position).getHightemp() + DEGREE + "F" + "/" +
                forecastList.get(position).getLowTemp() + DEGREE + "F";
        temp.setText(tempString);


        note = manager.getNote(city.getCityKey(),forecastList.get(position).getDate());
        if(note != null){
            Log.d("image",note.toString()+"  "+position);
            Picasso.with(context).load(R.drawable.bookmark_filled).resize(50,50).into(imageView);
        }

        Picasso.with(context).load(forecastList.get(position).getIconUrl()).into(image);

        return convertView;
    }
    static class ViewHolder {
        ImageView image;
        ImageView imageView;
        TextView time;
        TextView conditions;
        TextView temp;
    }
}



