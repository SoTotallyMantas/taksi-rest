package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.*;

/**
 * Public Driver model class
 */
@XmlRootElement(name = "Driver")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "firstname", "lastname", "phonenumber", "licenseplate"})
@Entity
@Table(name = "driver")
public class Driver extends AbstractUser {


    private String phoneNumber;
    private String licensePlate;

    public Driver(String firstname, String lastname, String phoneNumber, String licensePlate) {
        setFirstName(firstname);
        setLastName(lastname);
        this.phoneNumber = phoneNumber;
        this.licensePlate = licensePlate;
    }


    public Driver() {

    }

    @Override
    public String toString() {
        return String.format("\t\tDriver: \n" +
                        "\t\t\tFirst Name:   %s \n" +
                        "\t\t\tLast Name:    %s \n" +
                        "\t\t\tPhone Number: %s  \n" +
                        "\t\t\tLicense Plate:    %s \n",
                getFirstName(), getLastName(), this.phoneNumber, this.licensePlate);
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    @XmlElement(name = "Phone_Number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    @XmlElement(name = "License_Plate")
    public void setLicensePlate(String licenseplate) {
        this.licensePlate = licenseplate;
    }
}
