package com.myapp.cruddemo.service;

import com.myapp.cruddemo.dao.CartRepository;
import com.myapp.cruddemo.dao.ProductRepository;
import com.myapp.cruddemo.entity.Cart;
import com.myapp.cruddemo.entity.CartItem;
import com.myapp.cruddemo.entity.Product;
import com.myapp.cruddemo.exception.BadRequestException;
import com.myapp.cruddemo.exception.ResourceNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Iterator;
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCart(int cartId){
        return cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("No Cart Found with Id:" + cartId));
    }
    @Transactional(readOnly = true)
    public Cart getCartByUserId(int userId){

        return cartRepository.findByUserId(userId).orElseThrow(()-> new ResourceNotFoundException("No Cart Found for Costumer with Id:" + userId));
    }
    
    //post
    public Cart updateCart(int userId, List<CartItem> newCartItems){

        Cart existingCart = getCartByUserId(userId);
        existingCart.setCartItems(newCartItems);
        
        return cartRepository.save(existingCart);
    }
    //put
    public Cart addToCart(int userId, int productId, int quantity){
        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }
        Cart cart = getCartByUserId(userId);

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
    
    public Cart removeFromCart(int userId, int productId, int quantity){

        if (quantity<= 0){
            throw new BadRequestException("Quantity must be greater than 0");
        }
        Cart cart = getCartByUserId(userId);
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


    public void deleteCart(int userId) {
        Cart cart = getCartByUserId(userId);
        cartRepository.delete(cart);
    }
    public Cart clearCart(int userId){
        Cart existingCart = getCartByUserId(userId);
        existingCart.getCartItems().clear();
        return cartRepository.save(existingCart);
    }
}
