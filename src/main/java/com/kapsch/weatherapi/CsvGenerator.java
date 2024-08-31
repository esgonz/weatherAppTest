package com.kapsch.weatherapi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.kapsch.weatherapi.model.WeatherDetail;

public class CsvGenerator {

    public void generateCsvFile(List<WeatherDetail> weatherDetails, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Latitude,Longitude,Temperature,WindSpeed,WindDirection,UTCTime,LocalTime\n");

            for (WeatherDetail detail : weatherDetails) {
                writer.append(detail.getWeatherLocation().getLatitude() + ",")
                        .append(detail.getWeatherLocation().getLongitude() + ",")
                        .append(detail.getTemperature() + ",")
                        .append(detail.getWindSpeed() + ",")
                        .append(detail.getWindDirection() + ",")
                        .append(detail.getUtcTime() + ",")
                        .append(detail.getLocalTime() + "\n");
            }
        }
    }
}
