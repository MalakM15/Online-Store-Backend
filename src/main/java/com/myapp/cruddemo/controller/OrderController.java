package com.myapp.cruddemo.controller;
import com.myapp.cruddemo.service.OrderService;
import com.myapp.cruddemo.entity.Order;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
       

    }

    @GetMapping
    public List<Order> getOrders(Authentication authentication){

        return orderService.getOrders(authentication);
    }
    @PostMapping

    public Order placeOrder (Authentication authentication){
        return orderService.placeOrder(authentication);

    }


}
