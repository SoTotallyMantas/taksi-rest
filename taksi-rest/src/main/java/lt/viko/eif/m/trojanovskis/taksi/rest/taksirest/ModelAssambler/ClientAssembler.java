package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.ModelAssambler;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller.ClientController;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Represents ClientAssembler Object that implements RepresentationModelAssembler
 * for Client class
 * This class is responsible for creating links for Client class
 * and adding them to JSON response
 */
@Component
public class ClientAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>> {

    public  String rel;

    /**
     * Method to create links for Client class
     * and add them to JSON response
     * depending on the rel
     *
     * @param client Client object
     * @return EntityModel<Client> returns Client object with links
     */

    @Override
    public EntityModel<Client> toModel(Client client) {
        return EntityModel.of(client,
                !Objects.equals(rel, "all") ? linkTo(methodOn(ClientController.class).all()).withRel("Get all")
                        : linkTo(methodOn(ClientController.class).all()).withSelfRel(),

                !Objects.equals(rel, "findId") ? linkTo(methodOn(ClientController.class).findClientById(client.getId())).withRel("Find by ID")
                        : linkTo(methodOn(ClientController.class).findClientById(client.getId())).withSelfRel(),

                !Objects.equals(rel, "findClientPhoneNumber") ? linkTo(methodOn(ClientController.class).findClientByPhoneNumber(client.getPhoneNumber())).withRel("find by phone number")
                        : linkTo(methodOn(ClientController.class).findClientByPhoneNumber(client.getPhoneNumber())).withSelfRel(),

                !Objects.equals(rel, "findClientName") ? linkTo(methodOn(ClientController.class).findClientByName(client.getFirstName(), client.getLastName())).withRel("find by name")
                        : linkTo(methodOn(ClientController.class).findClientByName(client.getFirstName(), client.getLastName())).withSelfRel(),

                !Objects.equals(rel, "delete") ? linkTo(methodOn(ClientController.class).deleteClient(client.getId())).withRel("delete")
                        : linkTo(methodOn(ClientController.class).deleteClient(client.getId())).withSelfRel(),

                !Objects.equals(rel, "update") ? linkTo(methodOn(ClientController.class).updateClient(client.getId(), client.getPhoneNumber(), client.getFirstName(), client.getLastName())).withRel("update")
                        : linkTo(methodOn(ClientController.class).updateClient(client.getId(), client.getPhoneNumber(), client.getFirstName(), client.getLastName())).withSelfRel(),

                !Objects.equals(rel, "new") ? linkTo(methodOn(ClientController.class).newClient(client.getPhoneNumber(), client.getFirstName(), client.getLastName())).withRel("add new client")
                        : linkTo(methodOn(ClientController.class).newClient(client.getPhoneNumber(), client.getFirstName(), client.getLastName())).withSelfRel()
        );
    }
    /**
     * Method to create links for Client class
     * and add them to JSON response
     * depending on the rel
     *
     * @param clients List of Client objects
     * @return List<EntityModel<Client>> returns List of Client objects with links
     */
    public List<EntityModel<Client>> toModelList(List<Client> clients) {
        return clients.stream().map(this::toModel).collect(Collectors.toList());
    }
}
