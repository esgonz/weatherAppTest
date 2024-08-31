package com.kapsch.weatherapi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "weather_detail") // Ensure this matches your table name
public class WeatherDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "utc_time", nullable = false) // Ensure this matches your column name
    private LocalDateTime utcTime;

    @Column(name = "local_time", nullable = false) // Ensure this matches your column name
    private LocalDateTime localTime;

    @Column(nullable = false)
    private double temperature;

    @Column(name = "wind_speed", nullable = false) // Ensure this matches your column name
    private double windSpeed;

    @Column(name = "wind_direction", nullable = false) // Ensure this matches your column name
    private String windDirection;

    @ManyToOne
    @JoinColumn(name = "weather_location_id", nullable = false)
    private WeatherLocation weatherLocation;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(LocalDateTime utcTime) {
        this.utcTime = utcTime;
    }

    public LocalDateTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalDateTime localTime) {
        this.localTime = localTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public WeatherLocation getWeatherLocation() {
        return weatherLocation;
    }

    public void setWeatherLocation(WeatherLocation weatherLocation) {
        this.weatherLocation = weatherLocation;
    }
}
