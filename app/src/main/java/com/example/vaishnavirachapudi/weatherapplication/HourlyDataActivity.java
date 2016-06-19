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

public class HourlyDataActivity extends  AppCompatActivity {
    Cities city;

    String url="http://api.wunderground.com/api/ba05bd3bcdbeaed5/hourly/q/state_initial/city_name.xml";

    TextView currentLocation_textView;
    ListView hourlyData_listView;
    ProgressDialog progress;
    ArrayList<Cities> cities;
    DatabaseDataManager dm;

    public final static String LIST_KEY = "list";
    public final static String LOCATION_KEY = "location";
    public final static String INDEX_KEY = "index";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_data);
        city=(Cities)getIntent().getSerializableExtra(MainActivity.LOCATION_KEY);

        dm = new DatabaseDataManager(this);
        cities= dm.getAllCities();
        currentLocation_textView =(TextView) findViewById(R.id.currentLocation_textView);
        currentLocation_textView.setText("Current Location:"+""+city.getCityName()+","+city.getStateInitial());

        hourlyData_listView =(ListView)findViewById(R.id.hourlyData_listView);


        url="http://api.wunderground.com/api/ba05bd3bcdbeaed5/hourly/q/"+city.getStateInitial()+"/"+city.getCityName()+".xml";
        url=url.replaceAll("\\s+","");

        Log.d("Demo",url);
        new getHourlyDetails().execute(url);


    }


    private class getHourlyDetails extends AsyncTask<String , Void , ArrayList<Weather>> {
               @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(HourlyDataActivity.this);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setCancelable(false);
            progress.setMessage("Loading hourly data");
            progress.show();
        }

        @Override
        protected ArrayList<Weather> doInBackground(String... params) {
            InputStream in = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if(isConnected()){


                    in = conn.getInputStream();
                    return WeatherParser.WeatherPullParser.parseHourlyDetails(in);
                }else{
                    Toast.makeText(HourlyDataActivity.this, "Not CONNECTED", Toast.LENGTH_LONG).show();
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
        protected void onPostExecute(final ArrayList<Weather> hourlyDetails) {
            progress.dismiss();


            WeatherAdapter adapter = new WeatherAdapter(HourlyDataActivity.this, hourlyDetails);
            hourlyData_listView.setAdapter(adapter);
            hourlyData_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(HourlyDataActivity.this, DetailsActivity.class);
                    intent.putExtra(LIST_KEY, hourlyDetails);
                    intent.putExtra(LOCATION_KEY, currentLocation_textView.getText());
                    intent.putExtra(INDEX_KEY, position);
                    startActivity(intent);
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

                Intent intent = new Intent(HourlyDataActivity.this,AddCityActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_clear:

                dm.deleteAll();
                dm.deleteAllNotes();
                Intent intent1 = new Intent(HourlyDataActivity.this, MainActivity.class);
                startActivity(intent1);


                return true;
            case R.id.action_view:
                if(cities.size()<1){
                    Toast.makeText(getBaseContext(), "Please Add a City", Toast.LENGTH_LONG).show();
                }else{
                    Intent newIntent = new Intent(HourlyDataActivity.this, NoteActivity.class);
                    startActivity(newIntent);
                    return true;
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

