package com.example.springdata_homework.service.impl;

import com.example.springdata_homework.enumeration.ProductSortBy;
import com.example.springdata_homework.exception.ResourceNotFoundException;
import com.example.springdata_homework.model.Product;
import com.example.springdata_homework.model.dto.request.ProductRequest;
import com.example.springdata_homework.model.dto.response.ProductResponse;
import com.example.springdata_homework.repository.ProductRepository;
import com.example.springdata_homework.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public List<ProductResponse> getAllProducts(int page,
                                                int limit,
                                                ProductSortBy sortBy,
                                                Sort.Direction direction) {
        Sort sort = Sort.by(direction, sortBy.name());
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        Page<Product> products = productRepository.findAll(pageable);

        return products.getContent()
                .stream()
                .map(Product::toResponse)
                .toList();
    }

    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = productRequest.toProduct();
        productRepository.save(product);
        return product.toResponse();
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        Product updatedProduct = product.updateProduct(id,productRequest);

        productRepository.save(updatedProduct);
        return updatedProduct.toResponse();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        return product.toResponse();
    }
}
