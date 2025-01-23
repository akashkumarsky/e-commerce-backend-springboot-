package com.sky.ecommerce.controller;

import com.sky.ecommerce.exception.OrderException;
import com.sky.ecommerce.model.Order;
import com.sky.ecommerce.response.ApiResponse;
import com.sky.ecommerce.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    private OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public AdminOrderController(){}

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrdersHandler(){
        List<Order> orders = orderService.getAllOrders();
        return  new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,
                                                     @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.shippedOrder(orderId);
        return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId,
                                                       @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.deliveredOrder(orderId);
        return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> canceledOrderHandler(@PathVariable Long orderId,
                                                      @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.cancledOrder(orderId);
        return new ResponseEntity<Order>(order,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@PathVariable Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteOrder(orderId);
        ApiResponse res=new ApiResponse("Order Deleted Successfully",true);
        System.out.println("delete method working....");
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }
}
