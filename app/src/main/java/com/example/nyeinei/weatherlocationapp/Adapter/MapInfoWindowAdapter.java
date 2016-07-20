package com.example.nyeinei.weatherlocationapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyeinei.weatherlocationapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.net.URL;

/**
 * Created by Moe Han on 19-Jul-16.
 */
public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
    //private CurrentWeatherForNearbyCities currentWeatherForNearbyCities;
    private LayoutInflater layoutInflater;
    double kelvin = 273.15;
    View view;

    private ImageView weatherimage;
    private TextView countryTextView,cloudDescTextView,temperatureTextView,min_temperatureTextView,max_temperatureTextView,symbolTextView;

    public MapInfoWindowAdapter(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);

        view = layoutInflater.inflate(R.layout.infowindowlayout, null);
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        weatherimage = (ImageView) view.findViewById(R.id.weather_image);
        countryTextView = (TextView)view.findViewById(R.id.countryTextView);
        cloudDescTextView = (TextView)view.findViewById(R.id.clouddescTextView);
        temperatureTextView = (TextView)view.findViewById(R.id.temperatureTextView);
        min_temperatureTextView = (TextView)view.findViewById(R.id.min_temperatureTextView);
        max_temperatureTextView = (TextView)view.findViewById(R.id.max_temperatureTextView);
        symbolTextView = (TextView)view.findViewById(R.id.celicus_symbol_textView);


        String info = marker.getSnippet();
        String[] words = info.split(",");
        String name,description,temperature,tem_min,tem_max,icon;
        name = words[0]; description = words [1]; temperature = words[2];
        tem_min = words [3]; tem_max =words[4]; icon = words[5];

        double double_temp = Double.parseDouble(temperature);
        double double_temp_min = Double.parseDouble(tem_min);
        double double_temp_max = Double.parseDouble(tem_max);
        countryTextView.setText(name);
        cloudDescTextView.setText(description);
        temperatureTextView.setText(Double.toString(double_temp-kelvin).substring(0,4));
        symbolTextView.setText((char) 0x00B0 +"C");
        min_temperatureTextView.setText(Double.toString(double_temp_min-kelvin).substring(0,4) + (char) 0x00B0 +"C"+" /");
        max_temperatureTextView.setText(Double.toString(double_temp_max-kelvin).substring(0,4) + (char) 0x00B0 +"C");
        //textView3.setText(currentWeatherForNearbyCities.getWeather().);

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        try {
            URL url = new URL("http://openweathermap.org/img/w/"+icon+".png");
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            weatherimage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }
}
