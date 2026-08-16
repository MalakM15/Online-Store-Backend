package com.myapp.cruddemo.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;


@Entity
@Table(name="cart")

public class Cart {

    
    //annotate the class as an entity and map to db table

    //define the fields

    //annotate the fields
    //**set up mapping to instructorDetail entity */
    //create constructors

    //generate getter/setter
    
    //generate toString() method
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "cart", fetch= FetchType.LAZY ,orphanRemoval = true,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<CartItem> cartItems;
    public Cart(){
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }


    public void addCartItem(CartItem cartItem){
        if (cartItems == null){
            cartItems = new ArrayList<>();
        }
        
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : "null")+
                '}';
    }
    
}
