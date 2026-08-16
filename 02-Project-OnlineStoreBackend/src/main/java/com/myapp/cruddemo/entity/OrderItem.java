package com.myapp.cruddemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="order_item")
public class OrderItem {

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
    @JoinColumn(name="order_id")
    private Order order;

    public OrderItem(){
    }
    public OrderItem(int quantity) {
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

    public Order getOrder(){
        return order;
    }
    public void setOrder(Order order){
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }


}
