package com.example.springdata_homework.controller;

import com.example.springdata_homework.model.Product;
import com.example.springdata_homework.model.dto.request.ProductRequest;
import com.example.springdata_homework.model.dto.response.Response;
import com.example.springdata_homework.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.example.springdata_homework.utils.RequestUtils.getPaginatedResponse;
import static com.example.springdata_homework.utils.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    private ResponseEntity<Response<List<Product>>> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam Sort.Direction direction
            ) {
        List<Product> products = productService.getAllProducts(page,limit,sortBy,direction);
        int total = products.size();
        return ResponseEntity.ok(getPaginatedResponse(OK,"Get all products successfully",
                products,page,limit,total));
    }

    @PostMapping
    private ResponseEntity<Response<Product>> addProduct(
            @RequestBody ProductRequest productRequest) {
        Product product = productService.addProduct(productRequest);
        return ResponseEntity.created(URI.create("")).body(getResponse(
                CREATED,
                "Insert product successfully",
                product));
    }

    @PutMapping("/{id}")
    private ResponseEntity<Response<Product>> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest) {
        Product product = productService.updateProduct(id,productRequest);
        return ResponseEntity.ok(getResponse(
                OK,
                "Update product successfully"
                ,product));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Response<Product>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(getResponse(
                OK,
                "Deleted product successfully"
                ,null));
    }

    @GetMapping("/{id}")
    private ResponseEntity<Response<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(getResponse(
                OK,
                "Get product id "+ id +" successfully"
                ,product));
    }
}
