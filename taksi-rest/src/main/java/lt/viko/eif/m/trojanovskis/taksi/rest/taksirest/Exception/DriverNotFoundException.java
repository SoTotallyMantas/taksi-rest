package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception;

public class DriverNotFoundException extends RuntimeException{
    public DriverNotFoundException(Long id) {
        super("Could not find Driver " + id);
    }

    public DriverNotFoundException(String firstName, String lastName) {
        super("Could not find Driver for " + firstName + " " + lastName);
    }

    public DriverNotFoundException(String licensePlate) {
        super("Could not find Driver for " + licensePlate);
    }




}
