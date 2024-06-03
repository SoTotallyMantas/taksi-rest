package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.*;


/**
 * Public Client Model class
 */
@XmlRootElement(name = "Client")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "firstname", "lastname", "phonenumber"})
@Entity
@Table(name = "client")

public class Client extends AbstractUser {


    private String phoneNumber;

    public Client(String firstname, String lastname, String phoneNumber) {
        setFirstName(firstname);
        setLastName(lastname);
        this.phoneNumber = phoneNumber;
    }


    public Client() {

    }

    @Override
    public String toString() {
        return String.format("\t\tClient: \n" +
                        "\t\t\tFirst Name:   %s \n" +
                        "\t\t\tLast Name:    %s \n" +
                        "\t\t\tPhone Number: %s  \n",
                getFirstName(), getLastName(), this.phoneNumber);
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    @XmlElement(name = "Phone_Number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
