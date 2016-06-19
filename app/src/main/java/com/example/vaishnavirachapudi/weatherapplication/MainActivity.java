package com.example.vaishnavirachapudi.weatherapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseDataManager dm;
    TextView main_textView;
    ListView main_listView;
    ArrayAdapter<Cities> adapter;
    ArrayList<Cities> cities;
    public final static String LOCATION_KEY = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dm = new DatabaseDataManager(this);
        main_textView =(TextView)findViewById(R.id.main_textView);
        main_listView=(ListView)findViewById(R.id.main_listView);
        cities= dm.getAllCities();

        if(cities.size()<1){

            main_textView.setVisibility(View.VISIBLE);
            main_listView.setVisibility(View.INVISIBLE);
        }else{
            adapter = new CitiesListAdapter(MainActivity.this , cities);
            main_listView.setAdapter(adapter);
            main_textView.setVisibility(View.INVISIBLE);
            main_listView.setVisibility(View.VISIBLE);

            main_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(MainActivity.this, CityDataActivity.class);
                    intent.putExtra(LOCATION_KEY, cities.get(position));
                    startActivity(intent);


                }
            });

            main_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView city = (TextView) view.findViewById(R.id.textView_city);
                    String cityKey = city.getText().toString();
                    dm.delete(cityKey);
                    ArrayList<Cities> cities = dm.getAllCities();
                    if(cities.size()<1){

                        main_listView.setVisibility(View.INVISIBLE);
                        main_textView.setVisibility(View.VISIBLE);
                    }else{
                        adapter = new CitiesListAdapter(MainActivity.this , cities);
                        adapter.setNotifyOnChange(true);
                        main_listView.setAdapter(adapter);
                    }
                    return true;
                }
            });
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

                Intent intent = new Intent(MainActivity.this,AddCityActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_clear:

                dm.deleteAll();
                dm.deleteAllNotes();

                main_textView.setVisibility(View.VISIBLE);
                main_listView.setVisibility(View.INVISIBLE);

                return true;
            case R.id.action_view:
                if(cities.size()<1){
                    Toast.makeText(getBaseContext(), "Please Add a City", Toast.LENGTH_LONG).show();
                }else{
                    Intent newIntent = new Intent(MainActivity.this, NoteActivity.class);
                    startActivity(newIntent);
                    return true;
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
