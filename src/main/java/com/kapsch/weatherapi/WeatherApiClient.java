package com.kapsch.weatherapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherApiClient {

    private static final String API_URL = "https://api.open-meteo.com/v1/forecast";
    private static final Logger LOGGER = Logger.getLogger(WeatherApiClient.class.getName());

    public JSONObject getWeatherData(double latitude, double longitude) {
        String urlString = null;
        try {
            urlString = String.format("%s?latitude=%s&longitude=%s&current_weather=true",
                    API_URL,
                    URLEncoder.encode(String.valueOf(latitude), StandardCharsets.UTF_8.toString()),
                    URLEncoder.encode(String.valueOf(longitude), StandardCharsets.UTF_8.toString()));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error encoding URL parameters: {0}", e.getMessage());
            return null;
        }

        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String errorLine;
                StringBuilder errorResponse = new StringBuilder();
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();
                LOGGER.log(Level.SEVERE, "Server responded with error code {0}: {1}", new Object[]{responseCode, errorResponse.toString()});
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());

        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, "Malformed URL: {0}", e.getMessage());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "I/O error: {0}", e.getMessage());
        } catch (JSONException e) {
            LOGGER.log(Level.SEVERE, "JSON parsing error: {0}", e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error: {0}", e.getMessage());
        }

        return null; // Return null or handle it appropriately
    }
}
