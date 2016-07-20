package com.example.nyeinei.weatherlocationapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class SearchHourlyForecastFragment extends Fragment {
    View view;
    TextView cityNameTextView,countryCodeTextView;
    Button searchButton;
    ListView rv_weatherList;
    ArrayList<CurrentWeatherForOneLocation> weather_list = null ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_hourly_forecast, container, false);
        rv_weatherList = (ListView)view.findViewById(R.id.rv_Weather);
        weather_list = new ArrayList<CurrentWeatherForOneLocation>();
        //rv_weatherList = (RecyclerView)view.findViewById(R.id.rv_Weather);
        cityNameTextView = (TextView)view.findViewById(R.id.cityNameTextView);
        countryCodeTextView = (TextView)view.findViewById(R.id.countryCodeTextView);
        searchButton = (Button)view.findViewById(R.id.searchButton);

        cityNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityNameTextView.setText(null);
            }
        });

        countryCodeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryCodeTextView.setText(null);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
                HourlyWeatherForOneLocation hwf = HourlyWeatherForOneLocation.getHourlyWeatherForecastFromCitynameAndCountryCode(cityNameTextView.getText().toString(),countryCodeTextView.getText().toString());
                if(hwf!=null){
                    Log.e("HWFSearchFrag",hwf.toString());

                    if(hwf.getWeatherList().size()==0){
                        Toast toast= Toast.makeText(getContext(),"No data for hourly forecast for this location!!",Toast.LENGTH_LONG);
                        toast.show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("No data found for this location. Please type other city name and country code and search")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        // Create the AlertDialog object and return it
                        builder.create();
                        builder.show();
                    }else {
                        for(int i=0; i<hwf.getWeatherList().size();i++){
                            weather_list.add(hwf.getWeatherList().get(i));
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
        });

        return view;
    }

}
