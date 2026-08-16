package com.myapp.cruddemo.controller;
import com.myapp.cruddemo.exception.ResourceNotFoundException;
import com.myapp.cruddemo.service.OrderService;
import com.myapp.cruddemo.dao.UserRepository;
import com.myapp.cruddemo.entity.Order;
import com.myapp.cruddemo.entity.User;
import com.myapp.cruddemo.entity.User.Role;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.cruddemo.exception.BadRequestException;
import com.myapp.cruddemo.exception.ResourceNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService,UserRepository userRepository){
        this.orderService = orderService;
        this.userRepository = userRepository;

    }

    @GetMapping
    public List<Order> getOrders(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()-> new ResourceNotFoundException("User not found") );

        Role role = user.getRole();
        if (role == User.Role.ADMIN){
            return orderService.getAllOrders();
        }
        else if(role == User.Role.CUSTOMER){
            return orderService.getUserOrders(user.getId());
        }
        else {
            throw new BadRequestException("Invalid user role");
        }
    }


}
