package weather.cli;

import weather.exception.LocationException;
import weather.model.CityPair;
import weather.model.Location;
import weather.service.WeatherService;

import java.util.List;

public class WeatherCLI {
    private final WeatherService service;
    private final List<Location> locationList;

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
        else if(command.equalsIgnoreCase("Locations")){
            locations();
        }
        else if(command.equalsIgnoreCase("Current")){
            if(argument.isEmpty()){
                IO.println("Please use: current <location>");
            }
            else{
                current(argument);
            }
        }
        else if(command.equalsIgnoreCase("Compare")){
            String[] places = argument.split("\\s*,\\s", 2);

            if(places.length < 2 || places[0].isBlank() || places[1].isBlank()){
                IO.println("Please use: compare <location1>, <location2");
            }
            else{
                compare(places[0], places[1]);
            }
        }
        else if(command.equalsIgnoreCase("Summary")){
            if(argument.isEmpty()){
                IO.println("Please use: summary <location>");
            }
            else{
                summary(argument);
            }
        }
        else if(command.equalsIgnoreCase("Hottest")){
            hottest();
        }
        else if(command.equalsIgnoreCase("Exit")){
            return false;
        } else {
            IO.println("Unknown command. Please try again.");
        }
        return true;
    }

    private void help(){
        IO.println("Commands:");
        IO.println("    help");
        IO.println("    locations");
        IO.println("    current <location>");
        IO.println("    compare <location1><location2>");
        IO.println("    summary <location>");
        IO.println("    hottest");
        IO.println("    exit");
    }

    private void locations(){
        IO.println("All Locations:");
        for(Location location: locationList){
            IO.println("    " +location.name());
        }
    }

    private void current(String city){

        try {
            double temp = service.getTemperature(city);
            double humidity = service.getHumidity(city);
            double wind = service.getWindSpeed(city);
            System.out.printf("%s: %.0fF, Humidity: %.0f%%, Wind Speed: %.0fmph%n", city, temp, humidity, wind);
        }
        catch (LocationException e){
            IO.println(e.getMessage());
        }

    }

    private void compare(String city1, String city2){
        try{
            double temp1 = service.getTemperature(city1);
            double humidity1 = service.getHumidity(city1);
            double wind1 = service.getWindSpeed(city1);

            double temp2 = service.getTemperature(city2);
            double humidity2 = service.getHumidity(city2);
            double wind2 = service.getWindSpeed(city2);

            System.out.printf("%s: %.0fF, Humidity: %.0f%%, Wind Speed: %.0fmph%n", city1, temp1, humidity1, wind1);
            System.out.printf("%s: %.0fF, Humidity: %.0f%%, Wind Speed: %.0fmph%n", city2, temp2, humidity2, wind2);
            if(temp1 > temp2){
                System.out.printf("%s is the hotter city%n", city1);
            }
            else{
                System.out.printf("%s is the hotter city%n", city2);
            }
        } catch (LocationException e) {
            IO.println(e.getMessage());
        }

    }

    private void summary(String city){
        try{
            String description = service.getSummary(city);

            IO.println(description);
        }
        catch (LocationException e){
            IO.println(e.getMessage());
        }

    }

    private void hottest(){
        CityPair hotCity = service.getHottestCity();
        System.out.printf("%s is the hottest city with %.0fF%n", hotCity.city(), hotCity.weatherVal());
    }


}

