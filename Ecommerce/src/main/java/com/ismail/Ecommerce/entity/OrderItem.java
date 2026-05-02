package com.ismail.Ecommerce.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "image_url")
    private String ImageUrl;

    @Column(name = "product_name")
    private BigDecimal UnitPrice;

    @Column(name = "quantity")
    private int Quantity;

    @Column(name = "product_id")
    private Long ProductId;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
