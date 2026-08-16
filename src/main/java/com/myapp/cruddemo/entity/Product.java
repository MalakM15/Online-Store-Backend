package com.myapp.cruddemo.entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="product")
public class Product {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;
    
    @Column(name="price")
    private double price;

    @Column(name="stock")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    
    @OneToMany( mappedBy = "product" , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderItem> orderItems;
    
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews;

    public Product(){
    }
    public Product(String name, String description, double price, int stock) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    public void addCartItem(CartItem cartItem) {

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        cartItems.add(cartItem);
        cartItem.setProduct(this);
    }


    public void addOrderItem(OrderItem orderItem) {

        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }

        orderItems.add(orderItem);
        orderItem.setProduct(this);
    }


    public void addReview(Review review) {

        if (reviews == null) {
            reviews = new ArrayList<>();
        }

        reviews.add(review);
        review.setProduct(this);
    }


    @Override
    public String toString() {

        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}