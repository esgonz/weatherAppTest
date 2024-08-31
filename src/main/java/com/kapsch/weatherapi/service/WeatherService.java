package com.kapsch.weatherapi.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import com.kapsch.weatherapi.dao.WeatherDetailDAO;
import com.kapsch.weatherapi.dao.WeatherLocationDAO;
import com.kapsch.weatherapi.model.WeatherDetail;
import com.kapsch.weatherapi.model.WeatherLocation;

public class WeatherService {

    private WeatherLocationDAO locationDAO;
    private WeatherDetailDAO detailDAO;

    @Inject
    public WeatherService() {
        this.locationDAO = new WeatherLocationDAO();
        this.detailDAO = new WeatherDetailDAO();
    }

    @Transactional
    public void saveWeatherData(WeatherLocation location, WeatherDetail detail) {
        // Check if a WeatherLocation with the same latitude and longitude already exists
        WeatherLocation existingLocation = locationDAO.findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());

        if (existingLocation != null) {
            // Use the existing location
            location = existingLocation;
        } else {
            // Save the new location
            locationDAO.save(location);
        }

        // Ensure localTime is set
        if (detail.getLocalTime() == null) {
            detail.setLocalTime(LocalDateTime.now()); // Set to current time or appropriate value
        }

        // Ensure weatherLocation is set
        if (detail.getWeatherLocation() == null) {
            detail.setWeatherLocation(location); // Set to the provided location
        }

        // Save the detail
        detailDAO.save(detail);
    }

    // new untested method
    @Transactional
    public List<WeatherDetail> getWeatherDetailsByLocation(double latitude, double longitude) {
        WeatherLocation location = locationDAO.findByLatitudeAndLongitude(latitude, longitude);
        if (location != null) {
            return detailDAO.findByLocation(location);
        }
        return null;
    }
}
