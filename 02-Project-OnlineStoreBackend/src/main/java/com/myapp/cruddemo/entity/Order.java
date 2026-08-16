package com.myapp.cruddemo.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

@Entity
@Table(name="orders")

public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="status")
    private String status;
    @Column(name="order_date")  
    private LocalDate orderDate;
    @Column(name="total_price")
    private double totalPrice;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, 
                          CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn (name="user_id") 
    private User user;
    @OneToMany(mappedBy="order",fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<OrderItem> orderItems;


    public Order(){

    }
    public Order(String status, LocalDate orderDate, double totalPrice) {
        this.status = status;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }



    public void addOrderItem(OrderItem orderItem) {

        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }

        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    @Override
    public String toString() {
        return "Order [id=" + id
                + ", status=" + status
                + ", orderDate=" + orderDate
                + ", totalPrice=" + totalPrice + "]";
    }
}
