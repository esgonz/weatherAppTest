# Weather API
Welcome to the Weather API! This API provides weather details for specific locations. Below are the available endpoints and how to use them.

## Endpoints

### 1. Get Weather Data

**Endpoint:** `/weather`

**Method:** `GET`

**Description:** Retrieves current weather data for a specified location.

**Parameters:**
- `latitude` (required): The latitude of the location.
- `longitude` (required): The longitude of the location.

**Example Request:**
```bash
curl -X GET "http://localhost:8080/weather-api/weather?latitude=33.0000&longitude=-70.0000"
```

**Example Response:**
```json
{
    "elevation": 0,
    "generationtime_ms": 0.10395050048828125,
    "timezone_abbreviation": "GMT",
    "timezone": "GMT",
    "current_weather_units": {
        "weathercode": "wmo code",
        "temperature": "°C",
        "windspeed": "km/h",
        "is_day": "",
        "interval": "seconds",
        "time": "iso8601",
        "winddirection": "°"
    },
    "latitude": 32.994816,
    "utc_offset_seconds": 0,
    "current_weather": {
        "weathercode": 0,
        "temperature": 27.4,
        "windspeed": 16.9,
        "is_day": 1,
        "interval": 900,
        "time": "2024-08-31T20:45",
        "winddirection": 232
    },
    "longitude": -70.008934
}
```

### 2. Get Weather Details by Location (JSON)

**Endpoint:** `/weather/all`

**Method:** `GET`

**Description:** Retrieves weather details for a specified location in JSON format.

**Parameters:**
- `latitude` (required): The latitude of the location.
- `longitude` (required): The longitude of the location.

**Example Request:**
```bash
curl -X GET "http://localhost:8080/weather-api/weather/all?latitude=33.0000&longitude=-70.0000"
```

**Example Response:**
```json
[
    {
        "latitude": 33.0000,
        "longitude": -70.0000,
        "temperature": 27.4,
        "windSpeed": 17.9,
        "windDirection": "232",
        "localTime": "2024-08-31T20:45",
        "utc": "2024-08-31T20:45"
    },
    {
        "latitude": 33.0000,
        "longitude": -70.0000,
        "temperature": 27.4,
        "windSpeed": 16.9,
        "windDirection": "232",
        "localTime": "2024-08-31T20:45",
        "utc": "2024-08-31T20:45"
    }
]
```

### 3. Get Weather Details by Location (CSV)

**Endpoint:** `/weather/all/csv`

**Method:** `GET`

**Description:** Retrieves weather details for a specified location in CSV format.

**Parameters:**
- `latitude` (required): The latitude of the location.
- `longitude` (required): The longitude of the location.

**Example Request:**
```bash
curl -X GET "http://localhost:8080/weather-api/weather/all/csv?latitude=33.0000&longitude=-70.0000" -o weather_details.csv
```

**Example Response:**
```
Latitude,Longitude,Temperature,WindSpeed,WindDirection,LocalTime,UTCTime
33.0000,-70.0000,27.4,17.9,232,2024-08-31T20:45,2024-08-31T20:45
33.0000,-70.0000,27.4,16.9,232,2024-08-31T20:45,2024-08-31T20:45
```

## How to Use This Information

- Get Current Weather Data: Use the `/weather` endpoint to get the current weather data for a specific location by providing the latitude and longitude.

- Get Weather Details in JSON: Use the `/weather/all` endpoint to get detailed weather information for a specific location in JSON format. This is useful for applications that need to process weather data programmatically.
Welcome to the Weather API! This API provides weather details for specific locations. Below are the available endpoints and how to use them.

## Endpoints

### 1. Get Weather Data

**Endpoint:** `/weather`

**Method:** `GET`

**Description:** Retrieves current weather data for a specified location.

**Parameters:**
- `latitude` (required): The latitude of the location.
- `longitude` (required): The longitude of the location.

**Example Request:**
```bash
curl -X GET "http://localhost:8080/weather-api/weather?latitude=33.0000&longitude=-70.0000"
```

**Example Response:**
```json
{
    "elevation": 0,
    "generationtime_ms": 0.10395050048828125,
    "timezone_abbreviation": "GMT",
    "timezone": "GMT",
    "current_weather_units": {
        "weathercode": "wmo code",
        "temperature": "°C",
        "windspeed": "km/h",
        "is_day": "",
        "interval": "seconds",
        "time": "iso8601",
        "winddirection": "°"
    },
    "latitude": 32.994816,
    "utc_offset_seconds": 0,
    "current_weather": {
        "weathercode": 0,
        "temperature": 27.4,
        "windspeed": 16.9,
        "is_day": 1,
        "interval": 900,
        "time": "2024-08-31T20:45",
        "winddirection": 232
    },
    "longitude": -70.008934
}
```

### 2. Get Weather Details by Location (JSON)

**Endpoint:** `/weather/all`

**Method:** `GET`

**Description:** Retrieves weather details for a specified location in JSON format.

**Parameters:**
- `latitude` (required): The latitude of the location.
- `longitude` (required): The longitude of the location.

**Example Request:**
```bash
curl -X GET "http://localhost:8080/weather-api/weather/all?latitude=33.0000&longitude=-70.0000"
```

**Example Response:**
```json
[
    {
        "latitude": 33.0000,
        "longitude": -70.0000,
        "temperature": 27.4,
        "windSpeed": 17.9,
        "windDirection": "232",
        "localTime": "2024-08-31T20:45",
        "utc": "2024-08-31T20:45"
    },
    {
        "latitude": 33.0000,
        "longitude": -70.0000,
        "temperature": 27.4,
        "windSpeed": 16.9,
        "windDirection": "232",
        "localTime": "2024-08-31T20:45",
        "utc": "2024-08-31T20:45"
    }
]
```

### 3. Get Weather Details by Location (CSV)

**Endpoint:** `/weather/all/csv`

**Method:** `GET`

**Description:** Retrieves weather details for a specified location in CSV format.

**Parameters:**
- `latitude` (required): The latitude of the location.
- `longitude` (required): The longitude of the location.

**Example Request:**
```bash
curl -X GET "http://localhost:8080/weather-api/weather/all/csv?latitude=33.0000&longitude=-70.0000" -o weather_details.csv
```

**Example Response:**
```
Latitude,Longitude,Temperature,WindSpeed,WindDirection,LocalTime,UTCTime
33.0000,-70.0000,27.4,17.9,232,2024-08-31T20:45,2024-08-31T20:45
33.0000,-70.0000,27.4,16.9,232,2024-08-31T20:45,2024-08-31T20:45
```

## How to Use This Information

- Get Current Weather Data: Use the `/weather` endpoint to get the current weather data for a specific location by providing the latitude and longitude.

- Get Weather Details in JSON: Use the `/weather/all` endpoint to get detailed weather information for a specific location in JSON format. This is useful for applications that need to process weather data programmatically.

- Get Weather Details in CSV: Use the `/weather/all/csv` endpoint to get detailed weather information for a specific location in CSV format. This is useful for exporting data to a spreadsheet.

Running the Application
Prerequisites
- Java Development Kit (JDK): Ensure you have JDK 17 or higher installed.
- Apache Maven: Ensure you have Maven installed for building the project.
- PostgreSQL Database: Ensure you have PostgreSQL installed and running.

Database Configuration
Install PostgreSQL:
```bash
sudo apt-get update
sudo apt-get install postgresql postgresql-contrib
```

