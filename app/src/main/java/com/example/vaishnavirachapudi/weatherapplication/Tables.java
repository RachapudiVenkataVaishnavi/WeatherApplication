package com.example.vaishnavirachapudi.weatherapplication;

/**
 * Created by vaishnavirachapudi on 5/23/16.
 */
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by vaishnavirachapudi on 3/19/16.
 */
public class Tables {


    static final String TABLE_NAME1 ="cities";
    static final String TAB1_COLUMN_CITYKEY="citykey";
    static final String TAB1_COLUMN_CITYNAME ="cityname";
    static final String TAB1_COLUMN_STATE="state";

    static final String TABLE_NAME2 ="notes";
    static final String TAB2_COLUMN_CITYKEY="citykey";
    static final String TAB2_COLUMN_CITYDATE ="date";
    static final String TAB2_COLUMN_NOTE="note";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb1 = new StringBuilder();

        StringBuilder sb2 = new StringBuilder();

        sb1.append("CREATE TABLE " +TABLE_NAME1 +" (" );
        sb1.append(TAB1_COLUMN_CITYKEY +" text not null, ");
        sb1.append(TAB1_COLUMN_CITYNAME+" text not null, ");
        sb1.append(TAB1_COLUMN_STATE+" text not null); ");

        sb2.append("CREATE TABLE " +TABLE_NAME2 +" (" );
        sb2.append(TAB2_COLUMN_CITYKEY +" text not null, ");
        sb2.append(TAB2_COLUMN_CITYDATE+" text not null, ");
        sb2.append(TAB2_COLUMN_NOTE+" text not null); ");


        try{
            db.execSQL(sb1.toString());
            db.execSQL(sb2.toString());
        } catch(android.database.SQLException ex) {
            ex.printStackTrace();
        }


    }

    static public void onUpgrade(SQLiteDatabase db , int oldVersion , int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        Tables.onCreate(db);
    }



}

