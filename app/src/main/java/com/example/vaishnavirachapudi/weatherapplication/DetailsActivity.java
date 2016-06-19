package com.example.vaishnavirachapudi.weatherapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    TextView currentLocation, currentTemp, condition, maxTemp, minTemp, feelsLike, humidity, dewPoint, pressure, clouds, wind;
    ImageView icon;
    ImageButton next, prev;
    ArrayList<Weather> weatherHourly;
    int currentDisplayIndex, lastIndex;
    String currentLocationString;
    ArrayList<Cities> cities;
    DatabaseDataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        dm = new DatabaseDataManager(this);
        cities= dm.getAllCities();

        weatherHourly = (ArrayList<Weather>)getIntent().getSerializableExtra(HourlyDataActivity.LIST_KEY);
        currentDisplayIndex = getIntent().getIntExtra(HourlyDataActivity.INDEX_KEY, 0);
        lastIndex = (weatherHourly.size() - 1);
        currentLocationString = (getIntent().getStringExtra(HourlyDataActivity.LOCATION_KEY));

        currentLocation = (TextView)findViewById(R.id.textView_detailsCurrLoc);
        currentTemp = (TextView)findViewById(R.id.textView_detailsCurrTemp);
        condition = (TextView)findViewById(R.id.textView_detailsCondition);
        maxTemp = (TextView)findViewById(R.id.textView_detailsMaxTemp);
        minTemp = (TextView)findViewById(R.id.textView_detailsMinTemp);
        feelsLike = (TextView)findViewById(R.id.textView_detailsFeelsLike);
        humidity = (TextView)findViewById(R.id.textView_detailsHumidity);
        dewPoint = (TextView)findViewById(R.id.textView_detailsDewpoint);
        pressure = (TextView)findViewById(R.id.textView_detailsPressure);
        clouds = (TextView)findViewById(R.id.textView_detailsClouds);
        wind = (TextView)findViewById(R.id.textView_detailsWinds);

        icon = (ImageView)findViewById(R.id.imageView_detailsIcon);

        displayDetails(weatherHourly.get(currentDisplayIndex));

        next = (ImageButton)findViewById(R.id.imageButton_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentDisplayIndex < lastIndex) {
                    currentDisplayIndex++;
                    displayDetails(weatherHourly.get(currentDisplayIndex));
                } else {
                    currentDisplayIndex = 0;
                    displayDetails(weatherHourly.get(currentDisplayIndex));
                }
            }
        });

        prev = (ImageButton)findViewById(R.id.imageButton_prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentDisplayIndex > 0) {
                    currentDisplayIndex--;
                    displayDetails(weatherHourly.get(currentDisplayIndex));
                } else {
                    currentDisplayIndex = lastIndex;
                    displayDetails(weatherHourly.get(currentDisplayIndex));
                }
            }
        });
    }
    private void displayDetails(Weather w) {
        String currentLocationStringWithTime = currentLocationString + " (" + w.getTime() + ")";
        currentLocation.setText(currentLocationStringWithTime);

        Picasso.with(DetailsActivity.this).load(w.getIconUrl()).into(icon);

        String tempString = w.getTemperature() + WeatherAdapter.DEGREE + "F";
        currentTemp.setText(tempString);

        condition.setText(w.getClimateType());

        String maxTempString = "Max Temperature: " + w.getMaximumTemp() + " Fahrenheit";
        maxTemp.setText(maxTempString);

        String minTempString = "Min Temperature: " + w.getMinimumTemp() + " Fahrenheit";
        minTemp.setText(minTempString);

        String feelsLikeString = "Feels Like: " + w.getFeelsLike();
        feelsLike.setText(feelsLikeString);

        String humidityString = "Humidity: " + w.getHumidity();
        humidity.setText(humidityString);

        String dewpointString = "Dewpoint: " + w.getDewpoint();
        dewPoint.setText(dewpointString);

        String pressureString = "Pressure: " + w.getPressure();
        pressure.setText(pressureString);

        String cloudsString = "Clouds: " + w.getClouds();
        clouds.setText(cloudsString);

        String windString = "Winds: " + w.getWindSpeed() + ", " + w.getWindDirection();
        wind.setText(windString);
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

                Intent intent = new Intent(DetailsActivity.this,AddCityActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_clear:

                dm.deleteAll();
                dm.deleteAllNotes();
                Intent intent1 = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent1);


                return true;
            case R.id.action_view:
                if(cities.size()<1){
                    Toast.makeText(getBaseContext(), "Please Add a City", Toast.LENGTH_LONG).show();
                }else{
                    Intent newIntent = new Intent(DetailsActivity.this, NoteActivity.class);
                    startActivity(newIntent);
                    return true;
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
