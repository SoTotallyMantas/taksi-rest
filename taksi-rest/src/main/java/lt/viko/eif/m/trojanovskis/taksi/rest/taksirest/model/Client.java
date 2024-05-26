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
        setFirstname(firstname);
        setLastname(lastname);
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
                getFirstname(), getLastname(), this.phoneNumber);
    }


    public String getPhonenumber() {
        return phoneNumber;
    }

    @XmlElement(name = "Phone_Number")
    public void setPhonenumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
