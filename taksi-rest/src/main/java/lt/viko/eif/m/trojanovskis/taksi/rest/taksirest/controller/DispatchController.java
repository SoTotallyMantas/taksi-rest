package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DispatchNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.ModelAssambler.DispatchAssembler;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Service.dispatchService;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.DispatchRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Dispatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Represents DispatchController Object that is responsible for GET, POST, PUT, DELETE requests
 * related to Dispatch class
 */
@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Autowired
    private dispatchService service;
    @Autowired
    private DispatchAssembler assembler;

    /**
     * Method to get all Dispatches
     * @return ResponseEntity with list of Dispatches
     */
    @Operation(summary="Get all dispatches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Dispatches",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @GetMapping()
    public ResponseEntity<?> all()
    {
        List<Dispatch> dispatches = service.getAllDispatch();


        assembler.rel = "all";
        return ResponseEntity.ok(assembler.toModelList(dispatches));
    }

    /**
     * Method to get Dispatch by ID
     * @param id Dispatch ID
     * @return ResponseEntity with Dispatch
     */
    @Operation(summary = "Get Dispatch By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(params ={"id"}, method = RequestMethod.GET)
    public ResponseEntity<?> findDispatchById(@RequestParam(value = "id",required = false) Long id) {
        try {
            Dispatch dispatch = service.getDispatchById(id);
            assembler.rel = "findId";
            return ResponseEntity.ok(assembler.toModel(dispatch));
        } catch (DispatchNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to delete Dispatch by ID
     * @param id Dispatch ID
     * @return ResponseEntity with no content
     */

    @Operation(summary = "Delete Dispatch By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(params = {"id"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDispatch(@RequestParam(value = "id") Long id) {
        try {
             service.deleteDispatchById(id);

            return ResponseEntity.noContent().build();
        } catch (DispatchNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Method to update Dispatch by ID
     * @param id Dispatch ID
     * @param phoneNumber Dispatch Phone Number
     * @param firstName Dispatch First Name
     * @param lastName Dispatch Last Name
     * @param workNumber Dispatch Work Number
     * @return ResponseEntity with updated Dispatch
     */

    @Operation(summary = "Update Dispatch By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping( method = RequestMethod.PUT)
    public ResponseEntity<?> updateDispatch(@RequestParam(value = "id") Long id,
                                           @RequestParam(name = "phoneNumber",required = false) String phoneNumber,
                                           @RequestParam(name = "firstName",required = false) String firstName,
                                           @RequestParam(name = "lastName",required = false) String lastName,
                                           @RequestParam(name = "workNumber",required = false) String workNumber) {
        try {
            Dispatch dispatch = service.updateDispatch(id, workNumber, phoneNumber, firstName, lastName);

            assembler.rel = "update";
            return ResponseEntity.ok(assembler.toModel(dispatch));
        } catch (DispatchNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to add new Dispatch
     * @param phoneNumber Dispatch Phone Number
     * @param firstName Dispatch First Name
     * @param lastName Dispatch Last Name
     * @param workNumber Dispatch Work Number
     * @return ResponseEntity with new Dispatch
     */

    @Operation(summary = "Add Dispatch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(params = {"firstName","lastName","phoneNumber","workNumber"},method = RequestMethod.POST)
    public ResponseEntity<?> newDispatch(@RequestParam(name = "phoneNumber") String phoneNumber,
                                @RequestParam(name = "firstName") String firstName,
                                @RequestParam(name = "lastName") String lastName,
                                @RequestParam(name = "workNumber") String workNumber) {
        try
        {
            Dispatch newDispatch = service.newDispatch(firstName, lastName, phoneNumber, workNumber);

            assembler.rel = "new";
            return ResponseEntity.ok(assembler.toModel(newDispatch));
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Method to get Dispatch by First Name and Last Name
     * @param firstName Dispatch First Name
     * @param lastName Dispatch Last Name
     * @return ResponseEntity with Dispatch
     */

    @Operation(summary ="Get Dispatch By FirstName LastName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(value ="/findByName", method = RequestMethod.GET)
    public ResponseEntity<?> findDispatchByName(@RequestParam(value = "firstName",required = false) String firstName,
                                                @RequestParam(value = "lastName",required = false) String lastName) {
    try {

        List<Dispatch> dispatches = service.findByName(firstName, lastName);

        assembler.rel = "findDispatchName";
        return ResponseEntity.ok(assembler.toModelList(dispatches));

    } catch (DispatchNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

    /**
     * Method to get Dispatch by Phone Number
     * @param phoneNumber Dispatch Phone Number
     * @return ResponseEntity with Dispatch
     */
    @Operation(summary ="Get Dispatch By Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(value ="/findByPhoneNumber",params = {"phoneNumber"}, method = RequestMethod.GET)
    public ResponseEntity<?> findDispatchByPhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber) {
        try {
            List <Dispatch>  dispatch = service.findDispatchByPhoneNumber(phoneNumber);

            assembler.rel = "findDispatchPhoneNumber";
            return ResponseEntity.ok(assembler.toModelList(dispatch));
        } catch (DispatchNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to get Dispatch by Work Number
     * @param workNumber Dispatch Work Number
     * @return ResponseEntity with Dispatch
     */
    @Operation(summary ="Get Dispatch By Work Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(value="/findByWorkNumber",params = {"workNumber"}, method = RequestMethod.GET)
    public ResponseEntity<?> findDispatchByWorkNumber(@RequestParam(value = "workNumber") String workNumber) {
        try {
            List <Dispatch>  dispatch = service.findByWorkNumber(workNumber);

            assembler.rel = "findDispatchByWorkNumber";
            return ResponseEntity.ok(assembler.toModelList(dispatch));
        } catch (DispatchNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
