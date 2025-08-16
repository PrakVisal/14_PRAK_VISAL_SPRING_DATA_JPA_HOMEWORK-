package com.example.springdata_homework.service.impl;

import com.example.springdata_homework.enumeration.CustomerSortBy;
import com.example.springdata_homework.enumeration.OrderSortBy;
import com.example.springdata_homework.enumeration.Status;
import com.example.springdata_homework.exception.ResourceNotFoundException;
import com.example.springdata_homework.model.*;
import com.example.springdata_homework.model.dto.request.CustomerRequest;
import com.example.springdata_homework.model.dto.request.OrderItemRequest;
import com.example.springdata_homework.model.dto.request.OrderProductsRequest;
import com.example.springdata_homework.model.dto.request.OrderRequest;
import com.example.springdata_homework.model.dto.response.CreatedOrderResponse;
import com.example.springdata_homework.model.dto.response.OrderResponse;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public CreatedOrderResponse createOrder(Long customerId, OrderProductsRequest orderRequest) {

        //Find customer using requestParam
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        //Create order
        Order order = new Order();
        order.setCustomers(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);

        List<OrderItems> orderItemsList = new ArrayList<>();

        for (OrderItemRequest itemReq : orderRequest.getOrderRequests()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            OrderItems orderItem = new OrderItems();
            orderItem.setOrderItemsId(new OrderItemsId());
            orderItem.getOrderItemsId().setProductId(product.getId());

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());

            orderItemsList.add(orderItem);
        }

        BigDecimal total = orderItemsList.stream()
                .map(item -> item.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        order.setOrderItems(orderItemsList);

        //Save order (cascade saves orderItems)
        orderRepository.save(order);

        //Build response
        OrderResponse orderResponse = order.toResponse();

        return new CreatedOrderResponse(
                orderResponse,
                customer.toResponse(),
                orderItemsList.stream().map(oi -> oi.getProduct().toResponse()).toList()
        );
    }



    @Override
    public List<OrderResponse> getAllOrders(Long customerId,
                                            int page,
                                            int size,
                                            CustomerSortBy sortBy,
                                            Sort.Direction direction) {

        Sort sort = Sort.by(direction, sortBy.name());
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Order> orders = orderRepository.findByCustomersId(customerId,pageable);
        return orders.getContent().stream().map(Order::toResponse).toList();
    }

    @Override
    public CreatedOrderResponse updateOrderStatus(Long orderId, Status status) {
        //Find order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        //Update status
        order.setStatus(status);
        orderRepository.save(order);

        //Find customer
        Customers customer = customerRepository.findById(order.getCustomers().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        //Find all order items for this order
        List<OrderItems> orderItemsList = orderItemRespository.findByOrder(order);

        //Build response
        OrderResponse orderResponse = order.toResponse();

        return new CreatedOrderResponse(
                orderResponse,
                customer.toResponse(),
                orderItemsList.stream()
                        .map(oi -> oi.getProduct().toResponse())
                        .toList()
        );
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderRepository.delete(order);
    }

    @Override
    public CreatedOrderResponse getOrderDetailsById(Long orderId)
    {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        Customers customers = customerRepository
                .findById(order.getCustomers().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<OrderItems> orderItemsList = orderItemRespository.findByOrder(order);

        CreatedOrderResponse response = new CreatedOrderResponse();
        response.setOrder(order.toResponse());
        response.setCustomer(customers.toResponse());
        response.setProduct(orderItemsList.stream().map(oi -> oi.getProduct().toResponse()).toList());

        return response;
    }
}
