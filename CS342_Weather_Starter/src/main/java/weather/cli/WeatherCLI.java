package weather.cli;

import weather.service.WeatherService;

import java.util.Map;
import java.util.HashMap;


public class WeatherCLI {
    private final Map<String, Runnable> commands = new HashMap<>();
    private final WeatherService service;

    public WeatherCLI(WeatherService service){
        this.service = service;

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

    }

    private void current(){

    }

    private void compare(){

    }

    private void summary(){

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

