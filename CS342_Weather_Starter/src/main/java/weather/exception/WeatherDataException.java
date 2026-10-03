package weather.exception;
public class WeatherDataException extends Exception {
    public WeatherDataException(
            String message, Throwable cause) {
        super(message, cause);
    }

    public WeatherDataException(String message) {
        super(message);
    }
}