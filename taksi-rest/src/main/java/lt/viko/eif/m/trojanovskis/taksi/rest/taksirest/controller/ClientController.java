package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.ClientNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.ModelAssambler.ClientAssembler;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Service.clientService;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Driver;
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
 * Represents ClientController Object
 * This class is responsible for handling requests and responses
 * related to Client class
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private clientService service;
    @Autowired
    private ClientAssembler assembler;


    /**
     * Method to get all clients
     * @return ResponseEntity with list of clients
     */

    @Operation(summary="Get all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Clients",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @GetMapping()
    public ResponseEntity<?> all()
    {
        List<Client> client= service.getAllClients();
        assembler.rel="all";
        return ResponseEntity.ok(assembler.toModelList(client));
    }
    /**
     * Method to delete client by ID
     * @param id Client ID
     * @return ResponseEntity with no content
     */
    @Operation(summary = "Delete Client By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping(params = {"id"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClient(@RequestParam(value = "id") Long id) {
        service.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Method to update client by ID
     * @param id Client ID
     * @param phoneNumber Client Phone Number
     * @param firstName Client First Name
     * @param lastName Client Last Name
     * @return ResponseEntity with updated client
     */

    @Operation(summary = "Update Client By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateClient(@RequestParam(value = "id") Long id,
                                         @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                                         @RequestParam(name = "firstName", required = false) String firstName,
                                         @RequestParam(name = "lastName", required = false) String lastName
    ) {
        try {
            Client client = service.updateClient(id, phoneNumber, firstName, lastName);

            assembler.rel="update";
            return ResponseEntity.ok(assembler.toModel(client));
        } catch (ClientNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to find client by ID
     * @param id Client ID
     * @return ResponseEntity with found client
     */
    @Operation(summary = "Find By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping(params = {"id"}, method = RequestMethod.GET)
    public ResponseEntity<?> findClientById(@RequestParam(value = "id",required = false) Long id) {
        try {
            Client client = service.getClientById(id);
            assembler.rel="findId";
            return ResponseEntity.ok(assembler.toModel(client));
        } catch (ClientNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to add new client
     * @param phoneNumber Client Phone Number
     * @param firstName Client First Name
     * @param lastName Client Last Name
     * @return ResponseEntity with added client
     */

    @Operation(summary = "Add Client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Added Client",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping(params = {"firstName","lastName","phoneNumber"},method = RequestMethod.POST)
    public ResponseEntity<?> newClient(@RequestParam(name = "phoneNumber",required = true) String phoneNumber,
                            @RequestParam(name = "firstName",required = true) String firstName,
                            @RequestParam(name = "lastName",required = true) String lastName
                            ) {
        Client client = service.newClient(firstName, lastName, phoneNumber);


        assembler.rel="new";
        return ResponseEntity.ok(assembler.toModel(client));
    }


    /**
     * Method to find client by First Name and Last Name
     * @param firstName Client First Name
     * @param lastName Client Last Name
     * @return ResponseEntity with found client
     */

    @Operation(summary ="Get Client By FirstName LastName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping(value ="findByName", method = RequestMethod.GET)
    public ResponseEntity<?> findClientByName(@RequestParam(value = "firstName", required = false) String firstName,
                                              @RequestParam(value = "lastName", required = false) String lastName) {
        try {
            List<Client> clients = service.findByName(firstName, lastName);

            assembler.rel="findClientName";
            return ResponseEntity.ok(assembler.toModelList(clients));
        } catch (ClientNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to find client by Phone Number
     * @param phoneNumber Client Phone Number
     * @return ResponseEntity with found client
     */

    @Operation(summary ="Get Client By Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping( value="findByPhoneNumber",params = {"phoneNumber"}, method = RequestMethod.GET)
    public ResponseEntity<?> findClientByPhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber) {
        try {
            Client client = service.findClientByPhoneNumber(phoneNumber);

            assembler.rel="findClientPhoneNumber";
            return ResponseEntity.ok(assembler.toModel(client));
        } catch (ClientNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
