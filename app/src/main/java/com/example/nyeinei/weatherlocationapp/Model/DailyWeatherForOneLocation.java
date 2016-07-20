package com.example.nyeinei.weatherlocationapp.Model;

import android.util.Log;

import com.example.nyeinei.weatherlocationapp.Helper.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

/**
 * Created by NyeinEi on 7/14/2016.
 */
public class DailyWeatherForOneLocation {
    private City city;
    private List<DailyWeatherForecastList> weatherList = new ArrayList<DailyWeatherForecastList>();
    public static String baseURL = "http://api.openweathermap.org/data/2.5/forecast?q=";//Singapore,SG&appid=e446e2bc4dc218a04d3c9a995fcec373"
    public static String apiKey = "e446e2bc4dc218a04d3c9a995fcec373";

    public DailyWeatherForOneLocation(City city, List<DailyWeatherForecastList> weatherList) {
        this.city = city;
        this.weatherList = weatherList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<DailyWeatherForecastList> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<DailyWeatherForecastList> weatherList) {
        this.weatherList = weatherList;
    }

    public static DailyWeatherForOneLocation getDailyWeatherForecast(double latitude,double longitude){
        Log.e("Function","getDaily");
        String url_string = String.format("http://api.openweathermap.org/data/2.5/forecast/daily?lat=%s&lon=%s&appid=%s",latitude,longitude,apiKey);
        try {
            JSONObject jsonObject = JSONParser.getJSONFromUrl(url_string);
            if(jsonObject!=null){
                Weather weather = null;
                Long dt;
                Temperature temperature = null;
                double pressure;
                int humidity;
                double speed;
                int deg;
                int clouds;

                DailyWeatherForOneLocation dwf = null;

                //getting city from json object
                JSONObject jo_city = jsonObject.getJSONObject("city");
                //getting coordinate from json object
                JSONObject jo_coor = jo_city.getJSONObject("coord");
                City city = new City(jo_city.getString("name"),new Coordinate(jo_coor.getDouble("lon"),jo_coor.getDouble("lat")),jo_city.getString("country"));

                //getting weatherlist from json object
                JSONArray jr_weatherList = jsonObject.getJSONArray("list");
                Log.e("Daily Weather List",jr_weatherList+"");

                List<DailyWeatherForecastList> weather_list = new ArrayList<DailyWeatherForecastList>();
                for(int i=0;i<jr_weatherList.length();i++) {
                    JSONObject jo_weatherforecast = jr_weatherList.getJSONObject(i);

                    //getting weather obj from jsonobject
                    JSONArray jr_weathers = jo_weatherforecast.getJSONArray("weather");
                    for (int j = 0; j < jr_weathers.length(); j++) {
                        JSONObject o = jr_weathers.getJSONObject(j);
                        weather = new Weather(o.getString("main"), o.getString("description"), o.getString("icon"));
                    }

                    //getting dt from json object
                    dt = jo_weatherforecast.getLong("dt");

                    //getting pressure from json object
                    pressure = jo_weatherforecast.getDouble("pressure");

                    //getting humidity from json object
                    humidity = jo_weatherforecast.getInt("humidity");

                    speed = jo_weatherforecast.getDouble("speed");

                    deg = jo_weatherforecast.getInt("deg");

                    clouds = jo_weatherforecast.getInt("clouds");

                    //getting temperature from jsonobject
                    JSONObject jo_temp = jo_weatherforecast.getJSONObject("temp");
                    temperature = new Temperature(jo_temp.getDouble("day"), jo_temp.getDouble("min"), jo_temp.getDouble("max"), jo_temp.getDouble("night"), jo_temp.getDouble("eve"), jo_temp.getDouble("morn"));

                    DailyWeatherForecastList dfl = new DailyWeatherForecastList(temperature, dt, pressure, humidity, weather, speed, deg, clouds);
                    weather_list.add(dfl);
                }
                dwf = new DailyWeatherForOneLocation(city,weather_list);
                return dwf;
            }else {
                return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }
}
