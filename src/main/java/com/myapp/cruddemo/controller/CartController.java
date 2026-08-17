package com.myapp.cruddemo.controller;
import com.myapp.cruddemo.entity.Cart;
import com.myapp.cruddemo.service.CartService;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService ) {
        this.cartService = cartService;
    }

    @GetMapping
    public Cart getCart(Authentication authentication){ // The cart must be the user's cart
        return cartService.getCartByUserId(authentication);
    }

    @PutMapping("/items/{productId}/{quantity}")
    public Cart addItems(@PathVariable int productId , @PathVariable int quantity, Authentication authentication){
 
        return cartService.addToCart(authentication,productId , quantity);
    }

    @DeleteMapping("/items/{productId}/{quantity}")
    public Cart removeItem (@PathVariable int productId , @PathVariable int quantity,Authentication authentication){

        return cartService.removeFromCart(authentication, productId, quantity);
    }

}
