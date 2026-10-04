package weather;

import weather.cli.WeatherCLI;
import weather.provider.OpenMeteoWeatherProvider;
import weather.service.WeatherService;

//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static java.lang.IO.println;
//
//// STARTER CODE:
//// This class intentionally contains several responsibilities.
//// Refactor it into the required model, provider, service, and CLI packages.
//record TargetLocation(String city, String lat, String lon) {}

public class Main {

    public static void main(String[] args) {
        OpenMeteoWeatherProvider open = new OpenMeteoWeatherProvider();
        WeatherService service = new WeatherService(open);
        WeatherCLI console = new WeatherCLI(service);

        console.printTitle();
        while(true){
            console.readInput();
        }

    }
}
