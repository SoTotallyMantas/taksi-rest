package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.ModelAssambler;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller.DriverController;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Driver;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Represents DriverAssembler Object that implements RepresentationModelAssembler
 * for Driver class
 * This class is responsible for creating links for Driver class
 * and adding them to JSON response
 */
@Component
public class DriverAssembler implements RepresentationModelAssembler<Driver, EntityModel<Driver>> {

    public String rel;

    /**
     * Method to create links for Driver class
     * and add them to JSON response
     * depending on the rel
     *
     * @param driver Driver object
     * @return EntityModel<Driver> returns Driver object with links
     */

    @Override
    public EntityModel<Driver> toModel(Driver driver) {
        return EntityModel.of(driver,
                !Objects.equals(rel, "all") ? linkTo(methodOn(DriverController.class).all()).withRel("Find all drivers")
                        : linkTo(methodOn(DriverController.class).all()).withSelfRel(),

                !Objects.equals(rel, "findId") ? linkTo(methodOn(DriverController.class).getDriverById(driver.getId())).withRel("Get driver by id")
                        : linkTo(methodOn(DriverController.class).getDriverById(driver.getId())).withSelfRel(),

                !Objects.equals(rel, "findDriverPhoneNumber") ? linkTo(methodOn(DriverController.class).findDriverByPhoneNumber(driver.getPhoneNumber())).withRel("find driver by phone number")
                        : linkTo(methodOn(DriverController.class).findDriverByPhoneNumber(driver.getPhoneNumber())).withSelfRel(),

                !Objects.equals(rel, "findDriverLicensePlate") ? linkTo(methodOn(DriverController.class).findDriverByLicensePlate(driver.getLicensePlate())).withRel("find driver by license plate")
                        : linkTo(methodOn(DriverController.class).findDriverByLicensePlate(driver.getLicensePlate())).withSelfRel(),

                !Objects.equals(rel, "findDriverName") ? linkTo(methodOn(DriverController.class).findDriverByName(driver.getFirstName(), driver.getLastName())).withRel("find driver by name")
                        : linkTo(methodOn(DriverController.class).findDriverByName(driver.getFirstName(), driver.getLastName())).withSelfRel(),

                !Objects.equals(rel, "delete") ? linkTo(methodOn(DriverController.class).deleteDriver(driver.getId())).withRel("delete")
                        : linkTo(methodOn(DriverController.class).deleteDriver(driver.getId())).withSelfRel(),

                !Objects.equals(rel, "update") ? linkTo(methodOn(DriverController.class).updateDriver(driver.getId(), driver.getPhoneNumber(), driver.getFirstName(), driver.getLastName(), driver.getLicensePlate())).withRel("update")
                        : linkTo(methodOn(DriverController.class).updateDriver(driver.getId(), driver.getPhoneNumber(), driver.getFirstName(), driver.getLastName(), driver.getLicensePlate())).withSelfRel(),

                !Objects.equals(rel, "new") ? linkTo(methodOn(DriverController.class).newDriver(driver.getPhoneNumber(), driver.getFirstName(), driver.getLastName(), driver.getLicensePlate())).withRel("add new driver")
                        : linkTo(methodOn(DriverController.class).newDriver(driver.getPhoneNumber(), driver.getFirstName(), driver.getLastName(), driver.getLicensePlate())).withSelfRel()
        );
    }
    /**
     * Method to create list of links for Driver class
     * and add them to JSON response
     *
     * @param drivers List of Driver objects
     * @return List<EntityModel<Driver>> returns list of Driver objects with links
     */
    public List<EntityModel<Driver>> toModelList(List<Driver> drivers) {
        return drivers.stream().map(this::toModel).collect(Collectors.toList());
    }
}
