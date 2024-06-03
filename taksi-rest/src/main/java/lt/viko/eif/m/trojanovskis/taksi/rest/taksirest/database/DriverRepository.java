package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Represents DriverRepository Object that extends JpaRepository
 * This class is designated to communicate with database
 * and perform CRUD operations
 * on Driver objects
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    // Find By First Name and Last Name
    Optional<List<Driver>> findByFirstNameAndLastName(String firstName, String lastName);
    // Find By Phone Number
    Optional<Driver>  findByPhoneNumber(String phoneNumber);

    // Find By License Plate
    Optional<List<Driver>>  findByLicensePlate(String licensePlate);
    // Find By First Name
    Optional<List<Driver>>  findByFirstName(String firstName);
    // Find By Last Name
    Optional<List<Driver>>  findByLastName(String lastName);
}
