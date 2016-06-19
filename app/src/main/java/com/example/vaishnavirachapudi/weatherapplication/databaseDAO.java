package com.example.vaishnavirachapudi.weatherapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaishnavirachapudi on 3/19/16.
 */
public class databaseDAO {
    private SQLiteDatabase db;

    public databaseDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public long insertIntoCitiesTable(Cities city) {
        ContentValues values = new ContentValues();
        values.put(Tables.TAB1_COLUMN_CITYKEY, city.getCityKey());
        values.put(Tables.TAB1_COLUMN_CITYNAME, city.getCityName());
        values.put(Tables.TAB1_COLUMN_STATE, city.getStateInitial());

        long result= db.insert(Tables.TABLE_NAME1, null, values);
        return result;
    }


    public boolean deleteNote(String cityKey , String date){
        return db.delete(Tables.TABLE_NAME2, Tables.TAB2_COLUMN_CITYKEY + "=? AND "+Tables.TAB2_COLUMN_CITYDATE+"=?", new String[]{cityKey , date})>0;
    }

    public long insertIntoNotesTable(Notes note) {
        ContentValues values = new ContentValues();
        values.put(Tables.TAB2_COLUMN_CITYKEY, note.getCitykey());
        values.put(Tables.TAB2_COLUMN_CITYDATE, note.getDate());
        values.put(Tables.TAB2_COLUMN_NOTE, note.getNote());

        long result= db.insert(Tables.TABLE_NAME2, null, values);
        return result;
    }


    public ArrayList<Cities> getAllCities() {
        ArrayList<Cities> Cities = new ArrayList<Cities>();
        Cursor c = db.query(Tables.TABLE_NAME1, new String[]{
                Tables.TAB1_COLUMN_CITYKEY, Tables.TAB1_COLUMN_CITYNAME, Tables.TAB1_COLUMN_STATE
        }, null, null, null, null, null);
        if(c !=null && c.moveToFirst())
        {
            do{
                Cities city =BuildCitiesFromCursor(c);
                if(city!=null)
                {
                    Cities.add(city);
                }

            }while(c.moveToNext());
            {
                if(!c.isClosed())
                {
                    c.close();
                }

            }

        }

        return Cities;
    }


    private Cities BuildCitiesFromCursor(Cursor c) {
        Cities city = null;
        if (c != null) {
            city = new Cities();
            city.setCityKey(c.getString(0));
            city.setCityName(c.getString(1));
            city.setStateInitial(c.getString(2));

        }

        return city;

    }

    public String getCityKey(String cityName)
    {

        Cities city = null;
        Cursor c= db.query(true, Tables.TABLE_NAME1, new String[]{
                Tables.TAB1_COLUMN_CITYKEY, Tables.TAB1_COLUMN_CITYNAME, Tables.TAB1_COLUMN_STATE
        }, Tables.TAB1_COLUMN_CITYNAME + "=?", new String[]{cityName}, null, null, null, null, null);

        Log.d("cursor",c.toString());
        if(c!=null && c.moveToNext())
        {
            city=BuildCitiesFromCursor(c);
            if(!c.isClosed())
            {
                c.close();
            }

        }

        return city.getCityKey();
    }


    public Notes getNote(String city , String date){
        Notes newNote =null;
        Cursor c = db.query(true , Tables.TABLE_NAME2 , new String[]{Tables.TAB2_COLUMN_CITYKEY,Tables.TAB2_COLUMN_CITYDATE,Tables.TAB2_COLUMN_NOTE},Tables.TAB2_COLUMN_CITYKEY + "=? AND "+Tables.TAB2_COLUMN_CITYDATE+"=?", new String[]{city , date},null,null,null,null,null);

        if(c!=null && c.moveToFirst()){
            newNote = buildNoteFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }
        return newNote;
    }



    public ArrayList<Notes> getAllNotes() {
        ArrayList<Notes> Notes = new ArrayList<Notes>();

        String query = "SELECT * FROM " +Tables.TABLE_NAME2;

        Cursor c =db.rawQuery(query, null);

        if(c !=null && c.moveToFirst())
        {
            do {
                Notes note = new Notes();
                note.setCitykey(c.getString(0));
                note.setDate(c.getString(1));
                note.setNote(c.getString(2));

                Notes.add(note);
            }while(c.moveToNext());

        }

        return Notes;
    }


    public boolean delete(String cityKey){
        return db.delete(Tables.TABLE_NAME1, Tables.TAB1_COLUMN_CITYKEY + "=?", new String[]{cityKey})>0;
    }

    public boolean deleteAll(){

        return db.delete(Tables.TABLE_NAME1 ,null,null)>0;
    }
    public boolean deleteAllNotes(){

        return db.delete(Tables.TABLE_NAME2 ,null,null)>0;
    }

    public Notes buildNoteFromCursor(Cursor c){

        Notes note =null;
        if(c!=null){
            note = new Notes();
            note.setCitykey(c.getString(0));
            note.setDate(c.getString(1));
            note.setNote(c.getString(2));
        }
        return note;
    }


    }
