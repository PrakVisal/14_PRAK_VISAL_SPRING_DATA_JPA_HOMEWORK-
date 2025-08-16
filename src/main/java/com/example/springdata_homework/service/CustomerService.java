package com.example.springdata_homework.service;

import com.example.springdata_homework.enumeration.CustomerSortBy;
import com.example.springdata_homework.model.Customers;
import com.example.springdata_homework.model.dto.request.CustomerRequest;
import com.example.springdata_homework.model.dto.response.CustomerResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomers(int page, int size, CustomerSortBy sortBy, Sort.Direction direction);

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse getCustomerById(Long id);

    CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest);

    void deleteCustomer(Long id);
}
