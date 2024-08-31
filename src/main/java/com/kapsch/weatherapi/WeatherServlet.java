package com.kapsch.weatherapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kapsch.weatherapi.model.WeatherDetail;
import com.kapsch.weatherapi.model.WeatherLocation;
import com.kapsch.weatherapi.service.WeatherService;

@WebServlet("/weather/*")
public class WeatherServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(WeatherServlet.class.getName());
    private WeatherService weatherService;

    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public void init() throws ServletException {
        try {
            this.weatherService = new WeatherService(); // Manually instantiate or ensure DI is working
            if (this.weatherService == null) {
                throw new ServletException("WeatherService initialization failed");
            }
        } catch (ServletException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize WeatherService: {0}", e.getMessage());
            throw new ServletException("Failed to initialize WeatherService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path != null) {
            switch (path) {
                case "/all/csv":
                    handleGetWeatherDetailsByLocationCsv(request, response);
                    break;
                case "/all":
                    handleGetWeatherDetailsByLocation(request, response);
                    break;
                default:
                    handleGetWeatherData(request, response);
                    break;
            }
        } else {
            sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid request path");
        }
    }

    private void handleGetWeatherData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String latitudeParam = request.getParameter("latitude");
        String longitudeParam = request.getParameter("longitude");

        if (latitudeParam == null || longitudeParam == null) {
            sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Missing latitude or longitude parameter");
            return;
        }

        double latitude;
        double longitude;
        try {
            latitude = Double.parseDouble(latitudeParam);
            longitude = Double.parseDouble(longitudeParam);
        } catch (NumberFormatException e) {
            sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid latitude or longitude format");
            return;
        }

        try {
            WeatherApiClient client = new WeatherApiClient();
            JSONObject weatherData = client.getWeatherData(latitude, longitude);

            if (weatherData == null) {
                sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to retrieve weather data");
                return;
            }

            WeatherDataParser parser = new WeatherDataParser();
            WeatherDetail detail = parser.parseWeatherData(weatherData);

            if (detail == null) {
                sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to parse weather data");
                return;
            }

            WeatherLocation location = new WeatherLocation();
            location.setLatitude(latitude);
            location.setLongitude(longitude);

            // Save the data
            if (weatherService != null) {
                weatherService.saveWeatherData(location, detail);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(weatherData.toString());
            } else {
                sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "WeatherService is not available");
            }

        } catch (IOException e) {
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "I/O error: " + e.getMessage());
        } catch (JSONException e) {
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JSON parsing error: " + e.getMessage());
        } catch (Exception e) {
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
        }
    }

    private void handleGetWeatherDetailsByLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String latitudeParam = request.getParameter("latitude");
        String longitudeParam = request.getParameter("longitude");

        if (latitudeParam == null || longitudeParam == null) {
            sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Missing latitude or longitude parameter");
            return;
        }

        double latitude;
        double longitude;
        try {
            latitude = Double.parseDouble(latitudeParam);
            longitude = Double.parseDouble(longitudeParam);
        } catch (NumberFormatException e) {
            sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid latitude or longitude format");
            return;
        }

        try {
            if (weatherService != null) {
                List<WeatherDetail> details = weatherService.getWeatherDetailsByLocation(latitude, longitude);
                if (details == null || details.isEmpty()) {
                    sendJsonError(response, HttpServletResponse.SC_NOT_FOUND, "No weather details found for the specified location");
                    return;
                }

                // Convert the list of WeatherDetail objects to JSON
                JSONArray jsonArray = new JSONArray();
                for (WeatherDetail detail : details) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("latitude", latitude);
                    jsonObject.put("longitude", longitude);
                    jsonObject.put("temperature", detail.getTemperature());
                    jsonObject.put("windSpeed", detail.getWindSpeed());
                    jsonObject.put("windDirection", detail.getWindDirection());
                    jsonObject.put("localTime", detail.getLocalTime().toString());
                    jsonObject.put("utc", detail.getUtcTime().toString());
                    jsonArray.put(jsonObject);
                }

                // Send the JSON response
                response.setContentType("application/json");
                response.getWriter().write(jsonArray.toString());
            } else {
                sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "WeatherService is not available");
            }
        } catch (JSONException e) {
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JSON parsing error: " + e.getMessage());
        } catch (IOException e) {
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
        }
    }

    private void handleGetWeatherDetailsByLocationCsv(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String latitudeParam = request.getParameter("latitude");
        String longitudeParam = request.getParameter("longitude");

        if (latitudeParam == null || longitudeParam == null) {
            sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Missing latitude or longitude parameter");
            return;
        }

        double latitude;
        double longitude;
        try {
            latitude = Double.parseDouble(latitudeParam);
            longitude = Double.parseDouble(longitudeParam);
        } catch (NumberFormatException e) {
            sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid latitude or longitude format");
            return;
        }

        try {
            List<WeatherDetail> details = weatherService.getWeatherDetailsByLocation(latitude, longitude);
            if (details == null || details.isEmpty()) {
                sendJsonError(response, HttpServletResponse.SC_NOT_FOUND, "No weather details found for the specified location");
                return;
            }

            response.setContentType("text/csv");
            String fileName = String.format("weather_details_%s_%s.csv", latitude, longitude);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            PrintWriter writer = response.getWriter();
            writer.println("Latitude,Longitude,Temperature,WindSpeed,WindDirection,LocalTime,UTCTime");

            for (WeatherDetail detail : details) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s%n",
                        latitude,
                        longitude,
                        detail.getTemperature(),
                        detail.getWindSpeed(),
                        detail.getWindDirection(),
                        detail.getLocalTime().toString(),
                        detail.getUtcTime().toString()
                );
            }
            writer.flush();
        } catch (IOException e) {
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error: " + e.getMessage());
        }
    }

    private void sendJsonError(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        JSONObject error = new JSONObject();
        error.put("status", statusCode);
        error.put("message", message);
        response.getWriter().write(error.toString());
    }
}
