package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.ClientNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DriverNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.OrderNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.DriverRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    @Autowired
    private DriverRepository repository;

    @Operation(summary="Get all drivers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Drivers",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })
    @GetMapping
    public List<Driver> all()
    {
        return repository.findAll();
    }
    @Operation(summary = "Delete Driver By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })
    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Long id) {
        repository.deleteById(id);
    }

@Operation(summary = "Add Driver")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Added Driver",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = Driver.class))})
})
    @RequestMapping(method = RequestMethod.POST)
    public Driver newDriver(@RequestBody Driver newDriver) {
    return repository.save(newDriver);
    }



    @Operation(summary="Get driver by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Driver getDriverById(Long id) {
        return repository.findById(id).orElseThrow(() -> new DriverNotFoundException(id));
    }

    @Operation(summary="Get driver by First Name and Last Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })

    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.GET)
    public ResponseEntity<?> FindDriverByPhoneNumber(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            Driver driver = repository.findByFirstNameAndLastName(firstName, lastName)
                    .orElseThrow(() -> new DriverNotFoundException(firstName, lastName));
            return ResponseEntity.ok(driver);
        } catch (DriverNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary="Get driver by Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })

    @RequestMapping(value = "/{phoneNumber}", method = RequestMethod.GET)
    public ResponseEntity<?> FindDriverByPhoneNumber(@RequestParam String phoneNumber) {
        try {
            Driver driver = repository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new DriverNotFoundException(phoneNumber));
            return ResponseEntity.ok(driver);
        } catch (DriverNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary="Get driver by License Plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })

    @RequestMapping(value = "/{licensePlate}", method = RequestMethod.GET)
    public ResponseEntity<?> FindDriverByLicensePlate(@RequestParam String licensePlate) {
        try {
            Driver driver = repository.findByLicensePlate(licensePlate)
                    .orElseThrow(() -> new DriverNotFoundException(licensePlate));
            return ResponseEntity.ok(driver);
        } catch (DriverNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
