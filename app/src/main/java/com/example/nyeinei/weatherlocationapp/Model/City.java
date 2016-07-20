package com.example.nyeinei.weatherlocationapp.Model;

/**
 * Created by nyeint on 7/14/2016.
 */
public class City {
    private String name;
    private Coordinate coordinate;
    private String country;

    public City(String name, Coordinate coordinate, String country) {
        this.name = name;
        this.coordinate = coordinate;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
