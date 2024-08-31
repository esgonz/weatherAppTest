package com.kapsch.weatherapi;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kapsch.weatherapi.model.WeatherDetail;
import com.kapsch.weatherapi.service.WeatherService;

public class WeatherServletTest {

    private WeatherServlet weatherServlet;

    @Mock
    private WeatherService weatherService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        weatherServlet = new WeatherServlet();
        weatherServlet.init();
        weatherServlet.setWeatherService(weatherService);
    }

    private List<WeatherDetail> getFixedWeatherDetails() {
        WeatherDetail detail1 = new WeatherDetail();
        detail1.setTemperature(27.4);
        detail1.setWindSpeed(17.9);
        detail1.setWindDirection("232");
        detail1.setLocalTime(java.time.LocalDateTime.parse("2024-08-31T20:45"));
        detail1.setUtcTime(java.time.LocalDateTime.parse("2024-08-31T20:45"));

        WeatherDetail detail2 = new WeatherDetail();
        detail2.setTemperature(27.4);
        detail2.setWindSpeed(17.9);
        detail2.setWindDirection("232");
        detail2.setLocalTime(java.time.LocalDateTime.parse("2024-08-31T20:45"));
        detail2.setUtcTime(java.time.LocalDateTime.parse("2024-08-31T20:45"));

        return Arrays.asList(detail1, detail2);
    }

    @Test
    public void testHandleGetWeatherDetailsByLocation() throws Exception {
        when(request.getPathInfo()).thenReturn("/all");
        when(request.getParameter("latitude")).thenReturn("-33.447487");
        when(request.getParameter("longitude")).thenReturn("-70.673676");

        // Mock the service to return fixed data
        when(weatherService.getWeatherDetailsByLocation(-33.447487, -70.673676)).thenReturn(getFixedWeatherDetails());

        // Mocking the response writer
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        weatherServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        writer.flush();

        // Check the JSON response
        String responseOutput = stringWriter.toString();
        assertTrue(responseOutput.contains("\"localTime\":\"2024-08-31T20:45\""));
        assertTrue(responseOutput.contains("\"utc\":\"2024-08-31T20:45\""));
        assertTrue(responseOutput.contains("\"latitude\":-33.447487"));
        assertTrue(responseOutput.contains("\"longitude\":-70.673676"));
        assertTrue(responseOutput.contains("\"temperature\":27.4"));
        assertTrue(responseOutput.contains("\"windSpeed\":17.9"));
        assertTrue(responseOutput.contains("\"windDirection\":\"232\""));
    }

}
