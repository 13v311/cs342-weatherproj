
package weather.provider;

import weather.model.WeatherData;

public class FakeWeatherDataProvider implements WeatherDataProvider {

    @Override
    public WeatherData getCurrentWeather(String city) {
        if (city.equals("Chicago")) {
            return new WeatherData("Chicago", 70, 50, 10, 0);
        }

        if (city.equals("Los Angeles")) { //we use + since that is how it is formatted from WeatherService
            return new WeatherData("Los Angeles", 80, 40, 5, 2);
        }

        if (city.equals("New York")) {
            return new WeatherData("New York", 60, 60, 8, 3);
        }

        return null;
    }
}
