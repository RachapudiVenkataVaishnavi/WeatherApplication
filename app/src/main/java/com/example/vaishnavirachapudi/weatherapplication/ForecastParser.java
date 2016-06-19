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
public class ForecastParser {
    static public class ForecastPullParser {
        static ArrayList<Forecast> parseForecastDetails (InputStream in) throws XmlPullParserException, IOException
        {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            Forecast forecast = null;
            ArrayList<Forecast> forecastDetails = new ArrayList<Forecast>();
            int event = parser.getEventType();
            String lookForTemp = "";
            int flag=0;
            while(event!=XmlPullParser.END_DOCUMENT)
            {
                switch(event)
                {
                    case XmlPullParser.START_TAG :
                        if(parser.getName().equals("simpleforecast")){
                            flag = 1;
                        }
                        if(parser.getName().equals("forecastday") && flag==1)
                        {
                             forecast = new Forecast();
                            break;
                        }
                        if(parser.getName().equals("pretty"))
                        {
                            String Date = parser.nextText();
                            if(forecast != null && Date != null){
                                forecast.setDate(Date);
                            }
                            break;
                        }
                        if(parser.getName().equals("high"))
                        {
                            lookForTemp = "high";
                            break;
                        }
                        if(parser.getName().equals("low"))
                        {
                            lookForTemp = "low";
                            break;
                        }
                        if(parser.getName().equals("conditions"))
                        {
                            String clouds = parser.nextText();
                            if(forecast != null && clouds != null){
                                forecast.setClouds(clouds);
                            }
                            break;
                        }

                        if(parser.getName().equals("icon_url"))
                        {
                            String iconUrl = parser.nextText();
                            if(forecast != null && iconUrl != null){
                                forecast.setIconUrl(iconUrl);
                            }
                            break;
                        }
                        if(parser.getName().equals("maxwind"))
                        {
                            lookForTemp = "maxwind";
                            break;
                        }
                        if(parser.getName().equals("dir"))
                        {
                            String windDirection = parser.nextText();
                            if(forecast != null && windDirection != null){
                                forecast.setWindDirection(windDirection);
                            }
                            break;
                        }
                        if(parser.getName().equals("avehumidity"))
                        {
                            String avghumidity = parser.nextText();
                            if(forecast != null && avghumidity != null){
                                forecast.setAvghumidity(avghumidity);
                            }
                            break;
                        }
                        if (parser.getName().equals("fahrenheit")) {
                            switch (lookForTemp) {
                                case "high":
                                    String highTemp = parser.nextText();
                                    if (forecast != null && highTemp != null) {
                                        forecast.setHightemp(highTemp);
                                    }
                                    lookForTemp = "";
                                    break;
                                case "low":
                                    String lowTemp = parser.nextText();
                                    if (forecast != null && lowTemp != null) {
                                        forecast.setLowTemp(lowTemp);
                                    }
                                    lookForTemp = "";
                                    break;
                                default:
                                    break;
                            }
                            break;
                        }
                        if (parser.getName().equals("mph")) {
                            switch (lookForTemp) {
                                case "maxwind":
                                    String maxwind = parser.nextText();
                                    if (forecast != null && maxwind != null) {
                                        forecast.setMaxwindSpeed(maxwind);
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
                        if (parser.getName().equals("forecastday")  && forecast != null) {

                            forecastDetails.add(forecast);
                            forecast = null;
                        }
                        break;
                    default:
                        break;
                }

                event = parser.next();
            }


            return  forecastDetails;
        }
    }
}
