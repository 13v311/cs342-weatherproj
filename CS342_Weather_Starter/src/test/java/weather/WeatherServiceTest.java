package weather;

import org.junit.jupiter.api.Test;
import weather.exception.WeatherDataException;
import weather.model.CityPair;
import weather.provider.FakeWeatherDataProvider;
import weather.provider.WeatherDataProvider;
import weather.service.WeatherService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Starter test file.
 *
 * Final requirement: at least 8 meaningful JUnit test methods in this file.
 * Replace/remove this placeholder as you implement the project.
 */
class WeatherServiceTest {
    WeatherDataProvider fakeProvider = new FakeWeatherDataProvider();
    WeatherService service = new WeatherService(fakeProvider);

    @Test
    void verifyChicagoTemp() {
        assertEquals(70, service.getTemperature("Chicago"),
                "Chicago temperatures should be 70F.");
    }

    @Test
    void findExistingLocation() {
        assertNotNull(service.findLocation("Chicago"), "findLocation does not find 'Chicago'.");
    }

    @Test
    void findUnknownLocation() {
        assertNull(service.findLocation("Miami"), "Miami should not exist as a known location.");
    }

    @Test
    void allLocationExist() {
        assertEquals(3, service.getLocations().size(), "There should be no more or less than 3 known locations in this provider.");
    }

    @Test
    void verifyLAHumidity() {
        assertEquals(40, service.getHumidity("Los Angeles"), "Humidity for LA should be 40.");
    }

    @Test
    void verifyNYWindSpeed() {
        assertEquals(8, service.getWindSpeed("New York"), "Wind Speed for New York should be 8mph.");
    }


    @Test
    void caseInsensitiveLookup() {
        assertNotNull(service.findLocation("chicago"), "findLocation does not find 'chicago'.");
    }

    @Test
    void unknownLocationThrowsException() {
        assertThrows(WeatherDataException.class, () -> {
            service.getTemperature("Miami");
        });
    }

    @Test
    void highestTemp() {
        CityPair p = new CityPair("Los Angeles", 80);
        assertEquals(p, service.getHottestCity(), "Hottest city should be Los Angeles at 80F.");
    }
}
