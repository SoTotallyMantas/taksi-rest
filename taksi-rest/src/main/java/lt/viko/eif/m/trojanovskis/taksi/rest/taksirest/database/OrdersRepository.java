package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database;



import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Represents OrdersRepository Object that extends JpaRepository
 * This class is designated to communicate with database
 * and perform CRUD operations
 * on Order objects
 */
@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {

    // Find By Client Name
    @Query("SELECT o FROM Order o JOIN o.client c WHERE c.firstName = :firstname AND c.lastName = :lastname")
    Optional<Order> findOrderByClientName(@Param("firstname") String firstname, @Param("lastname") String lastname);
    // Find By Driver Name
    @Query("SELECT o FROM Order o JOIN o.driver d WHERE d.firstName = :firstname AND d.lastName = :lastname")
    Optional<Order> findOrderByDriverName(@Param("firstname") String firstname, @Param("lastname") String lastname);
    // Find By Dispatch Name
    @Query("SELECT o FROM Order o JOIN o.dispatch d WHERE d.firstName = :firstname AND d.lastName = :lastname")
    Optional<Order> findOrderByDispatchName(@Param("firstname") String firstname, @Param("lastname") String lastname);
    // Find By Driver License Plate
    @Query("SELECT o FROM Order o JOIN o.driver d WHERE d.licensePlate = :licensePlate")
    Optional<Order> findOrderByDriverLicensePlate(@Param("licensePlate") String licensePlate);
    // Find By Dispatch Phone Number
    @Query("SELECT o FROM Order o JOIN o.dispatch d WHERE d.phoneNumber = :phoneNumber")
    Optional<Order> findOrderByDispatchPhoneNumber(@Param("phoneNumber") String phoneNumber);
    // Find By Dispatch Work Number
    @Query("SELECT o FROM Order o JOIN o.dispatch d WHERE d.workNumber = :workNumber")
    Optional<Order> findOrderByDispatchWorkNumber(@Param("workNumber") String workNumber);

    // Find By Client Phone Number
    @Query("SELECT o FROM Order o JOIN o.client c WHERE c.phoneNumber = :phoneNumber")
    Optional<Order> findOrderByClientPhoneNumber(@Param("phoneNumber") String phoneNumber);

    // Find By Driver Phone Number
    @Query("SELECT o FROM Order o JOIN o.driver d WHERE d.phoneNumber = :phoneNumber")
    Optional<Order> findOrderByDriverPhoneNumber(@Param("phoneNumber") String phoneNumber);


}



