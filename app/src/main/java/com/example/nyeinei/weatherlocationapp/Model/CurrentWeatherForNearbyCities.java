package com.example.nyeinei.weatherlocationapp.Model;

import android.util.Log;

import com.example.nyeinei.weatherlocationapp.Helper.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moe Han on 17-Jul-16.
 */
public class CurrentWeatherForNearbyCities {

    public static String apiKey = "e446e2bc4dc218a04d3c9a995fcec373";

    private String cityName;
    private Coordinate coordinate;
    private MainInfo mainInfo;
    private Weather weather;
    private Wind wind;

    public CurrentWeatherForNearbyCities(String cityName, Coordinate coordinate, MainInfo mainInfo, Weather weather, Wind wind) {
        this.cityName = cityName;
        this.coordinate = coordinate;
        this.mainInfo = mainInfo;
        this.weather = weather;
        this.wind = wind;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public MainInfo getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(MainInfo mainInfo) {
        this.mainInfo = mainInfo;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public static List<CurrentWeatherForNearbyCities> getCurrentWeatherForNearbyCities(double lat, double lon)
    {
        String url_string = "http://api.openweathermap.org/data/2.5/find?lat="+"&lon="+lon+"&cnt=10&appid="+apiKey;
        List<CurrentWeatherForNearbyCities> list = new ArrayList<CurrentWeatherForNearbyCities>();
        try {
            Log.e("Url", url_string);
            JSONObject jsonObject = JSONParser.getJSONFromUrl(url_string);
            if(jsonObject != null){
                JSONArray list_array = jsonObject.getJSONArray("list");

                for (int i=0; i<list_array.length();i++)
                {
                    JSONObject jo = list_array.getJSONObject(i);

                    JSONObject coordinate_jo = jo.getJSONObject("coord");
                    JSONObject main_jo = jo.getJSONObject("main");
                    JSONObject wind_jo = jo.getJSONObject("wind");
                    JSONArray weather_ja = jo.getJSONArray("weather");
                    JSONObject weather_jo = weather_ja.getJSONObject(0);

                    String name = jo.getString("name");
                    Coordinate coordinate = new Coordinate(coordinate_jo.getDouble("lon"),coordinate_jo.getDouble("lat"));
                    MainInfo maininfo = new MainInfo(main_jo.getDouble("temp"),main_jo.getDouble("pressure"),main_jo.getDouble("temp_min"),
                            main_jo.getInt("humidity"), main_jo.getDouble("temp_max"));
                    Wind wind = new Wind(wind_jo.getDouble("speed"));
                    Weather weather = new Weather(weather_jo.getString("main"),weather_jo.getString("description"),weather_jo.getString("icon"));

                    CurrentWeatherForNearbyCities currentweatherfornearbycities = new CurrentWeatherForNearbyCities(name, coordinate, maininfo, weather, wind);
                    list.add(currentweatherfornearbycities);
                }
            }else {
                return null;
            }

        } catch (JSONException e) {
            return null;
        }

        return list;
    }
}
