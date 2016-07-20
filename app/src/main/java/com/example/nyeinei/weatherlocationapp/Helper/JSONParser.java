package com.example.nyeinei.weatherlocationapp.Helper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 5/5/16.
 */
public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        String line = null;
        JSONObject object = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8); //old charset iso-8859-1
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("readStream Exception",e.getMessage());
        }
        return sb.toString();
    }

    public static String getStream(String url) {
        InputStream is = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();
        }catch (MalformedURLException e) {
            e.getMessage();
            Log.e("Network Error",e.getMessage());
        }
        catch (ConnectException e){
            Log.e("Connection Error", e.toString());
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            Log.e("getStream Exception", e.toString());
            e.printStackTrace();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();

        }catch(Exception e) {
            e.printStackTrace();
            Log.e("getStream Exception2", e.toString());
        }
        return readStream(is);
    }

    public static String postStream(String url, String data) {
        InputStream is = null;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setFixedLengthStreamingMode(data.getBytes().length);
            conn.connect();
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(data.getBytes());
            os.flush();
            is = conn.getInputStream();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("postStream Exception", e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("postStream Exception", e.toString());
        }
        return readStream(is);
    }

    public static JSONObject getJSONFromUrl(String url) {
        Log.e("url", url.toString());
        JSONObject jObj = null;
        try {
            //jObj = new JSONObject(getStream(url));
            jObj = (JSONObject) new JSONTokener(getStream(url)).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Exception", e.toString());
        }
        return jObj;
    }

    public static JSONArray getJSONArrayFromUrl(String url) {
        JSONArray jArray = null;
        try {
            jArray = (JSONArray) new JSONTokener(getStream(url)).nextValue();
            //jArray = new JSONArray(getStream(url));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Exception", e.toString());
        }
        return jArray;
    }

}
