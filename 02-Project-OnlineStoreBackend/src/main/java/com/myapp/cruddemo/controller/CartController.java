package com.myapp.cruddemo.controller;
import com.myapp.cruddemo.entity.Cart;
import com.myapp.cruddemo.entity.User;
import com.myapp.cruddemo.exception.ResourceNotFoundException;
import com.myapp.cruddemo.service.CartService;
import com.myapp.cruddemo.dao.UserRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;
    public CartController(CartService cartService, UserRepository userRepository ) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Cart getCart(Authentication authentication){ // The cart must be the user's cart

        User user = userRepository.findByEmail( authentication.getName()).orElseThrow(
                                () -> new ResourceNotFoundException("User not found"));
        return cartService.getCartByUserId(user.getId());
    }

    @PutMapping("/items/{productId}/{quantity}")
    public Cart addItems(@PathVariable int productId , @PathVariable int quantity, Authentication authentication){
        User user = userRepository.findByEmail( authentication.getName()).orElseThrow(
                                () -> new ResourceNotFoundException("User not found"));
        return cartService.addToCart(user.getId(),productId , quantity);
    }

    @DeleteMapping("/items/{productId}/{quantity}")
    public Cart removeItem (@PathVariable int productId , @PathVariable int quantity,Authentication authentication){
        User user = userRepository.findByEmail( authentication.getName()).orElseThrow(
                                () -> new ResourceNotFoundException("User not found"));

        return cartService.removeFromCart(user.getId(), productId, quantity);
    }

}
