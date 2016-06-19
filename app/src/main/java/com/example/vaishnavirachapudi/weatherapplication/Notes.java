package com.example.vaishnavirachapudi.weatherapplication;

/**
 * Created by vaishnavirachapudi on 3/19/16.
 */
public class Notes {
    String citykey;
    String date;
    String note;

    public Notes(String citykey, String date, String note) {
        this.citykey = citykey;
        this.date = date;
        this.note = note;
    }

    public Notes()
    {

    }
    public String getCitykey() {
        return citykey;
    }

    public void setCitykey(String citykey) {
        this.citykey = citykey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "citykey='" + citykey + '\'' +
                ", date='" + date + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
