package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Dispatch;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Represents DispatchRepository Object that extends JpaRepository
 * This class is designated to communicate with database
 * and perform CRUD operations
 * on Dispatch objects
 */
@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Long> {

    // Find By First Name and Last Name
    Optional<List<Dispatch>> findByFirstNameAndLastName(String firstName, String lastName);
    // Find By Phone Number
    Optional <List <Dispatch>> findByPhoneNumber(String phoneNumber);
    // Find By Work Number
    Optional <List <Dispatch>> findByWorkNumber(String workNumber);
    // Find By First Name
    Optional<List<Dispatch>> findByFirstName(String firstName);
    // Find By Last Name
    Optional<List<Dispatch>> findByLastName(String lastName);




}
