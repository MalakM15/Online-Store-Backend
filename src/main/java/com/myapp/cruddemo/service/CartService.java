package com.myapp.cruddemo.service;

import com.myapp.cruddemo.dao.CartRepository;
import com.myapp.cruddemo.dao.ProductRepository;
import com.myapp.cruddemo.dao.UserRepository;

import com.myapp.cruddemo.entity.Cart;
import com.myapp.cruddemo.entity.CartItem;
import com.myapp.cruddemo.entity.Product;
import com.myapp.cruddemo.entity.User;

import com.myapp.cruddemo.exception.BadRequestException;
import com.myapp.cruddemo.exception.ResourceNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;


import java.util.Iterator;
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;

    }

    public Cart getCart(int cartId){
        return cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("No Cart Found with Id:" + cartId));
    }
    @Transactional(readOnly = true)
    public Cart getCartByUserId(Authentication authentication){
        User user = userRepository.findByEmail( authentication.getName()).orElseThrow(
                                () -> new ResourceNotFoundException("User not found"));
                                
        return cartRepository.findByUserId(user.getId()).orElseThrow(()-> new ResourceNotFoundException("No Cart Found for Costumer with Id:" + user.getId()));
    }
    
    //put
    @Transactional
    public Cart addToCart(Authentication authentication, int productId, int quantity){
        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        User user = userRepository.findByEmail( authentication.getName()).orElseThrow(
                                () -> new ResourceNotFoundException("User not found"));
        Cart cart = getCartByUserId(authentication);

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product not found with Id: " + productId));
  
       if(product.getStock() < quantity ){
            throw new ResourceNotFoundException("Not enough product in stock");
       }
       for (CartItem item: cart.getCartItems()){
            if (item.getProduct().getId()== productId){
                int newQuantity = item.getQuantity() + quantity;
                if (newQuantity > product.getStock()){
                    throw new ResourceNotFoundException("No Enough Product in stock");
                }
                item.setQuantity(newQuantity);

                return cartRepository.save(cart);
            }
        }
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);    
            cartItem.setCart(cart);
            
            cart.addCartItem(cartItem);
            return cartRepository.save(cart);
    }
    @Transactional
    public Cart removeFromCart(Authentication authentication, int productId, int quantity){

        if (quantity<= 0){
            throw new BadRequestException("Quantity must be greater than 0");
        }
        User user = userRepository.findByEmail( authentication.getName()).orElseThrow(
                                () -> new ResourceNotFoundException("User not found"));

        Cart cart = getCartByUserId(authentication);
        Iterator<CartItem> iterator = cart.getCartItems().iterator();

        while (iterator.hasNext() ){
            CartItem item = iterator.next();
            if (item.getProduct().getId() == productId){
                int newQuantity = item.getQuantity()-quantity;
                if (newQuantity <= 0){
                    iterator.remove();
                }
                else {
                    item.setQuantity(newQuantity);
                }
                return cartRepository.save(cart);
            }
        }
        throw new ResourceNotFoundException("Product is not in the cart");
    }

    @Transactional
    public void deleteCart(Authentication authentication) {
        Cart cart = getCartByUserId(authentication);
        cartRepository.delete(cart);
    }
    @Transactional
    public Cart clearCart(Authentication authentication){
        Cart existingCart = getCartByUserId(authentication);
        existingCart.getCartItems().clear();
        return cartRepository.save(existingCart);
    }
}
