package weather.exception;

public class LocationException extends RuntimeException {

    public LocationException(String message, Throwable cause){
        super(message, cause);
    }

    public LocationException(String message) {
        super(message);
    }
}
