package com.example.springdata_homework.service;

import com.example.springdata_homework.enumeration.CustomerSortBy;
import com.example.springdata_homework.enumeration.OrderSortBy;
import com.example.springdata_homework.enumeration.Status;
import com.example.springdata_homework.model.Customers;
import com.example.springdata_homework.model.Order;
import com.example.springdata_homework.model.dto.request.CustomerRequest;
import com.example.springdata_homework.model.dto.request.OrderItemRequest;
import com.example.springdata_homework.model.dto.request.OrderProductsRequest;
import com.example.springdata_homework.model.dto.request.OrderRequest;
import com.example.springdata_homework.model.dto.response.CreatedOrderResponse;
import com.example.springdata_homework.model.dto.response.OrderResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderService {
    CreatedOrderResponse createOrder(Long id, OrderProductsRequest orderRequest);

    List<OrderResponse> getAllOrders(Long customerId,int page, int size, CustomerSortBy sortBy, Sort.Direction direction);

    CreatedOrderResponse updateOrderStatus(Long orderId, Status status);

    void deleteOrder(Long orderId);

    CreatedOrderResponse getOrderDetailsById(Long orderId);
}
