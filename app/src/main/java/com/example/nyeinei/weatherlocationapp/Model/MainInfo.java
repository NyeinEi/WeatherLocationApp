package com.example.nyeinei.weatherlocationapp.Model;

/**
 * Created by nyeint on 7/14/2016.
 */
public class MainInfo {
    private double temperature;
    private double pressure;
    private int humidity;
    private double temp_min;
    private double temp_max;

    public MainInfo(double temperature, double pressure, double temp_min, int humidity, double temp_max) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.temp_min = temp_min;
        this.humidity = humidity;
        this.temp_max = temp_max;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
}
