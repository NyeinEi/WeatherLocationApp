package com.example.nyeinei.weatherlocationapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nyeinei.weatherlocationapp.Adapter.MapInfoWindowAdapter;
import com.example.nyeinei.weatherlocationapp.Model.CurrentWeatherForNearbyCities;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;
import java.util.List;

public class mapFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment supportMapFragment;
    private GoogleMap map;
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_map, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, supportMapFragment).commit();
        }

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                map = googleMap;

                final GPSTracker gpsTracker = new GPSTracker(getContext());
                final LatLng currentLocation = new LatLng(gpsTracker.latitude, gpsTracker.longitude);

                new AsyncTask<Void, Void, List<CurrentWeatherForNearbyCities>>() {
                    @Override
                    protected List<CurrentWeatherForNearbyCities> doInBackground(Void... params) {
                        List<CurrentWeatherForNearbyCities> cities = CurrentWeatherForNearbyCities.getCurrentWeatherForNearbyCities(gpsTracker.latitude, gpsTracker.longitude);
                        return cities;
                    }

                    @Override
                    protected void onPostExecute(List<CurrentWeatherForNearbyCities> result) {
                        try{
                            if(result != null){
                                final GPSTracker gpsTracker = new GPSTracker(getContext());
                                final LatLng currentLocation = new LatLng(gpsTracker.latitude, gpsTracker.longitude);

                                Log.e("Before For Loop: Name ", result.get(0).getCityName());
                                Log.e("Before For: Description", result.get(0).getWeather().getDescription());

                                Log.e("Before For Loop: Name1 ", result.get(1).getCityName());
                                Log.e("BeforeFor: Description1", result.get(1).getWeather().getDescription());
                                setUpMap(googleMap);

                                for (int i=0; i<result.size(); i++) {
                                    CurrentWeatherForNearbyCities cw = result.get(i);

                                    String info = cw.getCityName()+","+cw.getWeather().getDescription()+","+cw.getMainInfo().getTemperature()+","+cw.getMainInfo().getTemp_min()+","+cw.getMainInfo().getTemp_max()+","+cw.getWeather().getIcon();

                                    googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(cw.getCoordinate().getLat(),cw.getCoordinate().getLon()))
                                            .title(cw.getCityName())
                                            .snippet(info));
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(result.get(i).getCoordinate().getLat(),result.get(i).getCoordinate().getLon())));
                                    googleMap.setInfoWindowAdapter(new MapInfoWindowAdapter(getContext()));

                                    map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
                                        @Override
                                        public void onInfoWindowClick(Marker marker) {

                                        }
                                    });

                                    setUpMap(googleMap);
                                }
                            }else {
                                Toast toast = Toast.makeText(getContext(),"No internet Connection",Toast.LENGTH_LONG);
                                toast.show();
                            }

                        }catch (NullPointerException e){
                            Toast toast = Toast.makeText(getContext(),"No internet Connection",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                }.execute();

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

    }


    private void setUpMap(GoogleMap map) {
        GPSTracker gpsTracker = new GPSTracker(getContext());
        LatLng SG = new LatLng(gpsTracker.latitude, gpsTracker.longitude);

        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException ex) {

        }

        CameraPosition c = new CameraPosition.Builder()
                .target(SG)
                .zoom(10)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(c));
    }
}

