package com.example.springdata_homework.repository;

import com.example.springdata_homework.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
