package weather.service;

import weather.exception.LocationException;
import weather.exception.WeatherDataException;
import weather.model.CityPair;
import weather.model.Location;
import weather.model.WeatherData;
import weather.provider.WeatherDataProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherService {
    private final WeatherDataProvider provider;

    private final Map<Location, WeatherData> cities = new HashMap<>();

    private final Map<Integer, String> weatherCodes = Map.ofEntries(
            Map.entry(0, "Clear sky"),
            Map.entry(1, "Mainly clear"),
            Map.entry(2, "Partly cloudy"),
            Map.entry(3, "Overcast"),
            Map.entry(45, "Fog"),
            Map.entry(48, "Depositing rime fog"),
            Map.entry(51, "Light drizzle"),
            Map.entry(53, "Moderate drizzle"),
            Map.entry(55, "Dense drizzle"),
            Map.entry(56, "Light freezing drizzle"),
            Map.entry(57, "Dense freezing drizzle"),
            Map.entry(61, "Slight rain"),
            Map.entry(63, "Moderate rain"),
            Map.entry(65, "Heavy rain"),
            Map.entry(66, "Light freezing rain"),
            Map.entry(67, "Heavy freezing rain"),
            Map.entry(71, "Slight snowfall"),
            Map.entry(73, "Moderate snowfall"),
            Map.entry(75, "Heavy snowfall"),
            Map.entry(77, "Snow grains"),
            Map.entry(80, "Slight rain showers"),
            Map.entry(81, "Moderate rain showers"),
            Map.entry(82, "Violent rain showers"),
            Map.entry(85, "Slight snow showers"),
            Map.entry(86, "Heavy snow showers"),
            Map.entry(95, "Thunderstorm"),
            Map.entry(96, "Thunderstorm with slight hail"),
            Map.entry(97, "Heavy thunderstorm"),
            Map.entry(99, "Thunderstorm with heavy hail")
    );


    public WeatherService(WeatherDataProvider provider) throws WeatherDataException {
        this.provider = provider;

        List<Location> locations = List.of(
                new Location("Chicago"),
                new Location("Los Angeles"),
                new Location("New York")
        );

        for(Location location : locations) {
            String name = location.name();
            WeatherData data = null;
            data = this.provider.getCurrentWeather(name);
            this.cities.put(location, data);
        }
    }

    public List<Location> getLocations(){
        return new ArrayList<>(cities.keySet());
    }

    public Location findLocation(String name) {

        for(Location location : cities.keySet()) {
            if (name.equalsIgnoreCase(location.name())) {
                return location;
            }
        }
        throw new LocationException("Unknown location: " + name);
    }

    public double getTemperature(String city){
        Location location = findLocation(city);
        WeatherData f = cities.get(location);
        return f.temp();
    }

    public double getHumidity(String city) {
        Location location = findLocation(city);
        WeatherData f = cities.get(location);
        return f.humidity();
    }

    public double getWindSpeed(String city) {
        Location location = findLocation(city);
        WeatherData f = cities.get(location);
        return f.windSpeed();
    }

    public String getSummary(String city){
        Location location = findLocation(city);
        WeatherData f = cities.get(location);
        return weatherCodes.get(f.code());
    }

    public CityPair getHottestCity() {
        double highestTemp = 0;
        String city = "";
        List<Location> allLocations = getLocations();
        for(Location location : allLocations) {
            WeatherData f = cities.get(location);
            if(highestTemp < f.temp()) {
                highestTemp = f.temp();
                city = f.city();
            }
        }
        return new CityPair(city, highestTemp);
    }

}
