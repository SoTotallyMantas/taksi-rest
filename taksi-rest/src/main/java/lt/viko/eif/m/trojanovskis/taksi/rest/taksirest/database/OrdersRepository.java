package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Represents OrdersRepository Object that extends JpaRepository
 * This class is designated to communicate with database
 * and perform CRUD operations
 * on Order objects
 */
@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {


    // Add new order to database
    @Query(value = "INSERT into  Order o (o.address, o.client.id, o.dispatch.id, o.driver.id) VALUES (:address, :clientID, :dispatchID, :driverID)", nativeQuery = true)
    void addNewOrder(@Param("address") String address, @Param("clientID") Long clientID, @Param("dispatchID") Long dispatchID, @Param("driverID") Long driverID);

    // Update Order Address, ClientID, DispatchID, DriverID
    @Query("UPDATE Order o SET o.address = :address, o.client.id = :clientID, o.dispatch.id = :dispatchID, o.driver.id = :driverID WHERE o.id = :id")
    void updateOrder(@Param("id") Long id, @Param("address") String address, @Param("clientID") Long clientID, @Param("dispatchID") Long dispatchID, @Param("driverID") Long driverID);

    // Find By Client Name
    @Query("SELECT o FROM Order o JOIN o.client c WHERE c.firstName = :firstname AND c.lastName = :lastname")
    Optional<List<Order>> findOrderByClientName(@Param("firstname") String firstname, @Param("lastname") String lastname);
    // Find By Driver Name
    @Query("SELECT o FROM Order o JOIN o.driver d WHERE d.firstName = :firstname AND d.lastName = :lastname")
    Optional<List<Order>> findOrderByDriverName(@Param("firstname") String firstname, @Param("lastname") String lastname);
    // Find By Dispatch Name
    @Query("SELECT o FROM Order o JOIN o.dispatch d WHERE d.firstName = :firstname AND d.lastName = :lastname")
    Optional<List<Order>> findOrderByDispatchName(@Param("firstname") String firstname, @Param("lastname") String lastname);
    // Find By Driver License Plate
    @Query("SELECT o FROM Order o JOIN o.driver d WHERE d.licensePlate = :licensePlate")
    Optional<List<Order>> findOrderByDriverLicensePlate(@Param("licensePlate") String licensePlate);
    // Find By Dispatch Phone Number
    @Query("SELECT o FROM Order o JOIN o.dispatch d WHERE d.phoneNumber = :phoneNumber")
    Optional<List<Order>> findOrderByDispatchPhoneNumber(@Param("phoneNumber") String phoneNumber);
    // Find By Dispatch Work Number
    @Query("SELECT o FROM Order o JOIN o.dispatch d WHERE d.workNumber = :workNumber")
    Optional<List<Order>> findOrderByDispatchWorkNumber(@Param("workNumber") String workNumber);

    // Find By Client Phone Number
    @Query("SELECT o FROM Order o JOIN o.client c WHERE c.phoneNumber = :phoneNumber")
    Optional<List<Order>> findOrderByClientPhoneNumber(@Param("phoneNumber") String phoneNumber);

    // Find By Driver Phone Number
    @Query("SELECT o FROM Order o JOIN o.driver d WHERE d.phoneNumber = :phoneNumber")
    Optional<List<Order>> findOrderByDriverPhoneNumber(@Param("phoneNumber") String phoneNumber);

    // Find By Dispatch First Name
    @Query("SELECT o FROM Order o JOIN o.dispatch d WHERE d.firstName = :dispatchFirstName")
    Optional<List<Order>> findOrderByDispatchFirstName(@Param("dispatchFirstName") String dispatchFirstName);
    // Find By Dispatch Last Name
    @Query("SELECT o FROM Order o JOIN o.dispatch d WHERE d.lastName = :dispatchLastName")
    Optional<List<Order>> findOrderByDispatchLastName(@Param("dispatchLastName") String dispatchLastName);
    // Find By Driver First Name

    @Query("SELECT o FROM Order o JOIN o.driver d WHERE d.firstName = :driverFirstName")
    Optional<List<Order>> findOrderByDriverFirstName(@Param("driverFirstName") String driverFirstName);

    // Find By Driver Last Name
    @Query("SELECT o FROM Order o JOIN o.driver d WHERE d.lastName = :driverLastName")
    Optional<List<Order>> findOrderByDriverLastName(@Param("driverLastName") String driverLastName);
    // Find By Client First Name
    @Query("SELECT o FROM Order o JOIN o.client c WHERE c.firstName = :clientFirstName")
    Optional<List<Order>> findOrderByClientFirstName(@Param("clientFirstName") String clientFirstName);
    // Find By Client Last Name
    @Query("SELECT o FROM Order o JOIN o.client c WHERE c.lastName = :clientLastName")
    Optional<List<Order>> findOrderByClientLastName(@Param("clientLastName") String clientLastName);

    // Find By Address
    @Query("SELECT o FROM Order o WHERE o.address = :address")
    Optional<List<Order>> findOrderByAddress(@Param("address") String address);


}



