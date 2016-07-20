package com.example.nyeinei.weatherlocationapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyeinei.weatherlocationapp.Model.CurrentWeatherForOneLocation;
import com.example.nyeinei.weatherlocationapp.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NyeinEi on 7/18/2016.
 */
public class CustomAdapter extends BaseAdapter {

    private List<CurrentWeatherForOneLocation> list;
    Context mContext;
    private LayoutInflater layoutInflater;
    private TextView timeTextView,tempTextView,windTextView,humidityTextView;
    private ImageView weather_image;
    double kelvin = 273.15;
    URL url = null;
    Bitmap image = null;

    public CustomAdapter(ArrayList<CurrentWeatherForOneLocation> data,Context context){

        this.list = data;
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v = new ViewHolder();

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.weather_list_row, null);

            v.weather_image = (ImageView) convertView.findViewById(R.id.weather_image);
            v.timeTextView = (TextView)convertView.findViewById(R.id.timeTextView);
            v.tempTextView = (TextView)convertView.findViewById(R.id.tempTextView);
            v.windTextView = (TextView)convertView.findViewById(R.id.windTextView);
            v.humidityTextView = (TextView)convertView.findViewById(R.id.humidityTextView);

            convertView.setTag(v);
        }else {
            v = (ViewHolder) convertView.getTag();
        }

        v.timeTextView.setText(list.get(position).getDt_txt());
        v.tempTextView.setText(Double.toString(list.get(position).getMaininfo().getTemperature()-kelvin).substring(0,4)+ (char) 0x00B0 +"C");
        v.windTextView.setText(Double.toString(list.get(position).getWind().getSpeed())+" mph");
        v.humidityTextView.setText(Integer.toString(list.get(position).getMaininfo().getHumidity())+"%");

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        try {
        url = new URL("http://openweathermap.org/img/w/"+list.get(position).getWeather().getIcon()+".png");
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            v.weather_image.setImageBitmap(image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    public static class ViewHolder{
        private TextView timeTextView,tempTextView,windTextView,humidityTextView;
        private ImageView weather_image;
    }
}