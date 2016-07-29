package com.example.nyeinei.weatherlocationapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nyeinei.weatherlocationapp.Helper.ImageLoader;
import com.example.nyeinei.weatherlocationapp.Model.DailyWeatherForecastList;
import com.example.nyeinei.weatherlocationapp.R;

import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by NyeinEi on 7/18/2016.
 */
public class DailyWeatherListAdapter extends BaseAdapter {

    private List<DailyWeatherForecastList> list;
    private Context context;
    double kelvin = 273.15;
    private LayoutInflater layoutInflater;
    private ImageLoader imgLoader;
//    private TextView timeTextView,tempTextView,rainTextView,cloudTextView;
//    private ImageView weather_image;
    URL url = null;
    Bitmap image = null;

    public DailyWeatherListAdapter(List<DailyWeatherForecastList> list,Context context){
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        imgLoader = new ImageLoader(this.context);
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
            convertView = layoutInflater.inflate(R.layout.daily_weather_list_row, null);

            convertView.setBackgroundColor(Color.parseColor("#80dcbafe"));

            v.timeTextView = (TextView)convertView.findViewById(R.id.timeTextView);
            v.tempTextView = (TextView)convertView.findViewById(R.id.tempTextView);
            v.rainTextView = (TextView)convertView.findViewById(R.id.rainTextView);
            v.cloudTextView = (TextView)convertView.findViewById(R.id.cloudTextView);
            v.weather_image = (ImageView) convertView.findViewById(R.id.weather_image);

            convertView.setTag(v);
        }
        else
        {
            v = (ViewHolder) convertView.getTag();
        }



        v.timeTextView.setText(convertLongtoDate(list.get(position).getDt()));
        v.tempTextView.setText(Double.toString(list.get(position).getTemperature().getDay()-kelvin).substring(0,4)+ (char) 0x00B0 +"C");
        v.rainTextView.setText(list.get(position).getWeather().getDescription());
        v.cloudTextView.setText(Integer.toString(list.get(position).getCloud())+"%");

        /*StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
        try {
            url = new URL("http://openweathermap.org/img/w/"+list.get(position).getWeather().getIcon()+".png");
            Log.e("ImageURL",url+"");
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            v.weather_image.setImageBitmap(image);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        imgLoader.DisplayImage("http://openweathermap.org/img/w/"+list.get(position).getWeather().getIcon()+".png", v.weather_image);
        return convertView;
    }

    private String convertLongtoDate(Long dt) {
        String longV =Long.toString(dt);
        long millisecond = Long.parseLong(longV);
        String dateString= DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
        return dateString;
    }

    private static class ViewHolder {
        private TextView timeTextView,tempTextView,rainTextView,cloudTextView;
        private ImageView weather_image;
    }


}
