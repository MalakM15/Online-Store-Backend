package com.myapp.cruddemo.service;

import com.myapp.cruddemo.dao.OrderRepository;
import com.myapp.cruddemo.dao.ProductRepository;
import com.myapp.cruddemo.dao.UserRepository;
import com.myapp.cruddemo.entity.Cart;
import com.myapp.cruddemo.entity.CartItem;
import com.myapp.cruddemo.entity.Order;
import com.myapp.cruddemo.entity.OrderItem;
import com.myapp.cruddemo.entity.User;
import com.myapp.cruddemo.entity.Product;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.cruddemo.exception.BadRequestException;
import com.myapp.cruddemo.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,UserRepository userRepository, ProductRepository productRepository){
        this.orderRepository=orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    
    public List<Order> getOrders(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()-> new ResourceNotFoundException("User not found") );

        User.Role role = user.getRole();
        if (role == User.Role.ADMIN){
            return getAllOrders();
        }
        else if(role == User.Role.CUSTOMER){
            return getUserOrders(user.getId());
        }
        else {
            throw new BadRequestException("Invalid user role");
        }
    }
    public List<Order> getAllOrders(){ //for ADMIN
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Order> getUserOrders(int userId){ //for customer's user
        return orderRepository.findByUserId(userId);
    }
    

    @Transactional
    public Order placeOrder(Authentication authentication){
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(()-> new ResourceNotFoundException("user not found"));

        Cart cart = user.getCart();
        if (cart == null) {
            throw new ResourceNotFoundException("User does not have a cart");
        }        
        if (user.getCart().getCartItems().isEmpty()){
            throw new BadRequestException("Can't place an order, your Cart is empty.");
        }
        
        for (CartItem item: cart.getCartItems()){
            Product product = item.getProduct();

            if (product.getStock()< item.getQuantity()){
                throw new BadRequestException("not enough stock for : "+ product.getName());
            }
        }
    
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        
        double totalPrice = 0;
        for (CartItem item : cart.getCartItems()){
            Product product =   item.getProduct();
            int quantity = item.getQuantity();

            OrderItem orderItem =new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            order.addOrderItem(orderItem);

            totalPrice += product.getPrice() * quantity;
            int updatedRows = productRepository.decreaseStockIfAvailable(product.getId(),quantity);

            if (updatedRows == 0) {
                throw new BadRequestException("Not enough stock for: " + product.getName());
            }

        }
        order.setTotalPrice(totalPrice);
        cart.getCartItems().clear();
        Order savedOrder = orderRepository.save(order);


        return savedOrder;
            
        }
    }
