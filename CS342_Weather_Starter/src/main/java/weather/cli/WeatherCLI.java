package weather.cli;

import weather.model.Location;
//import weather.model.WeatherData;
import weather.service.WeatherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WeatherCLI {
    private final WeatherService service;
    private final List<Location> locationList;
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

    public WeatherCLI(WeatherService service){
        this.service = service;

        locationList = this.service.getLocations();
    }

    public void printTitle(){
        IO.println("Weather Information Service");
    }

    public String readInput() {
        return IO.readln(">");
    }

    public boolean processInput(String input){

        if(input.isEmpty()){
            return true;
        }

        String[] parts = input.split("\\s+", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";

        if(command.equalsIgnoreCase("Help")){
            help();
        }

        if(command.equalsIgnoreCase("Locations")){
            locations();
        }

        if(command.equalsIgnoreCase("Current")){
            if(argument.isEmpty()){
                IO.println("Please use: current <location>");
            }
            else{
                current(argument);
            }
        }

        if(command.equalsIgnoreCase("Compare")){
            String[] places = argument.split("\\s+", 2);

            if(places.length < 2 || places[0].isBlank() || places[1].isBlank()){
                IO.println("Please use: compare <location1> <location2");
            }
            else{
                compare(places[0], places[1]);
            }
        }

        if(command.equalsIgnoreCase("Summary")){
            if(argument.isEmpty()){
                IO.println("Please use: summary <location>");
            }
            else{
                summary(argument);
            }
        }

        if(command.equalsIgnoreCase("Exit")){
            return false;
        } else {
            IO.println("Unknown command. Please try again.");
            return true;
        }

    }

    private void help(){
        IO.println("Commands:");
        IO.println("    help");
        IO.println("    locations");
        IO.println("    current <location>");
        IO.println("    compare <location1><location2>");
        IO.println("    summary <location>");
        IO.println("    exit");
    }

    private void locations(){
        IO.println("All Locations:");
        for(Location location: locationList){
            IO.println(location.name());
        }
    }

    private void current(String city){
        double temp = service.getTemperature(city);
        double humidity = service.getHumidity(city);
        double wind = service.getWindSpeed(city);

        //print the thing
        System.out.printf("%s: %.0fF, Humidity: %.0f%%, Wind Speed: %.0fmph%n", city, temp, humidity, wind);
    }

    private void compare(String city1, String city2){
        double temp1 = service.getTemperature(city1);
        double humidity1 = service.getHumidity(city1);
        double wind1 = service.getWindSpeed(city1);

        double temp2 = service.getTemperature(city2);
        double humidity2 = service.getHumidity(city2);
        double wind2 = service.getWindSpeed(city2);

        //print the thing
        System.out.printf("%s: %.0fF, Humidity: %.0f%%, Wind Speed: %.0fmph%n", city1, temp1, humidity1, wind1);
        System.out.printf("%s: %.0fF, Humidity: %.0f%%, Wind Speed: %.0fmph%n", city2, temp2, humidity2, wind2);
    }

    private void summary(String city){
        Integer code = service.getCode(city);
        String description = weatherCodes.get(code);

        IO.println(description);
    }


}

