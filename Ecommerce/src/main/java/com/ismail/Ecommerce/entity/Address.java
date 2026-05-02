package com.ismail.Ecommerce.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "zip_code")
    private String zipCode;


    // association with order --> one address can be associated with one order
    // and cascade all operations to orders it means that when we save an address,
    // the associated order will also be saved, and when we delete an address,
    // the associated order will also be deleted.
    @OneToOne(mappedBy = "shippingAddress", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Order order;


}
