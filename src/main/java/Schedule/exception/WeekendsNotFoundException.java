package Schedule.exception;

public class WeekendsNotFoundException extends RuntimeException {
    public WeekendsNotFoundException() {
        super("Weekends not found");
    }
}
