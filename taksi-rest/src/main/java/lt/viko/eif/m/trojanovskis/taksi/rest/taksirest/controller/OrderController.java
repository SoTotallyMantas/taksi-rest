package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.OrderNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.OrdersRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrdersRepository repository;

    @Operation(summary="Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found All Orders",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })


    @GetMapping
    public List<Order> all()
    {
        return repository.findAll();
    }

    

    @Operation(summary = "Add Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @PostMapping
    public Order newOrder(@RequestBody Order newOrder)
    {
        return repository.save(newOrder);
    }
    @Operation(summary="Get Order By ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> FindByID(@RequestParam Long id)
    {
        try {
            Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
            return ResponseEntity.ok(order);
        }
        catch(OrderNotFoundException ex)
        {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Delete Order Using ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> DeleteByID(@RequestParam Long id)
    {
        try {
            Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
            repository.delete(order);
            return ResponseEntity.ok("Order Deleted");
        }
        catch(OrderNotFoundException ex)
        {
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary ="Get Order By Client First Name and Last Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/client/{firstName}/{lastName}", method = RequestMethod.GET)
    public ResponseEntity<?> FindByClientName(@RequestParam String firstname, @RequestParam String lastname) {
        try {
            Order order = repository.findOrderByClientName(firstname, lastname)
                    .orElseThrow(() -> new OrderNotFoundException(firstname, lastname));
            return ResponseEntity.ok(order);
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }


    }
    @Operation(summary ="Get Order By Driver First Name and Last Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Order.class))})
    })
    @RequestMapping(value = "/driver/{firstName}/{lastName}", method = RequestMethod.GET)
    public ResponseEntity<?> FindByDriverName(@RequestParam String firstname, @RequestParam String lastname) {
        try {
            Order order = repository.findOrderByDriverName(firstname, lastname)
                    .orElseThrow(() -> new OrderNotFoundException(firstname, lastname));
            return ResponseEntity.ok(order);
        } catch (OrderNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
        @Operation(summary ="Get Order By Dispatch First Name and Last Name")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Found Order",
                        content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = Order.class))})
        })
        @RequestMapping(value = "/dispatch/{firstName}/{lastName}", method = RequestMethod.GET)
        public ResponseEntity<?> FindByDispatchName(@RequestParam String firstname, @RequestParam String lastname) {
            try {
                Order order = repository.findOrderByDispatchName(firstname, lastname)
                        .orElseThrow(() -> new OrderNotFoundException(firstname, lastname));
                return ResponseEntity.ok(order);
            } catch (OrderNotFoundException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        @Operation(summary ="Get Order By Driver License Plate")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Found Order",
                        content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = Order.class))})
        })
        @RequestMapping(value = "/driver/{licensePlate}", method = RequestMethod.GET)
        public ResponseEntity<?> FindByDriverLicensePlate(@RequestParam String licensePlate) {
            try {
                Order order = repository.findOrderByDriverLicensePlate(licensePlate)
                        .orElseThrow(() -> new OrderNotFoundException(licensePlate));
                return ResponseEntity.ok(order);
            } catch (OrderNotFoundException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        @Operation(summary ="Get Order By Dispatch Phone Number")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Found Order",
                        content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = Order.class))})
        })
        @RequestMapping(value = "/dispatch/{phoneNumber}", method = RequestMethod.GET)
        public ResponseEntity<?> FindByDispatchPhoneNumber(@RequestParam String phoneNumber) {
            try {
                Order order = repository.findOrderByDispatchPhoneNumber(phoneNumber)
                        .orElseThrow(() -> new OrderNotFoundException(phoneNumber));
                return ResponseEntity.ok(order);
            } catch (OrderNotFoundException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        @Operation(summary ="Get Order By Dispatch Work Number")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Found Order",
                        content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = Order.class))})
        })
        @RequestMapping(value = "/dispatch/{workNumber}", method = RequestMethod.GET)
        public ResponseEntity<?> FindByDispatchWorkNumber(@RequestParam String workNumber) {
            try {
                Order order = repository.findOrderByDispatchWorkNumber(workNumber)
                        .orElseThrow(() -> new OrderNotFoundException(workNumber));
                return ResponseEntity.ok(order);
            } catch (OrderNotFoundException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        @Operation(summary ="Get Order By Client Phone Number")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Found Order",
                        content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = Order.class))})
        })
        @RequestMapping(value = "/client/{phoneNumber}", method = RequestMethod.GET)
        public ResponseEntity<?> FindByClientPhoneNumber(@RequestParam String phoneNumber) {
            try {
                Order order = repository.findOrderByClientPhoneNumber(phoneNumber)
                        .orElseThrow(() -> new OrderNotFoundException(phoneNumber));
                return ResponseEntity.ok(order);
            } catch (OrderNotFoundException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
        @Operation(summary ="Get Order By Driver Phone Number")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Found Order",
                        content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = Order.class))})
        })
        @RequestMapping(value = "/driver/{phoneNumber}", method = RequestMethod.GET)
        public ResponseEntity<?> FindByDriverPhoneNumber(@RequestParam String phoneNumber) {
            try {
                Order order = repository.findOrderByDriverPhoneNumber(phoneNumber)
                        .orElseThrow(() -> new OrderNotFoundException(phoneNumber));
                return ResponseEntity.ok(order);
            } catch (OrderNotFoundException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }
        }












}

