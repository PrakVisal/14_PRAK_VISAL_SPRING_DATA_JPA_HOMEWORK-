package com.example.springdata_homework.model.dto.response;

import com.example.springdata_homework.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private Status status;
}
