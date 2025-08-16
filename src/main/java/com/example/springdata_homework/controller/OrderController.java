package com.example.springdata_homework.controller;

import com.example.springdata_homework.enumeration.CustomerSortBy;
import com.example.springdata_homework.model.Customers;
import com.example.springdata_homework.model.Order;
import com.example.springdata_homework.model.dto.request.OrderItemRequest;
import com.example.springdata_homework.model.dto.request.OrderRequest;
import com.example.springdata_homework.model.dto.response.CreatedOrderResponse;
import com.example.springdata_homework.model.dto.response.OrderResponse;
import com.example.springdata_homework.model.dto.response.Response;
import com.example.springdata_homework.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("customer/{customer-id}")
    private ResponseEntity<Response<CreatedOrderResponse>> createOrder(@PathVariable("customer-id") Long id, @RequestBody OrderItemRequest orderRequest) {
        CreatedOrderResponse orderResponse = orderService.createOrder(id,orderRequest);
        return ResponseEntity.created(URI.create("")).body(getResponse(CREATED,
                "Created order successfully",orderResponse));
    }

    @GetMapping("/customers/{id}")
    ResponseEntity<Response<List<OrderResponse>>> getAllOrders(
            @PathVariable("id") Long customerId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam CustomerSortBy sortBy,
            @RequestParam Sort.Direction direction
    ) {
        List<OrderResponse> orders = orderService.getAllOrders(customerId,page,size,sortBy,direction);
        int total = orders.size();
        return ResponseEntity.ok(getPaginatedResponse(OK,"Get all orders successfully",orders,page,size,total));
    }
}
