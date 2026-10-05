package weather;

import weather.cli.WeatherCLI;
import weather.provider.OpenMeteoWeatherProvider;
import weather.service.WeatherService;


public class Main {

    public static void main(String[] args) {
        OpenMeteoWeatherProvider open = new OpenMeteoWeatherProvider();
        WeatherService service = new WeatherService(open);
        WeatherCLI console = new WeatherCLI(service);

        console.printTitle();
        String input = console.readInput();
        while(!input.isEmpty()){
            if(!console.processInput(input)) {break;}
            input = console.readInput();
        }

    }
}
