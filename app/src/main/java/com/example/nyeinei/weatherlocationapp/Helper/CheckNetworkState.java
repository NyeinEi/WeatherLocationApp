package com.example.nyeinei.weatherlocationapp.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nyeint on 5/19/2016.
 */
public class CheckNetworkState {

    private Context context;

    public CheckNetworkState(Context ctx){
        context = ctx;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }else {
            return false;
        }
    }


    public boolean isConnectedToServer() {
        try{
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);
            URL myUrl = new URL("http://192.168.0.16:9000/");
            HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
            connection.setConnectTimeout(1000);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            Log.e("Exception",e.toString());
            return false;
        }
    }
}
