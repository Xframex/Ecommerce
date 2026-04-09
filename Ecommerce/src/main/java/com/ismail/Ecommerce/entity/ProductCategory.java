package com.ismail.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "product_category")
//@Data --> We can use @Data annotation from Lombok to generate getters, setters, and other utility methods for the ProductCategory class. However, since the class is currently empty and does not have any fields, we can omit the @Data annotation for now. If you plan to add fields to the ProductCategory class in the future, you can consider adding the @Data annotation at that time to simplify your code.
@Getter
@Setter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Product> products;

}
