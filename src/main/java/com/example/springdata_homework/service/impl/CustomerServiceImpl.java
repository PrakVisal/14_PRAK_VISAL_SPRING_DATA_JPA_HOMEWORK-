package com.example.springdata_homework.service.impl;

import com.example.springdata_homework.enumeration.CustomerSortBy;
import com.example.springdata_homework.exception.ResourceNotFoundException;
import com.example.springdata_homework.model.CustomerAccounts;
import com.example.springdata_homework.model.Customers;
import com.example.springdata_homework.model.Product;
import com.example.springdata_homework.model.dto.request.CustomerRequest;
import com.example.springdata_homework.model.dto.response.CustomerResponse;
import com.example.springdata_homework.repository.CustomerAccountRepository;
import com.example.springdata_homework.repository.CustomerRepository;
import com.example.springdata_homework.repository.OrderRepository;
import com.example.springdata_homework.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerAccountRepository customerAccountRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<CustomerResponse> getAllCustomers(int page,
                                                  int size,
                                                  CustomerSortBy sortBy,
                                                  Sort.Direction direction) {
        Sort sort = Sort.by(direction, sortBy.name());
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Customers> customers = customerRepository.findAll(pageable);

        return customers.getContent().stream().map(Customers::toResponse).toList();
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customers customers = customerRequest.toCustomer();

        CustomerAccounts customerAccounts = customerRequest.toCustomerAccount();

        customerAccounts.setCustomers(customers);
        customers.setCustomerAccounts(customerAccounts);

        customerRepository.save(customers);
        return customers.toResponse();
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customers customers = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return customers.toResponse();
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {

        Customers customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        CustomerAccounts account = customerAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerAccount not found"));

        // Update fields directly on managed entities
        customer.setName(customerRequest.getName());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setAddress(customerRequest.getAddress());

        account.setUsername(customerRequest.getUsername());
        account.setPassword(customerRequest.getPassword());
        account.setIsActive(true);

        // Ensure bidirectional mapping is correct
        account.setCustomers(customer);
        customer.setCustomerAccounts(account);

        return customer.toResponse();

    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customers customers = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        orderRepository.deleteByCustomersId(id);
        customerAccountRepository.deleteByCustomersId(id);
        customerRepository.delete(customers);
    }
}
