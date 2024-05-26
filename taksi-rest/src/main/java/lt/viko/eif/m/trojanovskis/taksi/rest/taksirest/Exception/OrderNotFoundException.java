package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception;


public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Could not find Order " + id);
    }

    public OrderNotFoundException(String firstName, String lastName) {
        super("Could not find Order for " + firstName + " " + lastName);
    }

    public OrderNotFoundException(String licensePlate) {
        super("Could not find Order for " + licensePlate);
    }
}
