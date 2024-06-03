package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception;

/**
 * This class is responsible for OrderNotFoundException
 * it extends RuntimeException
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long id) {
        super("Could not find Order " + id);
    }

    public OrderNotFoundException(String firstName, String lastName) {
        super("Could not find Order for " + firstName + " " + lastName);
    }

    public OrderNotFoundException(String variable) {
        super("Could not find Order for " + variable);
    }
}
