package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.ClientNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DispatchNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DriverNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.OrderNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.ModelAssambler.OrderAssembler;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Service.orderService;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
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
 * Represents OrderController Object that is responsible for GET, POST, PUT, DELETE Requests
 * This class is responsible for handling API requests
 * and responses related to Order
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private orderService service;
    @Autowired
    private OrderAssembler assembler;

    /**
     * This method is responsible for handling GET request to get all orders
     * @return ResponseEntity with list of orders and HTTP status OK
     */
    @Operation(summary = "Get all orders", operationId = "getAllOrders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Orders",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })


    @GetMapping()
    public ResponseEntity<?> all() {
        List<Order> orders = service.getAllOrders();

        assembler.relResponse= "all";
        return ResponseEntity.ok(assembler.toModelList(orders));




    }

    /**
     * This method is responsible for handling POST request to add new order
     * @param clientID - ID of the client
     * @param dispatchID - ID of the dispatch
     * @param driverID - ID of the driver
     * @param address - address of the order
     * @return ResponseEntity with added order and HTTP status OK
     */

    @Operation(summary = "Add Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping( params = {"clientID", "dispatchID", "driverID", "address"}, method = RequestMethod.POST)
    public ResponseEntity<?> newOrder(@RequestParam(value = "clientID", required = true) Long clientID,
                                      @RequestParam(value = "dispatchID", required = true) Long dispatchID,
                                      @RequestParam(value = "driverID", required = true) Long driverID ,
                                      @RequestParam(value = "address",required = true) String address) {
        try {
             Order newOrder = service.addNewOrder(clientID, dispatchID, driverID, address);



            assembler.relResponse = "new";
            return ResponseEntity.ok(assembler.toModel(newOrder));
        } catch (OrderNotFoundException | DispatchNotFoundException  | DriverNotFoundException  | ClientNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method is responsible for handling GET request to get order by address
     * @param address - address of the order
     * @return ResponseEntity with order and HTTP status OK
     */
    @Operation(summary = "Find by Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Order By Address",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/findByAddress",params = {"address"}, method = RequestMethod.GET)
    public ResponseEntity<?> findByAddress(@RequestParam(value = "address", required = true) String address) {
        try {
            List<Order> order = service.findByAddress(address);
            assembler.relResponse = "findAddress";
            return ResponseEntity.ok(assembler.toModelList(order));
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling PUT request to update order
     * @param id - ID of the order
     * @param clientID - ID of the client
     * @param dispatchID - ID of the dispatch
     * @param driverID - ID of the driver
     * @param address - address of the order
     * @return ResponseEntity with updated order and HTTP status OK
     */
    @Operation(summary = "Update Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping( method =  RequestMethod.PUT)
    public ResponseEntity<?> updateOrder(@RequestParam(value = "orderID", required = false) Long id,
                                         @RequestParam(value = "clientID", required = false) Long clientID,
                                         @RequestParam(value = "dispatchID", required = false) Long dispatchID,
                                         @RequestParam(value = "driverID", required = false) Long driverID,
                                         @RequestParam(value = "address",required = false) String address) {
        try {

             service.updateOrder(id, clientID, dispatchID, driverID,address);
            Order updatedOrder = service.getOrderById(id);

            assembler.relResponse = "update";
            return ResponseEntity.ok(assembler.toModel(updatedOrder));
        } catch (OrderNotFoundException | ClientNotFoundException | DriverNotFoundException | DispatchNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
    /**
     * This method is responsible for handling GET request to get order by ID
     * @param id - ID of the order
     * @return ResponseEntity with order and HTTP status OK
     */
    @Operation(summary = "Get Order By ID", operationId = "getOrderById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping( params = {"orderID"}, method = RequestMethod.GET)
    public ResponseEntity<?> findByID(@RequestParam(value = "orderID", required = false) Long id) {
        try {
            Order order = service.getOrderById(id);


            assembler.relResponse = "findID";
            return ResponseEntity.ok(assembler.toModel(order));
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling DELETE request to delete order by ID
     * @param id - ID of the order
     * @return ResponseEntity with HTTP status NO_CONTENT
     */

    @Operation(summary = "Delete Order Using ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping( params = {"orderID"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteByID(@RequestParam(value = "orderID", required = false) Long id) {
        try {
            service.deleteOrderById(id);
            return ResponseEntity.noContent().build();
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling GET request to get order by first name and last name
     * @param dispatchFirstName - first name of the dispatch
     * @param dispatchLastName - last name of the dispatch
     * @return ResponseEntity with order and HTTP status OK
     */

    @Operation(summary = "Get Order By First Name and Last Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch Orders By Name",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/findByName",params = {"dispatchFirstName","dispatchLastName"}, method = RequestMethod.GET)
    public ResponseEntity<?> findByDispatchName( @RequestParam(value = "dispatchFirstName", required = false) String dispatchFirstName,
                                         @RequestParam(value = "dispatchLastName", required = false) String dispatchLastName)
    {
        try {
            List<Order> orders = null;
            if(dispatchFirstName != null && dispatchLastName != null) {
                orders =  service.findByDispatchName(dispatchFirstName, dispatchLastName);

            } else if ( dispatchFirstName != null)
            {
                orders =  service.findByDispatchFirstName(dispatchFirstName);

            } else if ( dispatchLastName != null)
            {
                orders =  service.findByDispatchLastName(dispatchLastName);


            }
            if(orders !=null)
            {
                assembler.relResponse = "findDispatchName";
                    return ResponseEntity.ok(assembler.toModelList(orders));
            }
            else {
                return new ResponseEntity<>("No Order Found", HttpStatus.NOT_FOUND);
            }

        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling GET request to get order by first name and last name
     * @param driverFirstName - first name of the driver
     * @param driverLastName - last name of the driver
     * @return ResponseEntity with order and HTTP status OK
     */

    @Operation(summary = "Get Order By First Name and Last Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch Orders By Name",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/findByName",params = {"driverFirstName","driverLastName"}, method = RequestMethod.GET)
    public ResponseEntity<?> findByDriverName(
            @RequestParam(value = "driverFirstName", required = false) String driverFirstName,
            @RequestParam(value = "driverLastName", required = false) String driverLastName)
    {
        try {
            List<Order> orders = null;
            if(driverFirstName != null && driverLastName != null) {
                orders =  service.findByDriverName(driverFirstName, driverLastName);

            } else if ( driverFirstName != null)
            {
                orders =  service.findByDriverFirstName(driverFirstName);

            } else if ( driverLastName != null)
            {
                orders =  service.findByDriverLastName(driverLastName);


            }
            if(orders !=null)
            {

                assembler.relResponse = "findDriverName";
                return ResponseEntity.ok(assembler.toModelList(orders));
            }
            else {
                return new ResponseEntity<>("No Order Found", HttpStatus.NOT_FOUND);
            }

        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * This method is responsible for handling GET request to get order by first name and last name
     * @param clientFirstName - first name of the client
     * @param clientLastName - last name of the client
     * @return ResponseEntity with order and HTTP status OK
     */

    @Operation(summary = "Get Order By First Name and Last Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch Orders By Name",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/findByName",params = {"clientFirstName","clientLastName"}, method = RequestMethod.GET)
    public ResponseEntity<?> findByClientName(
            @RequestParam(value = "clientFirstName", required = false) String clientFirstName,
            @RequestParam(value = "clientLastName", required = false) String clientLastName)
    {
        try {

            List<Order> orders = null;

            if(clientFirstName != null && clientLastName != null) {
                 orders =  service.findByClientName(clientFirstName, clientLastName);



            } else if ( clientFirstName != null)
            {
                orders =  service.findByClientFirstName(clientFirstName);



            } else if ( clientLastName != null)
            {
                orders =  service.findByClientLastName(clientLastName);




            }
            if(orders !=null)
            {

                assembler.relResponse = "findClientName";
                return ResponseEntity.ok(assembler.toModelList(orders));
            }
           else
            {
                return new ResponseEntity<>("No Order Found", HttpStatus.NOT_FOUND);
            }

        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling GET request to get order by driver license plate
     * @param licensePlate - license plate of the driver
     * @return ResponseEntity with order and HTTP status OK
     */

    @Operation(summary = "Get Order By Driver License Plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver License Plate Orders",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/findByLicensePlate",params = {"driverLicensePlate"}, method = RequestMethod.GET)
    public ResponseEntity<?> findByDriverLicensePlate(@RequestParam(value = "driverLicensePlate", required = true) String licensePlate) {
        try {
            List<Order> order = service.findByLicensePlate(licensePlate);
            assembler.relResponse = "findDriverLicensePlate";
            return ResponseEntity.ok(assembler.toModelList(order));
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling GET request to get order by dispatch work number
     * @param workNumber - work number of the dispatch
     * @return ResponseEntity with order and HTTP status OK
     */

    @Operation(summary = "Get Order By Work Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/findByWorkPhone",params = {"dispatchWorkNumber"} , method = RequestMethod.GET)
    public ResponseEntity<?> findByDispatchWorkNumber(@RequestParam(value = "dispatchWorkNumber", required = true) String workNumber) {
        try {
            List<Order> order = service.findByDispatchWorkNumber(workNumber);

            assembler.relResponse = "findDispatchWorkNumber";
                return ResponseEntity.ok(assembler.toModelList(order));
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling GET request to get order by client phone number
     * @param clientPhoneNumber - phone number of the client
     * @return ResponseEntity with order and HTTP status OK
     */
    @Operation(summary = "Get Order By Client Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Client Phone Number Orders",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/findByPhone",params = {"clientPhoneNumber"}, method = RequestMethod.GET)
    public ResponseEntity<?> findByClientPhoneNumber(@RequestParam(value = "clientPhoneNumber", required = true) String clientPhoneNumber)
    {
        try {
            List<Order> order = service.findByClientPhoneNumber(clientPhoneNumber);

            assembler.relResponse = "findClientPhoneNumber";
            return ResponseEntity.ok(assembler.toModelList(order));
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling GET request to get order by driver phone number
     * @param driverPhoneNumber - phone number of the driver
     * @return ResponseEntity with order and HTTP status OK
     */
    @Operation(summary = "Get Order By Driver Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Driver Phone Number Orders",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/findByPhone", params = {"driverPhoneNumber"}, method = RequestMethod.GET)
    public ResponseEntity<?> findByDriverPhoneNumber(@RequestParam(value = "driverPhoneNumber", required = true) String driverPhoneNumber)
    {
        try {
            List<Order> order = service.findByDriverPhoneNumber(driverPhoneNumber);

            assembler.relResponse = "findDriverPhoneNumber";
            return ResponseEntity.ok(assembler.toModelList(order));
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method is responsible for handling GET request to get order by dispatch phone number
     * @param dispatchPhoneNumber - phone number of the dispatch
     * @return ResponseEntity with order and HTTP status OK
     */
    @Operation(summary = "Get Order By Dispatch Phone Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Dispatch Phone Number Orders",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/findByPhone",params = {"dispatchPhoneNumber"}, method = RequestMethod.GET)
    public ResponseEntity<?> findByDispatchPhoneNumber(@RequestParam(value = "dispatchPhoneNumber", required = true) String dispatchPhoneNumber) {
        try {

                List<Order> order = service.findByDispatchPhoneNumber(dispatchPhoneNumber);

                assembler.relResponse = "findDispatchPhoneNumber";
            return ResponseEntity.ok(assembler.toModelList(order));


        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }



}

