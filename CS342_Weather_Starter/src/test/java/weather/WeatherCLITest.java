package weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import weather.cli.WeatherCLI;
import weather.exception.WeatherDataException;
import weather.provider.FakeWeatherDataProvider;
import weather.provider.WeatherDataProvider;
import weather.service.WeatherService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class WeatherCLITest {
    WeatherDataProvider fakeProvider = new FakeWeatherDataProvider();
    WeatherService service;
    WeatherCLI cli;

    @BeforeEach
    void setup(){
        try{
            service= new WeatherService(fakeProvider);
            cli = new WeatherCLI(service);
        }
        catch (WeatherDataException e){
            fail("Could not create WeatherService: " + e.getMessage());
        }
    }

    @Test
    void invalidCommand() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        cli.processInput("foobar");

        String result = output.toString();

        assertTrue(
                result.contains("Unknown command. Please try again."),
                "Invalid command should produce an error message"
        );
    }

    @Test
    void helpCommand() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        cli.processInput("help");

        String result = output.toString();

        assertTrue(result.contains("Commands:"));
        assertTrue(result.contains("help"));
        assertTrue(result.contains("locations"));
        assertTrue(result.contains("current <location>"));
        assertTrue(result.contains("compare <location1><location2>"));
        assertTrue(result.contains("summary <location>"));
        assertTrue(result.contains("hottest"));
        assertTrue(result.contains("exit"));
    }

    @Test
    void locationsCommand() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        cli.processInput("locations");

        String result = output.toString();

        assertTrue(result.contains("Chicago"));
        assertTrue(result.contains("New York"));
        assertTrue(result.contains("Los Angeles"));
    }

    @Test
    void currentCommand() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        cli.processInput("current Chicago");

        String result = output.toString();

        assertTrue(result.contains("Chicago: 70F, Humidity: 50%, Wind Speed: 10mph"));
    }

    @Test
    void compareCommand() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        cli.processInput("compare Chicago, Los Angeles");

        String result = output.toString();

        assertTrue(
                result.contains("Chicago: 70F, Humidity: 50%, Wind Speed: 10mph"),
                "Comparison should display Chicago's weather"
        );

        assertTrue(
                result.contains("Los Angeles: 80F, Humidity: 40%, Wind Speed: 5mph"),
                "Comparison should display Los Angeles's weather"
        );

        assertTrue(
                result.contains("Los Angeles is the hotter city"),
                "Comparison should show Los Angeles is hotter"
        );
    }

    @Test
    void summaryCommand() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        cli.processInput("summary Chicago");

        String result = output.toString();

        assertTrue(
                result.contains("Clear sky"),
                "Should show Clear Skies since the code is 0"
        );
    }

    //Additional functionality test
    @Test
    void hottestCommand(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        cli.processInput("hottest");
        String result  = output.toString();

        assertTrue(
                result.contains("Los Angeles is the hottest city with 80F"),
                "Out of the three, Los Angeles is the hotter city"
        );

    }

    @Test
    void exitCommand() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));


        boolean b = cli.processInput("exit");

        assertFalse(b, "Invalid command should produce an error message");
    }
}
