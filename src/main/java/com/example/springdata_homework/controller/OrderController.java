package com.example.springdata_homework.controller;

import com.example.springdata_homework.enumeration.CustomerSortBy;
import com.example.springdata_homework.enumeration.OrderSortBy;
import com.example.springdata_homework.enumeration.Status;
import com.example.springdata_homework.model.Customers;
import com.example.springdata_homework.model.Order;
import com.example.springdata_homework.model.dto.request.OrderItemRequest;
import com.example.springdata_homework.model.dto.request.OrderProductsRequest;
import com.example.springdata_homework.model.dto.request.OrderRequest;
import com.example.springdata_homework.model.dto.response.CreatedOrderResponse;
import com.example.springdata_homework.model.dto.response.OrderResponse;
import com.example.springdata_homework.model.dto.response.Response;
import com.example.springdata_homework.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create order(s) for a customer")
    @PostMapping("customer/{customer-id}")
    private ResponseEntity<Response<CreatedOrderResponse>> createOrder(
            @PathVariable("customer-id") Long id,
            @RequestBody OrderProductsRequest orderRequest) {
        CreatedOrderResponse orderResponse = orderService.createOrder(id,orderRequest);
        return ResponseEntity.created(URI.create("")).body(getResponse(CREATED,
                "Created order successfully",orderResponse));
    }

    @Operation(summary = "List orders by customer (paginated)")
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

    @Operation(summary = "Get order details by ID")
    @GetMapping("/{order-id}")
    ResponseEntity<Response<CreatedOrderResponse>> getOrderDetailsById(
            @PathVariable("order-id") Long orderId
    ) {
        CreatedOrderResponse orders = orderService.getOrderDetailsById(orderId);
        return ResponseEntity.ok(getResponse(OK,"Get order details successfully",orders));
    }

    @Operation(summary = "Update order status")
    @PutMapping("/{order-id}/status")
    ResponseEntity<Response<CreatedOrderResponse>> updateOrderStatus(@PathVariable("order-id") Long orderId,
                                                                     @RequestParam Status status){
        CreatedOrderResponse order = orderService.updateOrderStatus(orderId,status);
        return ResponseEntity.ok().body(getResponse(OK,"Updated order status successfully",order));

    }

    @Operation(summary = "Delete order by ID")
    @DeleteMapping("/{order-id}")
    ResponseEntity<Response<CreatedOrderResponse>> deleteOrder(@PathVariable("order-id") Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().body(getResponse(OK,"Updated order status successfully",null));

    }
}
