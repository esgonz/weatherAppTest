package com.kapsch.weatherapi;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.json.JSONException;
import org.json.JSONObject;

import com.kapsch.weatherapi.model.WeatherDetail;

public class WeatherDataParser {

    public WeatherDetail parseWeatherData(JSONObject json) {
        WeatherDetail detail = new WeatherDetail();
        try {
            JSONObject currentWeather = json.getJSONObject("current_weather");

            detail.setTemperature(currentWeather.getDouble("temperature"));
            detail.setWindSpeed(currentWeather.getDouble("windspeed"));

            // Handle wind direction as an integer
            detail.setWindDirection(String.valueOf(currentWeather.getInt("winddirection")));

            // Parse time field from the API
            String timeStr = currentWeather.getString("time");
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime utcTime;
            try {
                utcTime = LocalDateTime.parse(timeStr, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Date-time parsing error: " + e.getMessage());
                return null; // or handle it according to your needs
            }

            detail.setUtcTime(utcTime);

            // Convert UTC to local time if needed
            detail.setLocalTime(utcTime.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneOffset.systemDefault()).toLocalDateTime());

        } catch (JSONException e) {
            System.err.println("JSON parsing error: " + e.getMessage());
            // Handle JSON parsing error
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            // Handle any other unexpected errors
        }
        return detail;
    }
}
