package com.ismail.Ecommerce.dao;

import com.ismail.Ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(origins = "http://localhost:4200") // Allow cross-origin requests from the Angular frontend
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(@Param ("id") Long id, Pageable pageable);

    //findByNameContaining is a method provided by Spring Data JPA that allows you to search for products based on a partial match of their name. The @Param annotation is used to specify the parameter name in the query.
    Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);


}


