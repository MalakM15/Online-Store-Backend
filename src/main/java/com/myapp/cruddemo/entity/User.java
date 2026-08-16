package com.myapp.cruddemo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;
    
    @Column(name="email", unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="password", nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public enum Role { ADMIN, CUSTOMER}

    @OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.MERGE, 
                                CascadeType.DETACH, CascadeType.REFRESH},
                mappedBy = "user" )
    @JsonIgnore
    private List<Order> orders;
    @OneToMany(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST, CascadeType.MERGE, 
                                CascadeType.DETACH, CascadeType.REFRESH},
                mappedBy = "user" )
    private List<Review> reviews;
    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Cart cart;

    public User(){
    }
    public User(String firstName, String lastName, String email,String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() { 
        return password; 
    } 
    public void setPassword(String password) {
        this.password = password; 
    } 

    public Role getRole() { 
        return role;
    } 
    public void setRole(Role role) { 
        this.role = role; 
    }

    public List<Order> getOrders(){
        return orders;
    }
    public void setOrders(List<Order> orders){
        this.orders = orders;
    }

    public List<Review> getReview() {
        return reviews;
    }
    public void setReview(List<Review> review) {
        this.reviews = review;
    }

    public Cart getCart() { 
        return cart; 
    }
    public void setCart(Cart cart){

        if (cart == null) {
            if (this.cart != null) {
                this.cart.setUser(null); // Break the link if removing the cart
            }
        } else {
            cart.setUser(this); // Set the foreign key side automatically
        }
        this.cart = cart;
    }


    public void addOrder(Order theOrder){
        if (orders == null){
            orders = new ArrayList<>();
        }
        orders.add(theOrder);
        theOrder.setUser(this);

    }
    public void addReview(Review theReview){
        if (reviews == null){
            reviews = new ArrayList<>();
        }
        reviews.add(theReview);
        theReview.setUser(this);

    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", firstName=" + firstName 
                        + ", lastName=" + lastName + ", email=" + email 
                        + ", role=" +role + "]";
    }
}
