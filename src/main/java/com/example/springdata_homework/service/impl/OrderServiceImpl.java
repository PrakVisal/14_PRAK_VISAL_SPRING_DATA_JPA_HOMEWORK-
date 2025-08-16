package com.example.springdata_homework.service.impl;

import com.example.springdata_homework.enumeration.CustomerSortBy;
import com.example.springdata_homework.exception.ResourceNotFoundException;
import com.example.springdata_homework.model.*;
import com.example.springdata_homework.model.dto.request.CustomerRequest;
import com.example.springdata_homework.model.dto.request.OrderItemRequest;
import com.example.springdata_homework.model.dto.request.OrderRequest;
import com.example.springdata_homework.model.dto.response.CreatedOrderResponse;
import com.example.springdata_homework.repository.*;
import com.example.springdata_homework.service.CustomerService;
import com.example.springdata_homework.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderItemRespository orderItemRespository;

    @Override
    @Transactional
    public CreatedOrderResponse createOrder(Long id, OrderItemRequest orderRequest) {
       Customers customers = customerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Product product = productRepository.findById(orderRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Order order = orderRequest.toOrder(product.getUnitPrice());
        order.setCustomers(customers);

        orderRepository.save(order);

        OrderItems orderItems = orderRequest.toOrderItems();
        orderItems.setOrder(order);
        orderItems.setProduct(product);

        CreatedOrderResponse response = new CreatedOrderResponse();
        response.setOrder(order);
        response.setCustomer(customers.toResponse());
        response.setProduct(product);

        orderItemRespository.save(orderItems);
        return response;
    }

    @Override
    public List<Order> getAllOrders(int page,
                                    int size,
                                    CustomerSortBy sortBy,
                                    Sort.Direction direction) {
        Sort sort = Sort.by(direction, sortBy.name());
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.getContent();
    }
}
