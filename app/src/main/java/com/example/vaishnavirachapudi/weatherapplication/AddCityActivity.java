package com.example.vaishnavirachapudi.weatherapplication;


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

import android.widget.Button;
import android.widget.EditText;
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

public class AddCityActivity extends AppCompatActivity  {
    EditText editText_cityName;
    EditText editText_stateIntial;
    Button button_save;
    String cityName;
    String stateInitial;
    String cityKey;
    Cities city;

    Boolean Flag = false;
    DatabaseDataManager dm;


    String url="http://api.wunderground.com/api/ba05bd3bcdbeaed5/hourly/q/state_initial/city_name.xml";
    TextView main_textView;
    ListView main_listView;

    ArrayList<Cities> cities;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        editText_cityName =(EditText)findViewById(R.id.editText_cityName);
        editText_stateIntial=(EditText)findViewById(R.id.editText_stateInitial);
        button_save=(Button)findViewById(R.id.button_save);


        dm = new DatabaseDataManager(this);
        main_textView =(TextView)findViewById(R.id.main_textView);
        main_listView=(ListView)findViewById(R.id.main_listView);
        cities= dm.getAllCities();

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cityName = editText_cityName.getText().toString().toLowerCase();
                stateInitial = editText_stateIntial.getText().toString().toUpperCase();

                if (cityName == "" || cityName == null) {
                    Toast.makeText(getApplicationContext(), "Please Enter City Name", Toast.LENGTH_LONG).show();
                } else if (stateInitial == "" || stateInitial == null) {

                    Toast.makeText(getApplicationContext(), "Please Enter State Initial", Toast.LENGTH_LONG).show();

                } else {
                    cityName = resetCityName(cityName);


                    url="http://api.wunderground.com/api/ba05bd3bcdbeaed5/hourly/q/"+stateInitial+"/"+cityName+".xml";
                    url=url.replaceAll("\\s+","");
                    Log.d("demo",url);
                    new getHourlyDetails().execute(url);




                }

            }
        });




    }

    private String resetCityName(String cityName){
        String[] s = cityName.trim().split(" ");
        String newCityName="";
        for(int i=0; i<s.length;i++){
            s[i] = s[i].replaceFirst(s[i].substring(0,1) , s[i].substring(0,1).toUpperCase());
            newCityName += s[i]+" ";
        }

        return newCityName;
    }



    private class getHourlyDetails extends AsyncTask<String , Void , ArrayList<Weather>> {



        @Override
        protected ArrayList<Weather> doInBackground(String... params) {
            InputStream in = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (isConnected()) {


                    in = conn.getInputStream();
                    return WeatherParser.WeatherPullParser.parseHourlyDetails(in);
                } else {
                    Toast.makeText(AddCityActivity.this, "Not CONNECTED", Toast.LENGTH_LONG).show();
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

                if (hourlyDetails != null) {
                    Flag = true;


                }



            if(Flag==true ) {
                if(hourlyDetails.size()!=0) {


                    cityKey = cityName + "," + stateInitial;

                    city = new Cities(cityKey, cityName, stateInitial);



                    dm.insertIntoCitiesTable(city);

                    Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }else
            {
                Toast.makeText(AddCityActivity.this, "Please Enter Valid City and State Intial", Toast.LENGTH_LONG).show();
            }

            }

        }

        private boolean isConnected() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo.isConnected() && networkInfo != null) {


                return true;
            } else {


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

                Intent intent = new Intent(AddCityActivity.this,AddCityActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_clear:

                dm.deleteAll();
                dm.deleteAllNotes();
                Intent intent1 = new Intent(AddCityActivity.this, MainActivity.class);
                startActivity(intent1);


                return true;
            case R.id.action_view:
                if(cities.size()<1){
                    Toast.makeText(getBaseContext(), "Please Add a City", Toast.LENGTH_LONG).show();
                }else{
                    Intent newIntent = new Intent(AddCityActivity.this, NoteActivity.class);
                    startActivity(newIntent);
                    return true;
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    }


