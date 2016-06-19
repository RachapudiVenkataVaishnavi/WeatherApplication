package com.example.vaishnavirachapudi.weatherapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AddNotesActivity extends AppCompatActivity {

    EditText addNotes_editText;
    Cities city;
    Button saveNote_button;
    Forecast forecast;
    DatabaseDataManager manager;
    String noteDate;
    String position;
    ArrayList<Cities> cities;
    static final String CITY="citykey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        addNotes_editText =(EditText)findViewById(R.id.addnotes_editText);
        saveNote_button=(Button)findViewById(R.id.saveNote_button);
        manager = new DatabaseDataManager(this);
        cities= manager.getAllCities();

        forecast=(Forecast)getIntent().getSerializableExtra(ForecastActivity.FORECAST_KEY);
        position=(String)getIntent().getStringExtra(ForecastActivity.POSITION_KEY);
        city=(Cities)getIntent().getSerializableExtra(ForecastActivity.CITY_KEY);
        Log.d("demo", "in hourly>>>" + forecast.toString());
        Log.d("demo", "position>>>" + position);
        Log.d("demo","city"+city.toString());



        noteDate=(String)getIntent().getStringExtra(ForecastActivity.NOTE_DATE_KEY);;
        Log.d("demo","note date key"+noteDate);


        saveNote_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addNotes_editText.getText().toString() != null || addNotes_editText.getText().toString() != "")

                {
                    String noteText = addNotes_editText.getText().toString();

                    String cityKey = manager.getCityKey(city.getCityName());

                    Notes note = new Notes(cityKey, noteDate, noteText);
                    manager.insertIntoNotesTable(note);

                    Intent intent = new Intent(AddNotesActivity.this, CityDataActivity.class);

                    intent.putExtra(ForecastActivity.POSITION_KEY, position);
                    intent.putExtra(CITY, city);
                    setResult(RESULT_OK, intent);
                    startActivity(intent);

                    finish();


                }

            }
        });

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

                Intent intent = new Intent(AddNotesActivity.this,AddCityActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_clear:

                manager.deleteAll();
                manager.deleteAllNotes();
                Intent intent1 = new Intent(AddNotesActivity.this, MainActivity.class);
                startActivity(intent1);


                return true;
            case R.id.action_view:
                if(cities.size()<1){
                    Toast.makeText(getBaseContext(), "Please Add a City", Toast.LENGTH_LONG).show();
                }else{
                    Intent newIntent = new Intent(AddNotesActivity.this, NoteActivity.class);
                    startActivity(newIntent);
                    return true;
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
