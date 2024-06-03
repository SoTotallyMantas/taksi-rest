package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.ModelAssambler;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller.OrderController;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Represents OrderAssembler Object that implements RepresentationModelAssembler
 * for Order class
 * This class is responsible for creating links for Order class
 * and adding them to JSON response
 */
@Component
public class OrderAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    public String relResponse;


    /**
     *  Method to create links for Order class
     *  and add them to JSON response
     *  depending on the relResponse
     *
     * @param order Order object
     * @return EntityModel<Order> returns Order object with links
     */
    @Override
    public EntityModel<Order> toModel(Order order) {
        return EntityModel.of(order,
                !Objects.equals(relResponse, "all") ? linkTo(methodOn(OrderController.class).all()).withRel("orders")
                        : linkTo(methodOn(OrderController.class).all()).withSelfRel(),

                !Objects.equals(relResponse, "delete") ? linkTo(methodOn(OrderController.class).deleteByID(order.getId())).withRel("delete order")
                        : linkTo(methodOn(OrderController.class).deleteByID(order.getId())).withSelfRel(),

                !Objects.equals(relResponse, "findID") ? linkTo(methodOn(OrderController.class).findByID(order.getId())).withRel("find by order id")
                        : linkTo(methodOn(OrderController.class).findByID(order.getId())).withSelfRel(),

                !Objects.equals(relResponse, "update") ? linkTo(methodOn(OrderController.class).updateOrder(order.getId(), order.getClient().getId(), order.getDispatch().getId(), order.getDriver().getId(), order.getAddress())).withRel("update order")
                        : linkTo(methodOn(OrderController.class).updateOrder(order.getId(), order.getClient().getId(), order.getDispatch().getId(), order.getDriver().getId(), order.getAddress())).withSelfRel(),

                !Objects.equals(relResponse, "new") ? linkTo(methodOn(OrderController.class).newOrder(order.getClient().getId(), order.getDispatch().getId(), order.getDriver().getId(), order.getAddress())).withRel("add new order")
                        : linkTo(methodOn(OrderController.class).newOrder(order.getClient().getId(), order.getDispatch().getId(), order.getDriver().getId(), order.getAddress())).withSelfRel(),

                !Objects.equals(relResponse, "findDispatchName") ? linkTo(methodOn(OrderController.class).findByDispatchName(order.getDispatch().getFirstName(), order.getDispatch().getLastName())).withRel("find by dispatch name")
                        : linkTo(methodOn(OrderController.class).findByDispatchName(order.getDispatch().getFirstName(), order.getDispatch().getLastName())).withSelfRel(),

                !Objects.equals(relResponse, "findDriverName") ? linkTo(methodOn(OrderController.class).findByDriverName(order.getDriver().getFirstName(), order.getDriver().getLastName())).withRel("find by driver name")
                        : linkTo(methodOn(OrderController.class).findByDriverName(order.getDriver().getFirstName(), order.getDriver().getLastName())).withSelfRel(),

                !Objects.equals(relResponse, "findClientName") ? linkTo(methodOn(OrderController.class).findByClientName(order.getClient().getFirstName(), order.getClient().getLastName())).withRel("find by client name")
                        : linkTo(methodOn(OrderController.class).findByClientName(order.getClient().getFirstName(), order.getClient().getLastName())).withSelfRel(),

                !Objects.equals(relResponse, "findDriverLicensePlate") ? linkTo(methodOn(OrderController.class).findByDriverLicensePlate(order.getDriver().getLicensePlate())).withRel("find order by driver license plate")
                        : linkTo(methodOn(OrderController.class).findByDriverLicensePlate(order.getDriver().getLicensePlate())).withSelfRel(),

                !Objects.equals(relResponse, "findDispatchWorkNumber") ? linkTo(methodOn(OrderController.class).findByDispatchWorkNumber(order.getDispatch().getWorkNumber())).withRel("find by dispatch work number")
                        : linkTo(methodOn(OrderController.class).findByDispatchWorkNumber(order.getDispatch().getWorkNumber())).withSelfRel(),

                !Objects.equals(relResponse, "findClientPhoneNumber") ? linkTo(methodOn(OrderController.class).findByClientPhoneNumber(order.getClient().getPhoneNumber())).withRel("find by client phone number")
                        : linkTo(methodOn(OrderController.class).findByClientPhoneNumber(order.getClient().getPhoneNumber())).withSelfRel(),

                !Objects.equals(relResponse, "findDriverPhoneNumber") ? linkTo(methodOn(OrderController.class).findByDriverPhoneNumber(order.getDriver().getPhoneNumber())).withRel("find by driver phone number")
                        : linkTo(methodOn(OrderController.class).findByDriverPhoneNumber(order.getDriver().getPhoneNumber())).withSelfRel(),

                !Objects.equals(relResponse, "findDispatchPhoneNumber") ? linkTo(methodOn(OrderController.class).findByDispatchPhoneNumber(order.getDispatch().getPhoneNumber())).withRel("find by dispatch phone number")
                        : linkTo(methodOn(OrderController.class).findByDispatchPhoneNumber(order.getDispatch().getPhoneNumber())).withSelfRel(),

                !Objects.equals(relResponse, "findAddress") ? linkTo(methodOn(OrderController.class).findByAddress(order.getAddress())).withRel("find by address")
                        : linkTo(methodOn(OrderController.class).findByAddress(order.getAddress())).withSelfRel()
        );
    }
    /**
     * Method to create list of links for Order class
     * and add them to JSON response
     *
     * @param orders List of Order objects
     * @return List<EntityModel<Order>> returns list of Order objects with links
     */
    public List<EntityModel<Order>> toModelList(List<Order> orders) {
        return orders.stream().map(this::toModel).collect(Collectors.toList());
    }
}
