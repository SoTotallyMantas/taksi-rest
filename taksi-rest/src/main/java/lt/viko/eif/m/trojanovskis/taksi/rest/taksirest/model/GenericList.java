package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model;


import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;

import java.util.List;

/**
 * Generic model class wrapper for marshalling
 */
@XmlRootElement(name = "OrderList")
@XmlSeeAlso({Order.class, Driver.class, Client.class, Dispatch.class})
public class GenericList<T> {
    private List<T> Data;

    public GenericList() {
    }

    @XmlAnyElement(lax = true)
    public List<T> getData() {
        return Data;
    }

    public void setData(List<T> Data) {
        this.Data = Data;
    }


}
