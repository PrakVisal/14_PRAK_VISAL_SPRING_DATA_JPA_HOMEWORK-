package com.example.springdata_homework.model.dto.request;

import com.example.springdata_homework.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private BigDecimal unitPrice;
    private String description;

    public Product toProduct(){
        return new Product(null,name,unitPrice,description);
    }
}
