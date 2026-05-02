package com.ismail.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_tracking_number")
    private String orderTrackingNumber;

    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private Date lastUpdated;

    // one order can have many order items - cascade all operations to order items it means that when we save an order, all the associated order items will also be saved, and when we delete an order, all the associated order items will also be deleted.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    // association with customer --> many orders can be placed by one customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // association with shipping address --> one order can have one shipping address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private Address shippingAddress;

    // association with billing address --> one order can have one billing address
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    private Address billingAddress;





    // Add an order item to the order and set the order reference in the order item
    public void add(OrderItem Items) {
        if (Items != null) {
            if (orderItems == null) {
                orderItems = new HashSet<>();
            }
            // Add the order item to the order's set of order items
            orderItems.add(Items);
            Items.setOrder(this);
        }
    }
}

