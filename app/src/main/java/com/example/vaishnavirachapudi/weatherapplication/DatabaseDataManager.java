package com.example.vaishnavirachapudi.weatherapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaishnavirachapudi on 3/19/16.
 */
public class DatabaseDataManager {

    private Context mContext ;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private databaseDAO databaseDAO;

    public DatabaseDataManager(Context mContext) {
        this.mContext = mContext;
        dbOpenHelper = new DatabaseOpenHelper(this.mContext);
        db = dbOpenHelper.getWritableDatabase();
        databaseDAO = new databaseDAO(db);
    }

    public void close()
    {
        if(db!=null)
        {
            db.close();
        }
    }
    public databaseDAO getDatabaseDAO()
    {

        return this.databaseDAO;
    }

    public long insertIntoCitiesTable(Cities city)
    {

        return this.databaseDAO.insertIntoCitiesTable(city);
    }

    public long insertIntoNotesTable(Notes note)
    {

        return this.databaseDAO.insertIntoNotesTable(note);
    }

    public String getCityKey(String cityName)
    {
        return this.databaseDAO.getCityKey(cityName);
    }

        public ArrayList<Cities> getAllCities(){

        return this.databaseDAO.getAllCities();
    }

    public ArrayList<Notes> getAllNotes(){

        return this.databaseDAO.getAllNotes();
    }

    public Notes getNote(String city, String date){

        return this.databaseDAO.getNote(city, date);
    }

    public boolean delete(String cityKey){
        return this.databaseDAO.delete(cityKey);
    }

    public boolean deleteNote(String cityKey , String date){
        return this.databaseDAO.deleteNote(cityKey, date);
    }

    public boolean deleteAll(){

        return this.databaseDAO.deleteAll();
    }

    public boolean deleteAllNotes(){

        return this.databaseDAO.deleteAllNotes();
    }

}


