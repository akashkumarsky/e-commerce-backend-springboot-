package com.sky.ecommerce.service;

import com.sky.ecommerce.model.OrderItem;
import com.sky.ecommerce.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements  OrderItemService{

    private OrderItemRepository orderItemRepository;
    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository=orderItemRepository;
    }
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {

        return orderItemRepository.save(orderItem);
    }

}
