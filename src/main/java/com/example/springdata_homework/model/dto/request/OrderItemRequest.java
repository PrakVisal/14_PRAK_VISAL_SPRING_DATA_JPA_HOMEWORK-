package com.example.springdata_homework.model.dto.request;

import com.example.springdata_homework.enumeration.Status;
import com.example.springdata_homework.model.Order;
import com.example.springdata_homework.model.OrderItems;
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
public class OrderItemRequest {
    private Long quantity;
    private Long productId;

    public Order toOrder(BigDecimal price) {
        BigDecimal qty = BigDecimal.valueOf(this.quantity); // convert Long -> BigDecimal
        BigDecimal total = price.multiply(qty); // multiply safely
        return new Order(null, LocalDateTime.now(),total, Status.PENDING,null);
    }

    public OrderItems toOrderItems() {
//        return new OrderItems(null,null,null,this.quantity);
        OrderItems orderItems = new OrderItems();
        orderItems.setQuantity(this.quantity);
        return orderItems;
    }
}
