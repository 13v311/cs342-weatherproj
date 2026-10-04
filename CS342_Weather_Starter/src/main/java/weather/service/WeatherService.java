package weather.service;

import weather.exception.WeatherDataException;
import weather.model.Location;
import weather.model.WeatherData;
import weather.provider.WeatherDataProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class WeatherService {
    private final WeatherDataProvider provider;

    private final Map<Location, WeatherData> cities = new HashMap<>();

    public WeatherService(WeatherDataProvider provider){
        this.provider = provider;

        List<Location> locations = List.of(
                new Location("Chicago"),
                new Location("Los Angeles"),
                new Location("New York")
        );

        for(Location location : locations) {
            String name = location.name();
            WeatherData data = null;
            try{
                 data = this.provider.getCurrentWeather(name);
            }
            catch (WeatherDataException e){
                IO.println("Failure to get weather for:" + e.getMessage());
                exit(1);
            }
            this.cities.put(location, data);
        }
    }

    public double getTemperature(Location location){
        WeatherData f = cities.get(location);
        return f.temp();
    }

    public double getHumidity(Location location){
        WeatherData f = cities.get(location);
        return f.humidity();
    }

    public double getWindSpeed(Location location){
        WeatherData f = cities.get(location);
        return f.windSpeed();
    }


}
