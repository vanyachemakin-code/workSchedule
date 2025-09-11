package Schedule.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super("Employee not found");
    }
}
