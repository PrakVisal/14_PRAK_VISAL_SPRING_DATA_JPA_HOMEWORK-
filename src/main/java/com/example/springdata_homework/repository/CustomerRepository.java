package com.example.springdata_homework.repository;

import com.example.springdata_homework.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers,Long> {

}
