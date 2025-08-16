package com.example.springdata_homework.model.dto.response;

import com.example.springdata_homework.model.Order;
import com.example.springdata_homework.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedOrderResponse {
    private OrderResponse order;
    private CustomerResponse customer;
    private List<ProductResponse> product;
}
