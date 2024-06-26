package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.*;

/**
 * Public Dispatch Model class
 */
@XmlRootElement(name = "Dispatch")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "firstname", "lastname", "phonenumber", "worknumber"})
@Entity
@Table(name = "dispatch")
public class Dispatch extends AbstractUser {


    private String phoneNumber;
    private String workNumber;

    public Dispatch(String firstname, String lastname, String phoneNumber, String workNumber) {
        setFirstName(firstname);
        setLastName(lastname);
        this.phoneNumber = phoneNumber;
        this.workNumber = workNumber;
    }


    public Dispatch() {

    }

    @Override
    public String toString() {
        return String.format("\t\tDispatch: \n" +
                        "\t\t\tFirst Name:   %s \n" +
                        "\t\t\tLast Name:    %s \n" +
                        "\t\t\tPhone Number: %s \n" +
                        "\t\t\tWork Number:  %s \n",
                getFirstName(), getLastName(), this.phoneNumber, this.workNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @XmlElement(name = "Phone_Number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getWorkNumber() {
        return workNumber;
    }

    @XmlElement(name = "Work_Number")
    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }
}
