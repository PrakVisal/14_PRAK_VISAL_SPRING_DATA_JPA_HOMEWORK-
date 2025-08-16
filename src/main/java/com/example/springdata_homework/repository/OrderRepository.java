package com.example.springdata_homework.repository;

import com.example.springdata_homework.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    void deleteByCustomersId(Long id);

    Page<Order> findByCustomersId(Long id, Pageable pageable);
}
