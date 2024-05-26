package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Dispatch;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Long> {

    // Find By First Name and Last Name
    Optional<Dispatch> findByFirstNameAndLastName(String firstName, String lastName);
    // Find By Phone Number
    Optional<Dispatch> findByPhoneNumber(String phoneNumber);
    // Find By Work Number
    Optional<Dispatch> findByWorkNumber(String workNumber);



}
