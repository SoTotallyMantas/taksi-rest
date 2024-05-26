package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception;

public class DispatchNotFoundException extends RuntimeException{
    public DispatchNotFoundException(Long id) {
        super("Could not find Dispatch " + id);
    }

    public DispatchNotFoundException(String firstName, String lastName) {
        super("Could not find Dispatch for " + firstName + " " + lastName);
    }

    public DispatchNotFoundException(String number) {
        super("Could not find Dispatch for " + number);
    }
}
