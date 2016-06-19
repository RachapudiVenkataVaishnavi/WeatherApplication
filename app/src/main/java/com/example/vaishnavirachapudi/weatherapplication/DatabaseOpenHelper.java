package com.example.vaishnavirachapudi.weatherapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vaishnavirachapudi on 3/19/16.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME="database.db";
    static final int DB_VERSION=1;

    public DatabaseOpenHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Tables.onCreate(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Tables.onUpgrade(db,oldVersion,newVersion);

    }
}


