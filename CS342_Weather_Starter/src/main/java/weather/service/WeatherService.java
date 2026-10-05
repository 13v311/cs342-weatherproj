package weather.service;

import weather.exception.WeatherDataException;
import weather.model.CityPair;
import weather.model.Location;
import weather.model.WeatherData;
import weather.provider.WeatherDataProvider;
import java.util.ArrayList;
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
            name = name.replace(' ', '+');
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

    public List<Location> getLocations(){
        return new ArrayList<>(cities.keySet());
    }

    public Location findLocation(String name){
        for(Location location : cities.keySet()){
            if(name.equalsIgnoreCase(location.name())){
                return location;
            }

        }
        return null;
    }

    public double getTemperature(String city){
        Location location = findLocation(city);

        WeatherData f = cities.get(location);
        return f.temp();
    }

    public double getHumidity(String city){
        Location location = findLocation(city);
        WeatherData f = cities.get(location);
        return f.humidity();
    }

    public double getWindSpeed(String city){
        Location location = findLocation(city);
        WeatherData f = cities.get(location);
        return f.windSpeed();
    }

    public Integer getCode(String city){
        Location location = findLocation(city);
        WeatherData f = cities.get(location);
        return f.code();
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
