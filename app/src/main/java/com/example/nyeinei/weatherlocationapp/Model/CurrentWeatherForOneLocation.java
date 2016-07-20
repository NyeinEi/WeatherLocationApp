package com.example.nyeinei.weatherlocationapp.Model;

import android.os.StrictMode;
import android.util.Log;

import com.example.nyeinei.weatherlocationapp.Helper.CheckNetworkState;
import com.example.nyeinei.weatherlocationapp.Helper.JSONParser;
import com.example.nyeinei.weatherlocationapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by nyeint on 7/14/2016.
 */
public class CurrentWeatherForOneLocation {
    private Weather weather;
    private MainInfo maininfo;
    private Wind wind;
    private CloudInfo cloud;
    private Sys sys;
    private Long dt;
    private String dt_txt;
    private Rain rain;
    public static String apiKey = "e446e2bc4dc218a04d3c9a995fcec373";
    public static CurrentWeatherForOneLocation currentweather = null;
    private String name;


    public CurrentWeatherForOneLocation(Weather weather, MainInfo maininfo, Wind wind, Long dt, CloudInfo cloud,Sys sys,String name) {
        this.weather = weather;
        this.maininfo = maininfo;
        this.wind = wind;
        this.dt = dt;
        this.cloud = cloud;
        this.sys = sys;
        this.name = name;
    }

    public CurrentWeatherForOneLocation(Weather weather, MainInfo maininfo, Wind wind, String dt_txt, CloudInfo cloud) {
        this.weather = weather;
        this.maininfo = maininfo;
        this.wind = wind;
        this.cloud = cloud;
        this.dt_txt = dt_txt;
        //this.rain = rain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public MainInfo getMaininfo() {
        return maininfo;
    }

    public void setMaininfo(MainInfo maininfo) {
        this.maininfo = maininfo;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public CloudInfo getCloud() {
        return cloud;
    }

    public void setCloud(CloudInfo cloud) {
        this.cloud = cloud;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public static CurrentWeatherForOneLocation getCurrentWeather(final double latitude, final double longitude){
        JSONObject object = null;
        Weather weather = null;
        MainInfo mainInfo = null;
        Wind wind = null;
        CloudInfo cloudInfo = null;
        Long dt = null;
        CurrentWeatherForOneLocation cwf = null;
        Sys sys = null;
        String name = null;

        try {
            String url_string = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s",latitude,longitude,apiKey);
            object = JSONParser.getJSONFromUrl(url_string);
            Log.e("URL",url_string);
            //getting name obj from jsonobject
            name = object.getString("name");

            //getting weather obj from jsonobject
            JSONArray jr_weathers = object.getJSONArray("weather");
            for (int i =0; i<jr_weathers.length(); i++) {
                JSONObject o=jr_weathers.getJSONObject(i);
                weather = new Weather(o.getString("main"),o.getString("description"),o.getString("icon"));
            }

            //getting maininfo obj from json object
            JSONObject jo_mainInfo = object.getJSONObject("main");
            mainInfo = new MainInfo(jo_mainInfo.getDouble("temp"),jo_mainInfo.getDouble("pressure"),jo_mainInfo.getDouble("temp_min"),jo_mainInfo.getInt("humidity"),jo_mainInfo.getDouble("temp_max"));

            //getting wind from json object
            JSONObject jo_wind = object.getJSONObject("wind");
            wind = new Wind(jo_wind.getDouble("speed"));

            //getting cloudinfo obj from json object
            JSONObject jo_cloudinfo = object.getJSONObject("clouds");
            cloudInfo = new CloudInfo(jo_cloudinfo.getInt("all"));

            //getting Sys obj from json object
            JSONObject jo_sys = object.getJSONObject("sys");
            sys = new Sys(jo_sys.getLong("sunrise"),jo_sys.getLong("sunset"));

            //getting dt from json object
            dt = object.getLong("dt");

            //getting the whole object of currentweatherforonelocation
            cwf = new CurrentWeatherForOneLocation(weather,mainInfo,wind,dt,cloudInfo,sys,name);

        } catch (Exception e) {
            return null;
        }
        return cwf;
    }
}
