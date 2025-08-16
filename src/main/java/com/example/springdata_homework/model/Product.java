package com.example.springdata_homework.model;

import com.example.springdata_homework.model.dto.request.ProductRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    private String description;

    public Product updateProduct(Long id, ProductRequest productRequest) {
        return new Product(id,productRequest.getName(),productRequest.getUnitPrice(),productRequest.getDescription());
    }
}
