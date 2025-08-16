package com.example.springdata_homework.service;

import com.example.springdata_homework.model.Product;
import com.example.springdata_homework.model.dto.request.ProductRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    List<Product> getAllProducts(int page, int limit, String sortBy, Sort.Direction direction);

    Product addProduct(ProductRequest productRequest);

    Product updateProduct(Long id, ProductRequest productRequest);

    void deleteProduct(Long id);

    Product getProductById(Long id);
}
