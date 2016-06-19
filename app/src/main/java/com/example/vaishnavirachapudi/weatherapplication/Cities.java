package com.example.vaishnavirachapudi.weatherapplication;

import java.io.Serializable;

/**
 * Created by vaishnavirachapudi on 5/23/16.
 */
public class Cities implements Serializable {
    String cityKey;
    String cityName;
    String stateInitial;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    String temperature;

    public Cities() {
    }

    public Cities(String cityKey, String cityName, String stateInitial) {
        this.cityKey = cityKey;
        this.cityName = cityName;
        this.stateInitial = stateInitial;
    }

    public String getCityKey() {
        return cityKey;
    }

    public void setCityKey(String cityKey) {
        this.cityKey = cityKey;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateInitial() {
        return stateInitial;
    }

    public void setStateInitial(String stateInitial) {
        this.stateInitial = stateInitial;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityKey='" + cityKey + '\'' +
                ", cityName='" + cityName + '\'' +
                ", stateInitial='" + stateInitial + '\'' +
                '}';
    }
}
