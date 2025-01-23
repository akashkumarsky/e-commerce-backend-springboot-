package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.OrderException;
import com.sky.ecommerce.exception.UserException;
import com.sky.ecommerce.model.Address;
import com.sky.ecommerce.model.Order;
import com.sky.ecommerce.model.User;
import com.sky.ecommerce.service.OrderService;
import com.sky.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private UserService userService;

    @Autowired
    public OrderController(OrderService orderService,UserService userService) {
        this.orderService=orderService;
        this.userService=userService;
    }

    public OrderController(){}

    @PostMapping("/")
    public ResponseEntity<Order> createOrderHandler(@Valid @RequestBody Address shippingAddress,
                                                    @RequestHeader("Authorization") String jwtToken) throws UserException {
        // Fetch user based on the provided JWT token
        User user = userService.findUserProfileByJwt(jwtToken);

        // Create the order using the user and shipping address
        Order order = orderService.createOrder(user, shippingAddress);

        // Return the created order with HTTP 201 status
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }


    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistoryHandler(@RequestHeader("Authorization")
                                                                 String jwt) throws OrderException, UserException{

        User user=userService.findUserProfileByJwt(jwt);
        List<Order> orders=orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity< Order> findOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")
    String jwt) throws OrderException, UserException{

        User user=userService.findUserProfileByJwt(jwt);
        Order orders=orderService.findOrderById(orderId);
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

}

