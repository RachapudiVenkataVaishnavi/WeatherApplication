package com.example.vaishnavirachapudi.weatherapplication;



import android.app.TabActivity;
import android.content.Intent;



import android.os.Bundle;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;



public class CityDataActivity  extends TabActivity {

    Cities city;
    DatabaseDataManager manager;
    ArrayList<Cities> cities;
    int Flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_data);
        manager = new DatabaseDataManager(this);
        cities= manager.getAllCities();

        city=(Cities)getIntent().getSerializableExtra(MainActivity.LOCATION_KEY);
        if(city==null)
        {
            city=(Cities)getIntent().getSerializableExtra(AddNotesActivity.CITY);
            Flag=1;
            Toast.makeText(CityDataActivity.this, "Note Saved Successfully", Toast.LENGTH_LONG).show();
        }

        TabHost tabHost =(TabHost)findViewById(android.R.id.tabhost);


        Intent intent = new Intent(this, HourlyDataActivity.class);
        intent.putExtra(MainActivity.LOCATION_KEY, city);

        Intent intent1 = new Intent(this, ForecastActivity.class);
        intent1.putExtra(MainActivity.LOCATION_KEY , city);

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("HOURLY DATA").setContent(intent));



        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("FORECAST")
                .setContent(intent1));

        tabHost.setCurrentTab(Flag);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:

                Intent intent = new Intent(CityDataActivity.this,AddCityActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_clear:

                manager.deleteAll();
                manager.deleteAllNotes();
                Intent intent1 = new Intent(CityDataActivity.this, MainActivity.class);
                startActivity(intent1);


                return true;
            case R.id.action_view:
                if(cities.size()<1){
                    Toast.makeText(getBaseContext(), "Please Add a City", Toast.LENGTH_LONG).show();
                }else{
                    Intent newIntent = new Intent(CityDataActivity.this, NoteActivity.class);
                    startActivity(newIntent);
                    return true;
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
