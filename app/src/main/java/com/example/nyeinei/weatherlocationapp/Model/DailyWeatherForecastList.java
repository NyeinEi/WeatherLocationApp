package com.example.nyeinei.weatherlocationapp.Model;

/**
 * Created by NyeinEi on 7/14/2016.
 */
public class DailyWeatherForecastList {
    private Long dt;
    private Temperature temperature;
    private double pressure;
    private int humidity;
    private Weather weather;
    private double speed;
    private int deg;
    private int cloud;

    public DailyWeatherForecastList(Temperature temperature, Long dt, double pressure, int humidity, Weather weather, double speed, int deg, int cloud) {
        this.temperature = temperature;
        this.dt = dt;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather = weather;
        this.speed = speed;
        this.deg = deg;
        this.cloud = cloud;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
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

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }
}
