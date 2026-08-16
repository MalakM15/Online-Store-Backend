package com.myapp.cruddemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name="cart_item")

public class CartItem {

    //annotate the class as an entity and map to db table

    //define the fields

    //annotate the fields

    //create constructors

    //generate getter/setter
    //generate toString() method

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name="cart_id")
    @JsonIgnore
    private Cart cart;

    public CartItem(){

    }
    public CartItem(int quantity) {
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart getCart(){
        return cart;
    }
    public void setCart(Cart cart){
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String toString() {
        return "InstructorDetail{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }


}
