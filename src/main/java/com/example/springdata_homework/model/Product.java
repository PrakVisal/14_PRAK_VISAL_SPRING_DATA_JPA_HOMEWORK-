package com.example.springdata_homework.model;

import com.example.springdata_homework.model.dto.request.ProductRequest;
import com.example.springdata_homework.model.dto.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<OrderItems> orderItems = new ArrayList<>();

    public Product updateProduct(Long id, ProductRequest productRequest) {
        return new Product(id,productRequest.getName(),productRequest.getUnitPrice(),productRequest.getDescription(),null);
    }

    public ProductResponse toResponse() {
        return new ProductResponse(
                this.id,
                this.name,
                this.unitPrice,
                this.description
        );
    }
}
