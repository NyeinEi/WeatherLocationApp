package com.example.nyeinei.weatherlocationapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nyeinei.weatherlocationapp.Adapter.CustomAdapter;
import com.example.nyeinei.weatherlocationapp.Model.CurrentWeatherForOneLocation;
import com.example.nyeinei.weatherlocationapp.Model.HourlyWeatherForOneLocation;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class hourlyFragment extends Fragment {

    View view;
    ListView rv_weatherList;
    TextView countryname;
    double latitude,longitude;
    ArrayList<CurrentWeatherForOneLocation> weather_list = null ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hourly, container, false);
        weather_list = new ArrayList<CurrentWeatherForOneLocation>();
        //rv_weatherList = (RecyclerView)view.findViewById(R.id.rv_Weather);
        rv_weatherList = (ListView)view.findViewById(R.id.rv_Weather);
        countryname = (TextView)view.findViewById(R.id.countryTextView);

        try{
            GPSTracker gpsTracker = new GPSTracker(getContext());
            latitude = gpsTracker.latitude;
            Log.e("lat",latitude+"");
            longitude = gpsTracker.longitude;
            Log.e("Long",longitude+"");

            new AsyncTask<Void, Void, HourlyWeatherForOneLocation>() {
                @Override
                protected HourlyWeatherForOneLocation doInBackground(Void... params) {
                    HourlyWeatherForOneLocation hwf = HourlyWeatherForOneLocation.getHourlyWeatherForecast(latitude,longitude);
                    if(hwf != null){
                        return hwf;
                    }else{
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(HourlyWeatherForOneLocation result) {
                    if(result != null){
                        Log.e("HWFFragment",result.getWeatherList().size()+"");
                        countryname.setText(result.getCity().getName());
                        if(result.getWeatherList().size()==0){
                            Toast toast= Toast.makeText(getContext(),"No data for hourly forecast for this location!!",Toast.LENGTH_LONG);
                            toast.show();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Do you want to see the hourly forecast for other location")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(getActivity(),SearchActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog
                                        }
                                    });
                            // Create the AlertDialog object and return it
                            builder.create();
                            builder.show();
                        }else {
                            for(int i=0; i<result.getWeatherList().size();i++){
                                weather_list.add(result.getWeatherList().get(i));
                            }
                            CustomAdapter adapter = new CustomAdapter(weather_list,getContext());
                            rv_weatherList.setAdapter(adapter);
                        }
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Please type valid city name and country code!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        builder.create();
                        builder.show();
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
