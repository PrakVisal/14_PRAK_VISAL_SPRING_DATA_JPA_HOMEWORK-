package com.example.springdata_homework.repository;

import com.example.springdata_homework.model.CustomerAccounts;
import com.example.springdata_homework.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
