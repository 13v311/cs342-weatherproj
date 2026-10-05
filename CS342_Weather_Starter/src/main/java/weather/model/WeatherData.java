package weather.model;
public record WeatherData(String city,
                          double temp,
                          double humidity,
                          double windSpeed,
                          Integer code) {};