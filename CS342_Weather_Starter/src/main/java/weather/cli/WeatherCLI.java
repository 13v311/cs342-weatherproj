package weather.cli;

import weather.model.Location;
import weather.model.WeatherData;
import weather.service.WeatherService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;


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

    public void readInput(){
        String input = IO.readln(">");

        if(input.isEmpty()){
            return;
        }

        String[] parts = input.split("\\+", 2);
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
            String[] places = argument.split(" ", 1);
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
            exit();
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
        System.out.printf("%s: %fF, Humidity: %f, Wind Speed: %f", city, temp, humidity, wind);
    }

    private void compare(String city1, String city2){
        double temp1 = service.getTemperature(city1);
        double humidity1 = service.getHumidity(city1);
        double wind1 = service.getWindSpeed(city1);

        double temp2 = service.getTemperature(city2);
        double humidity2 = service.getHumidity(city2);
        double wind2 = service.getWindSpeed(city2);

        //print the thing
        System.out.printf("%s: %fF, Humidity: %f, Wind Speed: %f", city1, temp1, humidity1, wind1);
        System.out.printf("%s: %fF, Humidity: %f, Wind Speed: %f", city2, temp2, humidity2, wind2);
    }

    private void summary(String city){
        //Weather code like there's an actual code set of summaries

        IO.println("just a placeholder");
    }

    private void exit(){
        //gotta stop loop in main which would stop program

    }

    //is this needed?
    static void renderBar(String city, double temp) {  //Instead of these parameters, we pass the records
        System.out.printf("%-15s | %5.1f°F [", city, temp);
        int barLength = (int) Math.max(0, temp / 2);

        for (int j = 0; j < barLength; j++) {
            System.out.print("■");
        }
        for (int j = barLength; j < 40; j++) {
            System.out.print(" ");
        }
        //println("]");
    }
}

