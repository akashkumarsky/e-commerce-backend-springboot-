package com.sky.ecommerce.service;

import com.sky.ecommerce.model.Address;
import com.sky.ecommerce.model.Order;
import com.sky.ecommerce.exception.OrderException;
import com.sky.ecommerce.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder (User user, Address address);

    // Find an order by its ID
    Order findOrderById(Long orderId) throws OrderException;

    // Get a user's order history
    List<Order> userOrderHistory(Long userId) throws OrderException;

    // Place a new order
    Order placedOrder(Long userId) throws OrderException;

    // Confirm an order
    Order confirmedOrder(Long orderId) throws OrderException;

    // Mark an order as shipped
    Order shippedOrder(Long orderId) throws OrderException;

    // Mark an order as delivered
    Order deliveredOrder(Long orderId) throws OrderException;

    // Cancel an order
    Order canceledOrder(Long orderId) throws OrderException;

    // get all Orders
    List<Order>getAllOrders();


    //delete Orders
     void deleteOrder(Long orderId) throws  OrderException;
}
