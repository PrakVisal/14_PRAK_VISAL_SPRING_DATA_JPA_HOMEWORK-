package com.example.springdata_homework.model.dto.request;

import com.example.springdata_homework.model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductsRequest {
    private List<OrderItemRequest> orderRequests;
}
