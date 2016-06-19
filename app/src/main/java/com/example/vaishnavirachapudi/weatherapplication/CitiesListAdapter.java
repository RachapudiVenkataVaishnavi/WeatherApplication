package com.example.vaishnavirachapudi.weatherapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vaishnavirachapudi on 5/24/16.
 */
public class CitiesListAdapter extends ArrayAdapter<Cities> {

    Context context;
    ArrayList<Cities> cities;


    TextView cityName;
    TextView temp;

    public CitiesListAdapter(Context context, ArrayList<Cities> cities) {
        super(context,R.layout.activity_cities_list_adapter,cities);
        this.context = context;
        this.cities = cities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate( R.layout.activity_cities_list_adapter , parent,false);
        }
        Cities city = cities.get(position);
        cityName = (TextView) convertView.findViewById(R.id.textView_city);
        temp = (TextView) convertView.findViewById(R.id.textView_temp);

        cityName.setText(city.getCityKey());


        return convertView;
    }


}
