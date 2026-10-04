//This is an interface that represents a source of weather information.
package weather.provider;

import weather.exception.WeatherDataException;
import weather.model.WeatherData;

public interface WeatherDataProvider {
    WeatherData getCurrentWeather(String city) throws WeatherDataException;
}