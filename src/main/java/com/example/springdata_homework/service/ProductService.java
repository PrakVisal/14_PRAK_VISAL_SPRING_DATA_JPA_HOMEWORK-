package com.example.springdata_homework.service;

import com.example.springdata_homework.enumeration.ProductSortBy;
import com.example.springdata_homework.model.Product;
import com.example.springdata_homework.model.dto.request.ProductRequest;
import com.example.springdata_homework.model.dto.response.ProductResponse;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    List<ProductResponse> getAllProducts(int page, int limit, ProductSortBy sortBy, Sort.Direction direction);

    ProductResponse addProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);
}
