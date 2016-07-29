package com.example.nyeinei.weatherlocationapp;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nyeinei.weatherlocationapp.Adapter.DailyWeatherListAdapter;
import com.example.nyeinei.weatherlocationapp.Helper.CheckNetworkState;
import com.example.nyeinei.weatherlocationapp.Model.CurrentWeatherForOneLocation;
import com.example.nyeinei.weatherlocationapp.Model.DailyWeatherForOneLocation;
import com.example.nyeinei.weatherlocationapp.Model.DailyWeatherForecastList;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {
    View view;
    TextView title,country,currentTime,temperature,min_temp,max_temp,cloud,cloud_desc,wind,humidity,sunrise,sunset,celicus_symbol_textView;
    TextView showmore,showless;
    ImageView weatherimage,logo;
    Toolbar toolbar;

    double kelvin = 273.15;

    public static double latitude;
    public static double longtitude;
    public static String countryname;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000*60*1;

    URL url = null;
    Bitmap image = null;

    GridView dailyforecast;
    ArrayList<DailyWeatherForecastList> weather_list = null ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.current_weather_fragment, container, false);
        dailyforecast = (GridView)view.findViewById(R.id.dailyforecastgrid);
        title = MainActivity.title;
        logo = MainActivity.logo;
        toolbar = MainActivity.toolbar;
        weather_list = new ArrayList<DailyWeatherForecastList>();

        try{
            GPSTracker gpsTracker = new GPSTracker(getContext());
            latitude = gpsTracker.latitude;
            Log.e("lat",latitude+"");

            longtitude = gpsTracker.longitude;
            Log.e("Long",longtitude+"");

            new AsyncTask<Void, Void, DailyWeatherForOneLocation>() {
                @Override
                protected DailyWeatherForOneLocation doInBackground(Void... params) {
                    DailyWeatherForOneLocation dwf = DailyWeatherForOneLocation.getDailyWeatherForecast(latitude,longtitude);
                    Log.e("DWF",dwf+"");
                    return dwf;
                }

                @Override
                protected void onPostExecute(DailyWeatherForOneLocation result) {
                    if(result!=null){
                        Log.e("DWFFragment",result.getWeatherList()+"");

                        if(result.getWeatherList().size()==0){
                            Toast toast= Toast.makeText(getContext(),"No data for daily forecast for this location!!",Toast.LENGTH_LONG);
                            toast.show();
                        }else {
                            for(int i=0; i<result.getWeatherList().size();i++){
                                weather_list.add(result.getWeatherList().get(i));
                            }
                            country.setText(result.getCity().getName());
                            title.setText("Current Location Forecast");
                            logo.setBackgroundResource(R.drawable.locationicon);
                            toolbar.setTitle("");

                            Log.e("WeatherListSize",weather_list.size()+"");

                            DailyWeatherListAdapter adapter = new DailyWeatherListAdapter(weather_list,getContext());
                            dailyforecast.setAdapter(adapter);
                        }
                    }else {
                        Toast toast = Toast.makeText(getContext(),"No internet Connection",Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }.execute();
        }catch (Exception e){
            Toast toast = Toast.makeText(getContext(),"Cannot get Your Device's Location! Please reboot your device",Toast.LENGTH_LONG);
            toast.show();
        }


        country = (TextView)view.findViewById(R.id.countryTextView);
        currentTime = (TextView)view.findViewById(R.id.currentTimeTextView);
        temperature = (TextView)view.findViewById(R.id.temperatureTextView);
        cloud = (TextView)view.findViewById(R.id.cloudpercentTextView);
        wind = (TextView)view.findViewById(R.id.windTextView);
        humidity =(TextView)view.findViewById(R.id.humidityTextView);
        sunrise = (TextView)view.findViewById(R.id.sunriseTextView);
        sunset = (TextView)view.findViewById(R.id.sunsetTextView);
        showmore = (TextView) view.findViewById(R.id.showmoreTextView);
        min_temp = (TextView)view.findViewById(R.id.min_temperatureTextView);
        max_temp = (TextView)view.findViewById(R.id.max_temperatureTextView);
        cloud_desc = (TextView)view.findViewById(R.id.clouddescTextView);
        celicus_symbol_textView = (TextView)view.findViewById(R.id.celicus_symbol_textView);
        showless = (TextView)view.findViewById(R.id.show_lessTextView);
        weatherimage = (ImageView)view.findViewById(R.id.weather_image);

        try{
            GPSTracker gpsTracker = new GPSTracker(getContext());
            latitude = gpsTracker.latitude;
            Log.e("lat",latitude+"");

            longtitude = gpsTracker.longitude;
            Log.e("Long",longtitude+"");

            new AsyncTask<Void, Void, CurrentWeatherForOneLocation>() {
                @Override
                protected CurrentWeatherForOneLocation doInBackground(Void... params) {
                    CurrentWeatherForOneLocation cwf = CurrentWeatherForOneLocation.getCurrentWeather(latitude, longtitude);
                    if(cwf!=null){
                        return cwf;
                    }else {
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(CurrentWeatherForOneLocation result) {
                    if(result!=null){
                        Log.e("CWF",result.getMaininfo().getHumidity()+"");
                        countryname = result.getName();
                        country.setText(countryname);
                        currentTime.setText(getCurrentDate());
                        temperature.setText(Double.toString(result.getMaininfo().getTemperature()-kelvin).substring(0,4));
                        celicus_symbol_textView.setText((char) 0x00B0 +"C");
                        min_temp.setText(Double.toString(result.getMaininfo().getTemp_min()-kelvin).substring(0,4) + (char) 0x00B0 +"C"+" /");
                        max_temp.setText(Double.toString(result.getMaininfo().getTemp_max()-kelvin).substring(0,4)+ (char) 0x00B0 +"C");
                        cloud.setText(Double.toString(result.getCloud().getCloudiness())+"%");
                        cloud_desc.setText(result.getWeather().getDescription());
                        //wind.setText(Double.toString(result.getWind().getSpeed())+" mph");
                        //humidity.setText(Double.toString(result.getMaininfo().getHumidity())+"%");
                        //sunrise.setText(convertUTCtoTime(result.getSys().getSunrise()));
                        //sunset.setText(convertUTCtoTime(result.getSys().getSunset()));

                        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
                        try {
                            url = new URL("http://openweathermap.org/img/w/"+result.getWeather().getIcon()+".png");
                            Log.e("Imgurl",url+"");
                            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            weatherimage.setImageBitmap(image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        /*showmore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LinearLayout hiddenlayout = (LinearLayout)view.findViewById(R.id.hiddenlayout);
                                hiddenlayout.setVisibility(View.VISIBLE);
                                showmore.setVisibility(View.GONE);
                            }
                        });*/

                        /*showless.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showmore.setVisibility(View.VISIBLE);
                                LinearLayout hiddenlayout = (LinearLayout)view.findViewById(R.id.hiddenlayout);
                                hiddenlayout.setVisibility(View.GONE);
                            }
                        });*/
                    }else {
                        Toast toast = Toast.makeText(getContext(),"No internet Connection",Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }.execute();
        }catch (Exception e){
            Toast toast = Toast.makeText(getContext(),"Cannot get Your Device's Location! Please reboot your device",Toast.LENGTH_LONG);
            toast.show();
        }

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private String convertUTCtoTime(double utc) {
        double d = 1.14*100;
        Long l = Math.round(d);
        return convertLongtoTime(l);
    }

    private String convertLongtoTime(Long l) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateFormatted = formatter.format(l);
        return dateFormatted;
    }

    private String getCurrentDate() {
        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();
        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String reportDate = df.format(today);

        return reportDate;
    }

    private String convertLongtoDate(Long dt) {
        String longV =Long.toString(dt);
        long millisecond = Long.parseLong(longV);
        String dateString= DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
        return dateString;
    }

}
