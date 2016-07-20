package com.example.nyeinei.weatherlocationapp.Helper;

/**
 * Created by nyeint on 7/14/2016.
 */
public class Coordinate {
    private double lon;
    private double lat;

    public Coordinate(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
