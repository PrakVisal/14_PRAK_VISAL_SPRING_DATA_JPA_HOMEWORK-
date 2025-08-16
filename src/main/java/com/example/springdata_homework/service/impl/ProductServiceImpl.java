package com.example.springdata_homework.service.impl;

import com.example.springdata_homework.exception.ResourceNotFoundException;
import com.example.springdata_homework.model.Product;
import com.example.springdata_homework.model.dto.request.ProductRequest;
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
    public List<Product> getAllProducts(int page,
                                        int limit,
                                        String sortBy,
                                        Sort.Direction direction) {
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page-1,limit,sort);
        Page<Product> products = productRepository.findAll(pageable);
        return products.getContent();
    }

    @Override
    public Product addProduct(ProductRequest productRequest) {
        Product product = productRequest.toProduct();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        Product updatedProduct = product.updateProduct(id,productRequest);

        return productRepository.save(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }
}
