package com.kapsch.weatherapi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "weather_location")
public class WeatherLocation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToMany(mappedBy = "weatherLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeatherDetail> weatherDetails;

    // Getters and Setters
    public WeatherLocation() {
    }

    public WeatherLocation(double latitude, double longitude, List<WeatherDetail> weatherDetails) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherDetails = weatherDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<WeatherDetail> getWeatherDetails() {
        return weatherDetails;
    }

    public void setWeatherDetails(List<WeatherDetail> weatherDetails) {
        this.weatherDetails = weatherDetails;
    }
}
