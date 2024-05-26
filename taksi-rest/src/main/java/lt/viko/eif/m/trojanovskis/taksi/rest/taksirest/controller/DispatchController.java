package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DispatchNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.DispatchRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Dispatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispatch")
public class DispatchController {
    @Autowired
    private DispatchRepository repository;

    @Operation(summary="Get all dispatches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Dispatches",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @GetMapping
    public ResponseEntity<?> all()
    {
        return ResponseEntity.ok(repository.findAll());
    }

    @Operation(summary = "Delete Dispatch By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDispatch(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @Operation(summary = "Add Dispatch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(method = RequestMethod.POST)
    public Dispatch newDispatch(@RequestBody Dispatch newDispatch) {
        return repository.save(newDispatch);
    }
    @Operation(summary ="Get Dispatch By FirstName LastName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.GET)
    public ResponseEntity<?> FindDispatchByName(@RequestParam String firstName, @RequestParam String lastName) {
    try {
        Dispatch  dispatch = repository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new DispatchNotFoundException(firstName, lastName));
        return ResponseEntity.ok(dispatch);
    } catch (DispatchNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}

    @Operation(summary ="Get Dispatch By Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(value = "/{phoneNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> FindDispatchByPhoneNumber(@RequestParam String phoneNumber) {
        try {
            Dispatch  dispatch = repository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new DispatchNotFoundException(phoneNumber));
            return ResponseEntity.ok(dispatch);
        } catch (DispatchNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary ="Get Dispatch By Work Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Dispatch.class))})
    })
    @RequestMapping(value = "/{workNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> FindDispatchByWorkNumber(@RequestParam String workNumber) {
        try {
            Dispatch  dispatch = repository.findByWorkNumber(workNumber)
                    .orElseThrow(() -> new DispatchNotFoundException(workNumber));
            return ResponseEntity.ok(dispatch);
        } catch (DispatchNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
