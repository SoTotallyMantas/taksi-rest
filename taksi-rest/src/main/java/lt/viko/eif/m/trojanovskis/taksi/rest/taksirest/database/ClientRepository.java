package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Represents ClientRepository Object that extends JpaRepository
 * This class is designated to communicate with database
 * and perform CRUD operations
 * on Client objects
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Find By First Name and Last Name
    Optional<List<Client>> findByFirstNameAndLastName(String firstName, String lastName);
    // Find By Phone Number
    Optional<Client> findByPhoneNumber(String phoneNumber);
    // Find By First Name

    Optional<List<Client>> findByFirstName(String firstName);
    // Find By Last Name
    Optional<List<Client>> findByLastName(String lastName);



}
