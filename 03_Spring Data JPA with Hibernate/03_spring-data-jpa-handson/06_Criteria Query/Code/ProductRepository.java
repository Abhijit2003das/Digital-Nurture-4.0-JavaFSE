package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
