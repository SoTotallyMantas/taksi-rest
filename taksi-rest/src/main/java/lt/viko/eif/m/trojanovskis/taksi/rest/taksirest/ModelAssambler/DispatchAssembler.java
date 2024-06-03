package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.ModelAssambler;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller.DispatchController;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Dispatch;
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
 * Represents DispatchAssembler Object that implements RepresentationModelAssembler
 * for Dispatch class
 * This class is responsible for creating links for Dispatch class
 * and adding them to JSON response
 */
@Component
public class DispatchAssembler implements RepresentationModelAssembler<Dispatch, EntityModel<Dispatch>> {

    public String rel;
    /**
     * Method to create links for Dispatch class
     * and add them to JSON response
     * depending on the rel
     *
     * @param dispatch Dispatch object
     * @return EntityModel<Dispatch> returns Dispatch object with links
     */
    @Override
    public EntityModel<Dispatch> toModel(Dispatch dispatch) {
        return EntityModel.of(dispatch,
                !Objects.equals(rel, "all") ? linkTo(methodOn(DispatchController.class).all()).withRel("Get all")
                        : linkTo(methodOn(DispatchController.class).all()).withSelfRel(),

                !Objects.equals(rel, "findId") ? linkTo(methodOn(DispatchController.class).findDispatchById(dispatch.getId())).withRel("Find by ID")
                        : linkTo(methodOn(DispatchController.class).findDispatchById(dispatch.getId())).withSelfRel(),

                !Objects.equals(rel, "findDispatchPhoneNumber") ? linkTo(methodOn(DispatchController.class).findDispatchByPhoneNumber(dispatch.getPhoneNumber())).withRel("find by phone number")
                        : linkTo(methodOn(DispatchController.class).findDispatchByPhoneNumber(dispatch.getPhoneNumber())).withSelfRel(),

                !Objects.equals(rel, "findDispatchName") ? linkTo(methodOn(DispatchController.class).findDispatchByName(dispatch.getFirstName(), dispatch.getLastName())).withRel("find by name")
                        : linkTo(methodOn(DispatchController.class).findDispatchByName(dispatch.getFirstName(), dispatch.getLastName())).withSelfRel(),

                !Objects.equals(rel, "delete") ? linkTo(methodOn(DispatchController.class).deleteDispatch(dispatch.getId())).withRel("delete")
                        : linkTo(methodOn(DispatchController.class).deleteDispatch(dispatch.getId())).withSelfRel(),

                !Objects.equals(rel, "update") ? linkTo(methodOn(DispatchController.class).updateDispatch(dispatch.getId(), dispatch.getWorkNumber(), dispatch.getPhoneNumber(), dispatch.getFirstName(), dispatch.getLastName())).withRel("update")
                        : linkTo(methodOn(DispatchController.class).updateDispatch(dispatch.getId(), dispatch.getWorkNumber(), dispatch.getPhoneNumber(), dispatch.getFirstName(), dispatch.getLastName())).withSelfRel(),

                !Objects.equals(rel, "new") ? linkTo(methodOn(DispatchController.class).newDispatch(dispatch.getPhoneNumber(), dispatch.getFirstName(), dispatch.getLastName(), dispatch.getWorkNumber())).withRel("add new dispatch")
                        : linkTo(methodOn(DispatchController.class).newDispatch(dispatch.getPhoneNumber(), dispatch.getFirstName(), dispatch.getLastName(), dispatch.getWorkNumber())).withSelfRel(),

                !Objects.equals(rel, "findDispatchByWorkNumber") ? linkTo(methodOn(DispatchController.class).findDispatchByWorkNumber(dispatch.getWorkNumber())).withRel("find by work number")
                        : linkTo(methodOn(DispatchController.class).findDispatchByWorkNumber(dispatch.getWorkNumber())).withSelfRel()
        );
    }
    /**
     * Method to create list of links for Dispatch class
     * and add them to JSON response
     *
     * @param dispatches List of Dispatch objects
     * @return List<EntityModel<Dispatch>> returns list of Dispatch objects with links
     */
    public List<EntityModel<Dispatch>> toModelList(List<Dispatch> dispatches) {
        return dispatches.stream().map(this::toModel).collect(Collectors.toList());
    }
}
