package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Find By First Name and Last Name
    Optional<Client> findByFirstNameAndLastName(String firstName, String lastName);
    // Find By Phone Number
    Optional<Client> findByPhoneNumber(String phoneNumber);



}
