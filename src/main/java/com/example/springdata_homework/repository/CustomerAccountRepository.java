package com.example.springdata_homework.repository;

import com.example.springdata_homework.model.CustomerAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccounts, Long> {
    void deleteByCustomersId(Long id);
}
