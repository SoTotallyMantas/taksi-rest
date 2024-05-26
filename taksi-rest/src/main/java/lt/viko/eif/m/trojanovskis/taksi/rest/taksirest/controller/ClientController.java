package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.ClientNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.OrderNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.ClientRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientRepository repository;


    @Operation(summary="Get all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Clients",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @GetMapping
    public ResponseEntity<?> all()
    {
        return ResponseEntity.ok(repository.findAll());
    }

    @Operation(summary = "Delete Client By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteClient(@PathVariable Long id) {
        repository.deleteById(id);
    }


    @Operation(summary = "Add Client")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Added Client",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping(method = RequestMethod.POST)
    public Client newClient(@RequestBody Client newClient) {
        return repository.save(newClient);
    }




    @Operation(summary ="Get Client By FirstName LastName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.GET)
    public ResponseEntity<?> FindClientByName(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            Client  client = repository.findByFirstNameAndLastName(firstName, lastName)
                    .orElseThrow(() -> new ClientNotFoundException(firstName, lastName));
            return ResponseEntity.ok(client);
        } catch (ClientNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary ="Get Client By Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))})
    })
    @RequestMapping(value = "/{phoneNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> FindClientByPhoneNumber(@RequestParam String phoneNumber) {
        try {
            Client  client = repository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new ClientNotFoundException(phoneNumber));
            return ResponseEntity.ok(client);
        } catch (ClientNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }




}
