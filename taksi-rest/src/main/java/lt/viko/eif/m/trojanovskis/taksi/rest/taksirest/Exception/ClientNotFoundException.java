package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception;
/**
 * This class is responsible for ClientNotFoundException
 * it extends RuntimeException
 */
public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(Long id) {
        super("Could not find Client " + id);
    }

    public ClientNotFoundException(String firstName, String lastName) {
        super("Could not find Client for " + firstName + " " + lastName);
    }

    public ClientNotFoundException(String phoneNumber) {
        super("Could not find Client for " + phoneNumber);
    }
}
