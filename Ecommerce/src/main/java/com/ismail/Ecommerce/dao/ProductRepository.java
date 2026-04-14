package com.ismail.Ecommerce.dao;

import com.ismail.Ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200") // Allow cross-origin requests from the Angular frontend
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
