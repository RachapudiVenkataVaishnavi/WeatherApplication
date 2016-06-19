package com.example.vaishnavirachapudi.weatherapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by vaishnavirachapudi on 3/17/16.
 */
public class WeatherParser {
    static public class WeatherPullParser{
        static ArrayList<Weather> parseHourlyDetails(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            Weather weather = null;
            ArrayList<Weather> hourlyDetails = new ArrayList<Weather>();
            int event = parser.getEventType();
            String lookForTemp = "";
            int tempHigh = -1000;
            int tempLow = 1000;
            while(event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("forecast")) {
                            weather = new Weather();
                            break;
                        }
                        if (parser.getName().equals("civil")) {
                            String time = parser.nextText();
                            if(weather != null && time != null){
                                weather.setTime(time);
                            }
                            break;
                        }
                        if (parser.getName().equals("temp")) {
                            lookForTemp = "temp";
                            break;
                        }
                        if (parser.getName().equals("dewpoint")) {
                            lookForTemp = "dewpoint";
                            break;
                        }
                        if (parser.getName().equals("condition")) {
                            String clouds = parser.nextText();
                            if(weather != null && clouds != null){
                                weather.setClouds(clouds);
                            }
                            break;
                        }
                        if (parser.getName().equals("icon_url")) {
                            String iconUrl = parser.nextText();
                            if(weather != null && iconUrl != null){
                                weather.setIconUrl(iconUrl);
                            }
                            break;
                        }
                        if (parser.getName().equals("wspd")) {
                            lookForTemp = "wspd";
                            break;
                        }
                        if (parser.getName().equals("dir")) {
                            String windDirection = parser.nextText();
                            if(weather != null && windDirection != null){
                                weather.setWindDirection(windDirection);
                            }
                            break;
                        }
                        if (parser.getName().equals("wx")) {
                            String climateType = parser.nextText();
                            if(weather != null && climateType != null){
                                weather.setClimateType(climateType);
                            }
                            break;
                        }
                        if (parser.getName().equals("humidity")) {
                            String humidity = parser.nextText();
                            if(weather != null && humidity != null){
                                weather.setHumidity(humidity);
                            }
                            break;
                        }
                        if (parser.getName().equals("feelslike")) {
                            lookForTemp = "feelslike";
                            break;
                        }
                        if (parser.getName().equals("mslp")) {
                            lookForTemp = "mslp";
                            break;
                        }
                        if (parser.getName().equals("english")) {
                            switch (lookForTemp) {
                                case "temp":
                                    String temp = parser.nextText();
                                    if(weather != null && temp != null){
                                        weather.setTemperature(temp);
                                    }
                                    lookForTemp = "";
                                    break;
                                case "dewpoint":
                                    String dewpoint = parser.nextText();
                                    if(weather != null && dewpoint != null){
                                        weather.setDewpoint(dewpoint);
                                    }
                                    lookForTemp = "";
                                    break;
                                case "wspd":
                                    String windSpeed = parser.nextText();
                                    if(weather != null && windSpeed != null){
                                        weather.setWindSpeed(windSpeed);
                                    }
                                    lookForTemp = "";
                                    break;
                                case "feelslike":
                                    String feelsLike = parser.nextText();
                                    if(weather != null && feelsLike != null){
                                        weather.setFeelsLike(feelsLike);
                                    }
                                    lookForTemp = "";
                                    break;
                                case "mslp":
                                    String pressure = parser.nextText();
                                    if(weather != null && pressure != null){
                                        weather.setPressure(pressure);
                                    }
                                    lookForTemp = "";
                                    break;
                                default:
                                    break;
                            }
                            break;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("forecast")  && weather != null) {
                            int temp = Integer.parseInt(weather.getTemperature());
                            if (temp > tempHigh) {
                                tempHigh = temp;
                            }
                            if (temp < tempLow) {
                                tempLow = temp;
                            }
                            hourlyDetails.add(weather);
                            weather = null;
                        }
                        break;
                    default:
                        break;
                }
                event = parser.next();
            }
            for (Weather w : hourlyDetails) {
                w.setMaximumTemp("" + tempHigh);
                w.setMinimumTemp("" + tempLow);

            }
            return hourlyDetails;
        }
    }
}

