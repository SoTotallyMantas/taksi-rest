package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.QueryHint;
import jakarta.transaction.Transactional;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.ClientNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DispatchNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DriverNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.OrderNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.ClientRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.DispatchRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.DriverRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.OrdersRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Dispatch;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Driver;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents OrderService Object that builds Order Object
 * and does operations with it.
 * This class is responsible for getting, deleting, adding, updating Orders
 * and finding Orders by different parameters.
 * This class is using OrdersRepository, ClientRepository, DispatchRepository, DriverRepository
 * and EntityManager to do operations with Order Objects.
 */
@Service
public class orderService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DispatchRepository dispatchRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private EntityManager entityManager;

    /**
     * This method is used to get all Orders
     * @return List of Orders
     */
   public List<Order> getAllOrders()
   {
         return ordersRepository.findAll();
   }

    /**
     * This method is used to delete Order by ID
     * @param id Order ID
     */
    public void deleteOrderById(Long id)
    {
            ordersRepository.deleteById(id);
    }

    /**
     * This method is used to add new Order
     * @param clientID Client ID
     * @param dispatchID Dispatch ID
     * @param driverID Driver ID
     * @param address Address
     * @return Order Object
     * @throws Exception Throws Exception
     */
    @Transactional
    public Order addNewOrder(Long clientID, Long dispatchID, Long driverID, String address) throws Exception {
        try {

            Client client = clientRepository.findById(clientID).orElseThrow(() -> new ClientNotFoundException(clientID));
            Dispatch dispatch = dispatchRepository.findById(dispatchID).orElseThrow(() -> new DispatchNotFoundException(dispatchID));
            Driver driver = driverRepository.findById(driverID).orElseThrow(() -> new DriverNotFoundException(driverID));


            Order newOrder = new Order();
            newOrder.setClient(client);
            newOrder.setDispatch(dispatch);
            newOrder.setDriver(driver);
            newOrder.setAddress(address);


            return ordersRepository.save(newOrder);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     *  This method is used to find Order by Address
     * @param address Address
     * @return List of Orders
     */
    public List<Order> findByAddress(String address)
    {
        return ordersRepository.findOrderByAddress(address).orElseThrow(() -> new OrderNotFoundException(address));
    }

    /**
     *  This method is used to find Order by Client ID
     * @param id Client ID
     * @return List of Orders
     */
    public Order getOrderById(Long id)
    {
        return ordersRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
    /**
     * This method is used to update Order
     * @param id Order ID
     * @param clientID Client ID
     * @param dispatchID Dispatch ID
     * @param driverID Driver ID
     * @param address Address
     */
    @Transactional
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "false")})
    public void updateOrder(Long id, Long clientID, Long dispatchID, Long driverID, String address) {

        Order updateOrder = getOrderById(id);
        entityManager.refresh(updateOrder);

        Client client = null;
        Dispatch dispatch = null;
        Driver driver = null;

        if (clientID != null) {

            client = clientRepository.findById(clientID).orElseThrow(() -> new ClientNotFoundException(clientID));
            entityManager.refresh(client);
        }
        if (dispatchID != null) {

            dispatch = dispatchRepository.findById(dispatchID).orElseThrow(() -> new DispatchNotFoundException(dispatchID));
            entityManager.refresh(dispatch);
        }
        if (driverID != null) {

            driver = driverRepository.findById(driverID).orElseThrow(() -> new DriverNotFoundException(driverID));
            entityManager.refresh(driver);
        }

        if(driverID !=null) {
            updateOrder.setDriver(driver);
        }
        if (clientID != null) {
            updateOrder.setClient(client);
        }
        if (dispatchID != null) {
            updateOrder.setDispatch(dispatch);
        }
        if (address != null) {
            updateOrder.setAddress(address);
        }

        ordersRepository.save(updateOrder);
    }

    /**
     *  This method is used to find Order by Driver License Plate
     * @param licensePlate License Plate
     * @return List of Orders
     */
   public List<Order> findByLicensePlate(String licensePlate)
    {
        return ordersRepository.findOrderByDriverLicensePlate(licensePlate).orElseThrow(() -> new OrderNotFoundException(licensePlate));
    }

    /**
     * This method is used to find Order by Client Phone Number
     * @param phoneNumber Phone Number
     * @return List of Orders
     */
    public List<Order> findByClientPhoneNumber(String phoneNumber)
    {
        return ordersRepository.findOrderByClientPhoneNumber(phoneNumber).orElseThrow(() -> new OrderNotFoundException(phoneNumber));
    }
    /**
     * This method is used to find Order by Dispatch Phone Number
     * @param phoneNumber Phone Number
     * @return List of Orders
     */
    public List<Order> findByDispatchPhoneNumber(String phoneNumber)
    {
        return ordersRepository.findOrderByDispatchPhoneNumber(phoneNumber).orElseThrow(() -> new OrderNotFoundException(phoneNumber));
    }
    /**
     * This method is used to find Order by Driver Phone Number
     * @param phoneNumber Phone Number
     * @return List of Orders
     */
    public List<Order> findByDriverPhoneNumber(String phoneNumber)
    {
        return ordersRepository.findOrderByDriverPhoneNumber(phoneNumber).orElseThrow(() -> new OrderNotFoundException(phoneNumber));
    }
    /**
     * This method is used to find Order by Client Work Number
     * @param workNumber Work Number
     * @return List of Orders
     */

    public List<Order> findByDispatchWorkNumber(String workNumber)
    {
        return ordersRepository.findOrderByDispatchWorkNumber(workNumber).orElseThrow(() -> new OrderNotFoundException(workNumber));
    }
    /**
     * This method is used to find Order by Client Name
     * @param firstName First Name
     * @param lastName Last Name
     * @return List of Orders
     */
    public List<Order> findByClientName(String firstName, String lastName)
    {
        return ordersRepository.findOrderByClientName(firstName, lastName).orElseThrow(() -> new OrderNotFoundException(firstName, lastName));
    }

    /**
     *  This method is used to find Order by Client First Name
     * @param firstName First Name
     * @return List of Orders
     */
    public List<Order> findByClientFirstName(String firstName)
    {
        return ordersRepository.findOrderByClientFirstName(firstName).orElseThrow(() -> new OrderNotFoundException(firstName));
    }
    /**
     * This method is used to find Order by Client Last Name
     * @param lastName Last Name
     * @return List of Orders
     */
    public List<Order> findByClientLastName(String lastName)
    {
        return ordersRepository.findOrderByClientLastName(lastName).orElseThrow(() -> new OrderNotFoundException(lastName));
    }
    /**
     * This method is used to find Order by Dispatch Name
     * @param firstName First Name
     * @param lastName Last Name
     * @return List of Orders
     */
    public List<Order> findByDispatchName(String firstName, String lastName)
    {
        return ordersRepository.findOrderByDispatchName(firstName, lastName).orElseThrow(() -> new OrderNotFoundException(firstName, lastName));
    }
    /**
     * This method is used to find Order by Dispatch First Name
     * @param firstName First Name
     * @return List of Orders
     */
    public List<Order> findByDispatchFirstName(String firstName)
    {
        return ordersRepository.findOrderByDispatchFirstName(firstName).orElseThrow(() -> new OrderNotFoundException(firstName));
    }
    /**
     * This method is used to find Order by Dispatch Last Name
     * @param lastName Last Name
     * @return List of Orders
     */
    public List<Order> findByDispatchLastName(String lastName)
    {
        return ordersRepository.findOrderByDispatchLastName(lastName).orElseThrow(() -> new OrderNotFoundException(lastName));
    }
    /**
     * This method is used to find Order by Driver Name
     * @param firstName First Name
     * @param lastName Last Name
     * @return List of Orders
     */
    public List<Order> findByDriverName(String firstName, String lastName)
    {
        return ordersRepository.findOrderByDriverName(firstName, lastName).orElseThrow(() -> new OrderNotFoundException(firstName, lastName));
    }
    /**
     * This method is used to find Order by Driver First Name
     * @param firstName First Name
     * @return List of Orders
     */
    public List<Order> findByDriverFirstName(String firstName)
    {
        return ordersRepository.findOrderByDriverFirstName(firstName).orElseThrow(() -> new OrderNotFoundException(firstName));
    }
    /**
     * This method is used to find Order by Driver Last Name
     * @param lastName Last Name
     * @return List of Orders
     */
    public List<Order> findByDriverLastName(String lastName)
    {
        return ordersRepository.findOrderByDriverLastName(lastName).orElseThrow(() -> new OrderNotFoundException(lastName));
    }
}
