package com.example.vaishnavirachapudi.weatherapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {
    ArrayList<Notes> notes;
    DatabaseDataManager dm;
    ListView listView;
    ViewNoteAdapter adapter;
    ArrayList<Cities> cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        dm = new DatabaseDataManager(this);
        cities= dm.getAllCities();
        listView = (ListView) findViewById(R.id.viewNote_listView);
        notes = dm.getAllNotes();

        if (notes != null) {
            adapter = new ViewNoteAdapter(this, notes);
            listView.setAdapter(adapter);
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

                Intent intent = new Intent(NoteActivity.this,AddCityActivity.class);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_clear:

                dm.deleteAll();
                dm.deleteAllNotes();
                Intent intent1 = new Intent(NoteActivity.this, MainActivity.class);
                startActivity(intent1);


                return true;
            case R.id.action_view:
                if(cities.size()<1){
                    Toast.makeText(getBaseContext(), "Please Add a City", Toast.LENGTH_LONG).show();
                }else{
                    Intent newIntent = new Intent(NoteActivity.this, NoteActivity.class);
                    startActivity(newIntent);
                    return true;
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
