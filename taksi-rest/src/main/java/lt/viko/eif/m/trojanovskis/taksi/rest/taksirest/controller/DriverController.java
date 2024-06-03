package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DriverNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.ModelAssambler.DriverAssembler;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Service.driverService;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Driver;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 *  Represents DriverController Object that is responsible for GET, POST, PUT, DELETE requests
 *  related to Driver class
 *
 */
@RestController
@RequestMapping("/drivers")
public class DriverController {

    @Autowired
    public driverService service;
    @Autowired
    public DriverAssembler assembler;

    /**
     * Method to get all drivers
     * @return ResponseEntity with list of drivers
     */
    @Operation(summary = "Get all drivers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Drivers",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })
    @GetMapping()
    public ResponseEntity<?> all() {
        List<Driver> drivers = service.getAllDrivers();


        assembler.rel = "all";
        return ResponseEntity.ok(assembler.toModelList(drivers));

    }

    /**
     * Method to delete driver by ID
     * @param id driver ID
     * @return ResponseEntity no content
     */

    @Operation(summary = "Delete Driver By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })
    @RequestMapping(params = {"id"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDriver(@RequestParam(value = "id") Long id) {
        service.deleteDriverById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Method to update driver by ID
     * @param id driver ID
     * @param phoneNumber driver phone number
     * @param firstName driver first name
     * @param lastName driver last name
     * @param licensePlate driver license plate
     * @return ResponseEntity with updated driver
     */
    @Operation(summary = "Update Driver By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })
    @RequestMapping( method = RequestMethod.PUT)
    public ResponseEntity<?> updateDriver(@RequestParam(value = "id") Long id,
                               @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                               @RequestParam(name = "firstName", required = false) String firstName,
                               @RequestParam(name = "lastName", required = false) String lastName,
                               @RequestParam(name = "licensePlate", required = false) String licensePlate
    ) {
        try {
           Driver driver = service.updateDriver(id, phoneNumber, firstName, lastName, licensePlate);
            assembler.rel = "update";
            return ResponseEntity.ok(assembler.toModel(driver));
        }
        catch (DriverNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
}


    /**
     * Method to add new driver
     * @param phoneNumber driver phone number
     * @param firstName driver first name
     * @param lastName driver last name
     * @param licensePlate driver license plate
     * @return ResponseEntity with added driver
     */
@Operation(summary = "Add Driver")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Added Driver",
                content = {@Content(mediaType = "application/json",
                        schema = @Schema(implementation = Driver.class))})
})
    @RequestMapping(params={"phoneNumber","firstName","lastName","licensePlate"}, method = RequestMethod.POST)
    public ResponseEntity<?> newDriver(@RequestParam(name = "phoneNumber") String phoneNumber,
                            @RequestParam(name = "firstName") String firstName,
                            @RequestParam(name = "lastName") String lastName,
                            @RequestParam(name = "licensePlate") String licensePlate
                            ){
        try {
            Driver driver = service.newDriver(firstName, lastName, phoneNumber, licensePlate);
            assembler.rel = "new";
            return ResponseEntity.ok(assembler.toModel(driver));
        }
        catch (DriverNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    /**
     * Method to get driver by ID
     * @param id driver ID
     * @return ResponseEntity with driver
     */

    @Operation(summary="Get driver by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })
    @RequestMapping(params = {"id"}, method = RequestMethod.GET)
    public ResponseEntity<?> getDriverById(@RequestParam(value = "id",required = false)Long id) {
        try {
            Driver driver =  service.getDriverById(id);
            assembler.rel = "findId";
            return ResponseEntity.ok(assembler.toModel(driver));

        }
        catch (DriverNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    /**
     * Method to get driver by First Name and Last Name
     * @param firstName driver first name
     * @param lastName driver last name
     * @return ResponseEntity with driver
     */
    @Operation(summary="Get driver by First Name and Last Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })

    @RequestMapping(value="/findByName", method = RequestMethod.GET)
    public ResponseEntity<?> findDriverByName(@RequestParam(value = "firstName",required = false) String firstName,
                                                     @RequestParam(value = "lastName",required = false) String lastName) {
        try {


            List<Driver> drivers = service.findByName(firstName, lastName);



            assembler.rel = "findDriverName";
            return ResponseEntity.ok(assembler.toModelList(drivers));


        } catch (DriverNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to get driver by Phone Number
     * @param phoneNumber driver phone number
     * @return ResponseEntity with driver
     */

    @Operation(summary="Get driver by Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })

    @RequestMapping(value = "findByPhoneNumber",params = {"phoneNumber"}, method = RequestMethod.GET)
    public ResponseEntity<?> findDriverByPhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber) {
        try {
            Driver driver = service.findDriverByPhoneNumber(phoneNumber);
            assembler.rel = "findDriverPhoneNumber";
            return ResponseEntity.ok(assembler.toModel(driver));
        } catch (DriverNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to get driver by License Plate
     * @param licensePlate driver license plate
     * @return ResponseEntity with driver
     */

    @Operation(summary="Get driver by License Plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Driver.class))})
    })

    @RequestMapping(value="findByLicensePlate",params = {"licensePlate"}, method = RequestMethod.GET)
    public ResponseEntity<?> findDriverByLicensePlate(@RequestParam(value = "licensePlate") String licensePlate) {
        try {
            List<Driver> driver = service.findByLicensePlate(licensePlate);
            assembler.rel = "findDriverLicensePlate";
            return ResponseEntity.ok(assembler.toModelList(driver));
        } catch (DriverNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
