package weather.cli;

import weather.model.Location;
import weather.model.WeatherData;
import weather.service.WeatherService;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class WeatherCLI {
    private final Map<String, Runnable> commands = new HashMap<>();
    private final WeatherService service;
    private final List<Location> locationList;

    public WeatherCLI(WeatherService service){
        this.service = service;

        locationList = this.service.getLocations();

        commands.put("help", this::help);
        commands.put("locations", this::locations);
        commands.put("current", this::current);
        commands.put("compare", this::compare);
        commands.put("summary", this::summary);
        commands.put("exit", this::exit);

        //then do something with weather sercice which idk right now
    }

    public void printTitle(){
        IO.println("Weather Information Service");
    }

    public void readInput(){
        String input = IO.readln(">");

        Runnable command = commands.get(input);

        if(command != null){
            command.run();
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
    }

    private void compare(String city1, String city2){
        double temp1 = service.getTemperature(city1);
        double humidity1 = service.getHumidity(city1);
        double wind1 = service.getWindSpeed(city1);

        double temp2 = service.getTemperature(city2);
        double humidity2 = service.getHumidity(city2);
        double wind2 = service.getWindSpeed(city2);

        //print the thing
    }

    private void summary(String city){
        //Weather code like there's an actual code set of summaries

        //print the thing
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

