package com.example.springdata_homework.repository;

import com.example.springdata_homework.model.Order;
import com.example.springdata_homework.model.OrderItems;
import com.example.springdata_homework.model.OrderItemsId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRespository extends JpaRepository<OrderItems, OrderItemsId> {
    List<OrderItems> findByOrder(Order order);
}
