//This file will take from the OpenMeteo API provided

package weather.provider;
import weather.exception.WeatherDataException;
import weather.model.WeatherData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenMeteoWeatherProvider implements WeatherDataProvider {

    private double parseLat(String json) throws WeatherDataException {
        Pattern pattern = Pattern.compile("\"latitude\":\\s*([0-9.-]+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new WeatherDataException(
                    "Latitude missing from http response");
        }
        return Double.parseDouble(matcher.group(1));
    }

    private double parseLon(String json) throws WeatherDataException {
        Pattern pattern = Pattern.compile("\"longitude\":\\s*([0-9.-]+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new WeatherDataException(
                    "Longitude missing from http response");
        }
        return Double.parseDouble(matcher.group(1));
    }

    private double parseTemperature(String json) throws WeatherDataException {
        Pattern pattern = Pattern.compile("\"temperature_2m\":\\s*([0-9.-]+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new WeatherDataException(
                    "Temperature missing from http response");
        }
        return Double.parseDouble(matcher.group(1));
    }

    private double parseHumidity(String json) throws WeatherDataException {
        Pattern pattern = Pattern.compile("\"relative_humidity_2m\":\\s*([0-9.-]+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new WeatherDataException(
                    "Humidity missing from http response");
        }
        return Double.parseDouble(matcher.group(1));
    }

    private double parseWindSpeed(String json) throws WeatherDataException {
        Pattern pattern = Pattern.compile("\"wind_speed_10m\":\\s*([0-9.-]+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new WeatherDataException(
                    "Wind Speed missing from http response");
        }
        return Double.parseDouble(matcher.group(1));
    }

    private Integer parseWeatherCode(String json) throws WeatherDataException {
        Pattern pattern = Pattern.compile("\"weather_code\":\\s*([0-9.-]+)");
        Matcher matcher = pattern.matcher(json);
        if (!matcher.find()) {
            throw new WeatherDataException(
                    "Weather Code missing from http response");
        }
        return Integer.parseInt(matcher.group(1));
    }

    public WeatherData getCurrentWeather(String city) throws WeatherDataException {
        double lat = 0;
        double lon = 0;
        double temp = 0; // in F
        double humidity = 0;
        double windSpeed = 0; //in mph
        Integer code = 0;

        try (HttpClient client = HttpClient.newHttpClient()) {
            String url = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                    city + "&format=json";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new WeatherDataException(
                        "⚠️ Weather service returned HTTP "
                                + response.statusCode());
            }
            lat = parseLat(response.body());
            lon = parseLon(response.body());

            //now that we have lat and lon, we can find the weather info we're looking for, namely
            //humidity, temp, and wind speed
        } catch (IOException e) {
            throw new WeatherDataException(
                    "Could not contact weather service", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherDataException(
                    "Weather request interrupted", e);
        }

        try (HttpClient client = HttpClient.newHttpClient()) {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" +
                    lat + "&longitude=" +
                    lon + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,weather_code&wind_speed_unit=mph&temperature_unit=fahrenheit";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new WeatherDataException(
                        "⚠️ Weather service returned HTTP "
                                + response.statusCode());
            }
            temp = parseTemperature(response.body());
            humidity = parseHumidity(response.body());
            windSpeed = parseWindSpeed(response.body());
            code = parseWeatherCode(response.body());




        } catch (IOException e) {
            throw new WeatherDataException(
                    "Could not contact weather service", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherDataException(
                    "Weather request interrupted", e);
        }
        return new WeatherData(city, temp, humidity, windSpeed, code);
    }
}

