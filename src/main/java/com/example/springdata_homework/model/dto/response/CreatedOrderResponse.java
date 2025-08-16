package com.example.springdata_homework.model.dto.response;

import com.example.springdata_homework.model.Order;
import com.example.springdata_homework.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedOrderResponse {
    private Order order;
    private CustomerResponse customer;
    private Product product;
}
