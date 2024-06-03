package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlTransient;
import org.springframework.hateoas.RepresentationModel;


/**
 * Abstract User Model class
 */
@XmlTransient
@XmlSeeAlso({Client.class, Dispatch.class, Driver.class})

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractUser extends RepresentationModel<AbstractUser> {

    @jakarta.persistence.Id
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

    public long getId() {
        return id;
    }

    @XmlElement(name = "FirstName")

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    @XmlElement(name = "Lastname")
    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }



}
