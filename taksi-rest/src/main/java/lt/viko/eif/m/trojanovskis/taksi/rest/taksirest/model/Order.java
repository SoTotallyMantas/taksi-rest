package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import org.springframework.hateoas.RepresentationModel;


/**
 * Order Model class
 */
@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "address", "client", "driver", "dispatch"})
@Entity
@Table(name = "orders")
public class Order extends RepresentationModel<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    private String address;
    @OneToOne(targetEntity = Client.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToOne(targetEntity = Driver.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @OneToOne(targetEntity = Dispatch.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dispatch_id")
    private Dispatch dispatch;

    public Order() {

    }



    @Override
    public String toString() {
        return String.format("Order: \n" +
                        "\tAdress: %s \n" +
                        "%s " +
                        "%s " +
                        "%s ",
                this.address, this.client, this.driver, this.dispatch);
    }

    public long getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Dispatch getDispatch() {
        return dispatch;
    }

    public void setDispatch(Dispatch dispatch) {
        this.dispatch = dispatch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }






}
