package com.ismail.Ecommerce.dto;


import com.ismail.Ecommerce.entity.Address;
import com.ismail.Ecommerce.entity.Customer;
import com.ismail.Ecommerce.entity.Order;
import com.ismail.Ecommerce.entity.OrderItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Data
@Getter
@Setter
public class Purchase {

    private Customer customer;
    private Order order;
    private Address shippingAddress;
    private Address billingAddress;

    private Set<OrderItem> orderItems;

    private String paymentIntentId;
}
