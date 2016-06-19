package com.example.vaishnavirachapudi.weatherapplication;

import java.io.Serializable;

/**
 * Created by vaishnavirachapudi on 3/17/16.
 */
public class Forecast implements Serializable {


    String Date;
    String Hightemp;
    String lowTemp;
    String clouds;
    String iconUrl;
    String maxwindSpeed;
    String windDirection;
    String avghumidity;

    public Forecast() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHightemp() {
        return Hightemp;
    }

    public void setHightemp(String hightemp) {
        Hightemp = hightemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMaxwindSpeed() {
        return maxwindSpeed;
    }

    public void setMaxwindSpeed(String maxwindSpeed) {
        this.maxwindSpeed = maxwindSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getAvghumidity() {
        return avghumidity;
    }

    public void setAvghumidity(String avghumidity) {
        this.avghumidity = avghumidity;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "Date='" + Date + '\'' +
                ", Hightemp='" + Hightemp + '\'' +
                ", lowTemp='" + lowTemp + '\'' +
                ", clouds='" + clouds + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", maxwindSpeed='" + maxwindSpeed + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", avghumidity='" + avghumidity + '\'' +
                '}';
    }
}
