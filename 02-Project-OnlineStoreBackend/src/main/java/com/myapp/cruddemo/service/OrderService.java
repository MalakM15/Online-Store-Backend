package com.myapp.cruddemo.service;

import com.myapp.cruddemo.dao.OrderRepository;
import com.myapp.cruddemo.entity.Order;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    public List<Order> getAllOrders(){ //for ADMIN
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> getUserOrders(int userId){ //for customer's user
        return orderRepository.findByUserId(userId);
    }

}
