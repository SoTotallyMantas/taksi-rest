package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlTransient;
import org.springframework.data.annotation.Id;




/**
 * Abstract User Model class
 */
@XmlTransient
@XmlSeeAlso({Client.class, Dispatch.class, Driver.class})

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractUser {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int id;
    @Column(name = "`firstName`")
    private String firstName;
    @Column(name = "`lastName`")
    private String lastName;


    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "FirstName")

    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    @XmlElement(name = "Lastname")
    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getFirstname() {
        return firstName;
    }

    public String getLastname() {
        return lastName;
    }





    public int getId() {
        return id;
    }
}
