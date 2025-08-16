package com.example.springdata_homework.model.dto.request;

import com.example.springdata_homework.enumeration.Status;
import com.example.springdata_homework.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequest {
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private Status status;
    private Long customerId;

    public Order toOrder(){
        return new Order(null,this.orderDate,this.totalAmount,this.status,null);
    }
}
