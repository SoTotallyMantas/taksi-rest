package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    // Find By First Name and Last Name
    Optional<Driver> findByFirstNameAndLastName(String firstName, String lastName);
    // Find By Phone Number
    Optional<Driver>  findByPhoneNumber(String phoneNumber);

    // Find By License Plate
    Optional<Driver>  findByLicensePlate(String licensePlate);
}
