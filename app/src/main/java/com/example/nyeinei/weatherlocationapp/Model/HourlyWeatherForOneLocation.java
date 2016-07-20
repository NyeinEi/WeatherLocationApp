package com.example.nyeinei.weatherlocationapp.Model;

import android.util.Log;
import android.widget.TextView;

import com.example.nyeinei.weatherlocationapp.Helper.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NyeinEi on 7/14/2016.
 */
public class HourlyWeatherForOneLocation {
    private City city;
    private List<CurrentWeatherForOneLocation> weatherList = new ArrayList<CurrentWeatherForOneLocation>();
    public static String baseURL = "http://api.openweathermap.org/data/2.5/forecast?q=";//Singapore,SG&appid=e446e2bc4dc218a04d3c9a995fcec373"
    public static String apiKey = "e446e2bc4dc218a04d3c9a995fcec373";

    public HourlyWeatherForOneLocation(City city, List<CurrentWeatherForOneLocation> weatherList) {
        this.city = city;
        this.weatherList = weatherList;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<CurrentWeatherForOneLocation> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<CurrentWeatherForOneLocation> weatherList) {
        this.weatherList = weatherList;
    }

    public static HourlyWeatherForOneLocation getHourlyWeatherForecast(double latitude,double longitude){
        String url_string = String.format("http://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&appid=%s",latitude,longitude,apiKey);
        try {
            JSONObject jsonObject = JSONParser.getJSONFromUrl(url_string);
            if(jsonObject != null){
                Weather weather = null;
                MainInfo mainInfo = null;
                Wind wind = null;
                CloudInfo cloudInfo = null;
                String dt_txt;
                Rain rain;
                HourlyWeatherForOneLocation hwf = null;

                //getting city from json object
                JSONObject jo_city = jsonObject.getJSONObject("city");
                //getting coordinate from json object
                JSONObject jo_coor = jo_city.getJSONObject("coord");
                City city = new City(jo_city.getString("name"),new Coordinate(jo_coor.getDouble("lon"),jo_coor.getDouble("lat")),jo_city.getString("country"));

                //getting weatherlist from json object
                JSONArray jr_weatherList = jsonObject.getJSONArray("list");

                List<CurrentWeatherForOneLocation> weather_list = new ArrayList<CurrentWeatherForOneLocation>();
                for(int i=0;i<jr_weatherList.length();i++){
                    JSONObject jo_weatherforecast = jr_weatherList.getJSONObject(i);

                    //getting dt from json object
                    dt_txt = jo_weatherforecast.getString("dt_txt");

                    //getting weather obj from jsonobject
                    JSONArray jr_weathers = jo_weatherforecast.getJSONArray("weather");
                    for (int j =0; j<jr_weathers.length(); j++) {
                        JSONObject o=jr_weathers.getJSONObject(j);
                        weather = new Weather(o.getString("main"),o.getString("description"),o.getString("icon"));
                    }

                    //getting maininfo
                    JSONObject jo_mainInfo = jo_weatherforecast.getJSONObject("main");
                    mainInfo = new MainInfo(jo_mainInfo.getDouble("temp"),jo_mainInfo.getDouble("pressure"),jo_mainInfo.getDouble("temp_min"),jo_mainInfo.getInt("humidity"),jo_mainInfo.getDouble("temp_max"));

                    //getting wind from json object
                    JSONObject jo_wind = jo_weatherforecast.getJSONObject("wind");
                    wind = new Wind(jo_wind.getDouble("speed"));

                    //getting cloudinfo obj from json object
                    JSONObject jo_cloudinfo = jo_weatherforecast.getJSONObject("clouds");
                    cloudInfo = new CloudInfo(jo_cloudinfo.getInt("all"));

                    CurrentWeatherForOneLocation cwf = new CurrentWeatherForOneLocation(weather,mainInfo,wind,dt_txt,cloudInfo);
                    weather_list.add(cwf);
                    //Log.e("CWFF",cwf+"");

                }
                //Log.e("WeatherListSize",weather_list.size()+"");

                hwf = new HourlyWeatherForOneLocation(city,weather_list);
                return hwf;
            }else {
                return null;
            }

        } catch (JSONException e) {
            return null;
        }

    }


    public static HourlyWeatherForOneLocation getHourlyWeatherForecastFromCitynameAndCountryCode(String cityname,String countrycode) {
        String url_string = String.format("http://api.openweathermap.org/data/2.5/forecast?q=%s,%s&appid=%s", cityname, countrycode, apiKey);
        JSONObject jsonObject = JSONParser.getJSONFromUrl(url_string);
        if (jsonObject != null) {
            Weather weather = null;
            MainInfo mainInfo = null;
            Wind wind = null;
            CloudInfo cloudInfo = null;
            String dt_txt;
            Rain rain;
            HourlyWeatherForOneLocation hwf = null;
            try {
                //getting city from json object
                JSONObject jo_city = jsonObject.getJSONObject("city");
                //getting coordinate from json object
                JSONObject jo_coor = jo_city.getJSONObject("coord");
                City city = new City(jo_city.getString("name"), new Coordinate(jo_coor.getDouble("lon"), jo_coor.getDouble("lat")), jo_city.getString("country"));

                //getting weatherlist from json object
                JSONArray jr_weatherList = jsonObject.getJSONArray("list");

                List<CurrentWeatherForOneLocation> weather_list = new ArrayList<CurrentWeatherForOneLocation>();
                for (int i = 0; i < jr_weatherList.length(); i++) {
                    JSONObject jo_weatherforecast = jr_weatherList.getJSONObject(i);

                    //getting dt from json object
                    dt_txt = jo_weatherforecast.getString("dt_txt");

                    //getting weather obj from jsonobject
                    JSONArray jr_weathers = jo_weatherforecast.getJSONArray("weather");
                    for (int j = 0; j < jr_weathers.length(); j++) {
                        JSONObject o = jr_weathers.getJSONObject(j);
                        weather = new Weather(o.getString("main"), o.getString("description"), o.getString("icon"));
                    }

                    //getting maininfo
                    JSONObject jo_mainInfo = jo_weatherforecast.getJSONObject("main");
                    mainInfo = new MainInfo(jo_mainInfo.getDouble("temp"), jo_mainInfo.getDouble("pressure"), jo_mainInfo.getDouble("temp_min"), jo_mainInfo.getInt("humidity"), jo_mainInfo.getDouble("temp_max"));

                    //getting wind from json object
                    JSONObject jo_wind = jo_weatherforecast.getJSONObject("wind");
                    wind = new Wind(jo_wind.getDouble("speed"));

                    //getting cloudinfo obj from json object
                    JSONObject jo_cloudinfo = jo_weatherforecast.getJSONObject("clouds");
                    cloudInfo = new CloudInfo(jo_cloudinfo.getInt("all"));

                    CurrentWeatherForOneLocation cwf = new CurrentWeatherForOneLocation(weather, mainInfo, wind, dt_txt, cloudInfo);
                    weather_list.add(cwf);
                    //Log.e("CWFF",cwf+"");

                }
                //Log.e("WeatherListSize",weather_list.size()+"");
                hwf = new HourlyWeatherForOneLocation(city, weather_list);
                return hwf;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            return null;
        }
        return null;
    }
}
