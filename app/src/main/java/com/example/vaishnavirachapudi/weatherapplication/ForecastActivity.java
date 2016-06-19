package com.example.vaishnavirachapudi.weatherapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {

    Cities city;
    String url="http://api.wunderground.com/api/ba05bd3bcdbeaed5/forecast10day/q/state_initial/city_name.xml";
    TextView currentLocation_textView;
    ListView forecastData_listView;
    DatabaseDataManager manager;
    ProgressDialog progress;
    static final String FORECAST_KEY="forecastKey";
    static final String POSITION_KEY="position";
    static final String CITY_KEY="citykey";
    static final String NOTE_DATE_KEY="notedate";
    public final static int REQUEST_CODE = 101;
    ArrayList<Cities> cities;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        city=(Cities)getIntent().getSerializableExtra(MainActivity.LOCATION_KEY);
        manager = new DatabaseDataManager(this);
        cities= manager.getAllCities();
        currentLocation_textView =(TextView) findViewById(R.id.currentLocation_textView);
        currentLocation_textView.setText("Current Location:"+""+city.getCityName()+","+city.getStateInitial());

        forecastData_listView =(ListView)findViewById(R.id.forecastData_listView);

        url="http://api.wunderground.com/api/ba05bd3bcdbeaed5/forecast10day/q/"+city.getStateInitial()+"/"+city.getCityName()+".xml";
        url=url.replaceAll("\\s+","");


        Log.d("Demo",url);
        new getForecastDetails().execute(url);

    }


    private class getForecastDetails extends AsyncTask<String , Void , ArrayList<Forecast>> {
        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(ForecastActivity.this);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.setMessage("Loading Forecast data");
            progress.show();
        }

        @Override
        protected ArrayList<Forecast> doInBackground(String... params) {
            InputStream in = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if(isConnected()){


                    in = conn.getInputStream();
                    return ForecastParser.ForecastPullParser.parseForecastDetails(in);
                }else{
                    Toast.makeText(ForecastActivity.this, "Not CONNECTED", Toast.LENGTH_LONG).show();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(final ArrayList<Forecast> forecastDetails) {
            progress.dismiss();


            ForecastAdapter adapter = new ForecastAdapter(ForecastActivity.this, forecastDetails , city);
            forecastData_listView.setAdapter(adapter);
            forecastData_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            forecastData_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("demo", "forecastDetails.get(position)>>>>>>>" + forecastDetails.get(position).toString());

                    Notes note = manager.getNote(city.getCityKey(), forecastDetails.get(position).getDate());
                    if (note != null) {
                        manager.deleteNote(note.getCitykey(), note.getDate());
                        Toast.makeText(ForecastActivity.this, "Note has been Removed", Toast.LENGTH_LONG).show();
                        ForecastAdapter adapter = new ForecastAdapter(ForecastActivity.this, forecastDetails , city);
                        forecastData_listView.setAdapter(adapter);
                        adapter.setNotifyOnChange(true);
                    } else {



                    Intent intent = new Intent(ForecastActivity.this, AddNotesActivity.class);
                    intent.putExtra(FORECAST_KEY, forecastDetails.get(position));
                    intent.putExtra(POSITION_KEY, String.valueOf(position));
                    intent.putExtra(CITY_KEY, city);
                    intent.putExtra(NOTE_DATE_KEY, forecastDetails.get(position).getDate());
                    startActivityForResult(intent, REQUEST_CODE);

                    startActivity(intent);

                }
                    return false;
                }
            });
        }

    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo.isConnected() && networkInfo !=null){

            Log.d("demo","connected");
            return true;
        }else{

            Log.d("demo","not connected");
            return false;
        }

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

                Intent intent = new Intent(ForecastActivity.this,AddCityActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_clear:

                manager.deleteAll();
                manager.deleteAllNotes();
                Intent intent1 = new Intent(ForecastActivity.this, MainActivity.class);
                startActivity(intent1);


                return true;
            case R.id.action_view:
                if(cities.size()<1){
                    Toast.makeText(getBaseContext(), "Please Add a City", Toast.LENGTH_LONG).show();
                }else{
                    Intent newIntent = new Intent(ForecastActivity.this, NoteActivity.class);
                    startActivity(newIntent);
                    return true;
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
