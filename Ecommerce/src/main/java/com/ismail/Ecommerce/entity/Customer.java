package com.ismail.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    // association with order --> one customer can place many orders and cascade all operations to orders it means that when we save a customer, all the associated orders will also be saved, and when we delete a customer, all the associated orders will also be deleted.
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    public void addOrder(Order order) {
        // this method is used to add an order to the customer's set of orders and also to set the customer
        // reference in the order object. This ensures
        // that the bidirectional relationship between Customer and Order is properly maintained.
        if (order != null) {
            if (orders == null) {
                orders = new HashSet<>();
            }
            orders.add(order);
            order.setCustomer(this);
        }
    }

}
