package com.example.nyeinei.weatherlocationapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nyeinei.weatherlocationapp.Adapter.DailyWeatherListAdapter;
import com.example.nyeinei.weatherlocationapp.Model.DailyWeatherForOneLocation;
import com.example.nyeinei.weatherlocationapp.Model.DailyWeatherForecastList;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class dailyFragment extends Fragment {

    View view;
    ListView rv_weatherList;
    ArrayList<DailyWeatherForecastList> weather_list = null ;
    TextView countryname;
    public static double latitude;
    public static double longtitude;
    //public static String countryname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_daily, container, false);
        weather_list = new ArrayList<DailyWeatherForecastList>();
        rv_weatherList = (ListView)view.findViewById(R.id.rv_Weather);
        countryname = (TextView)view.findViewById(R.id.countryTextView);
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
                            countryname.setText(result.getCity().getName());

                            Log.e("WeatherListSize",weather_list.size()+"");

                            DailyWeatherListAdapter adapter = new DailyWeatherListAdapter(weather_list,getContext());
                            rv_weatherList.setAdapter(adapter);
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

        return view;
    }

}
