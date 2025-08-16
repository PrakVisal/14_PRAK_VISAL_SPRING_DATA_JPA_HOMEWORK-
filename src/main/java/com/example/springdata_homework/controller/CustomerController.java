package com.example.springdata_homework.controller;

import com.example.springdata_homework.enumeration.CustomerSortBy;
import com.example.springdata_homework.model.Customers;
import com.example.springdata_homework.model.dto.request.CustomerRequest;
import com.example.springdata_homework.model.dto.response.CustomerResponse;
import com.example.springdata_homework.model.dto.response.Response;
import com.example.springdata_homework.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.example.springdata_homework.utils.RequestUtils.getPaginatedResponse;
import static com.example.springdata_homework.utils.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController{
    private final CustomerService customerService;

    @Operation(summary = "Get paginated list of customers")
    @GetMapping
    ResponseEntity<Response<List<CustomerResponse>>> getAllCustomers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam CustomerSortBy sortBy,
            @RequestParam Sort.Direction direction
            ) {
        List<CustomerResponse> customers = customerService.getAllCustomers(page,size,sortBy,direction);
        int total = customers.size();
        return ResponseEntity.ok(getPaginatedResponse(OK,"Get all customers successfully",customers,page,size,total));
    }

    @Operation(summary = "Create a new customer")
    @PostMapping
    ResponseEntity<Response<CustomerResponse>> createCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customers = customerService.createCustomer(customerRequest);
        return ResponseEntity.created(URI.create("")).body(getResponse(CREATED,"Create customer successfully",customers));
    }

    @Operation(summary = "Get customer by ID")
    @GetMapping("/{id}")
    ResponseEntity<Response<CustomerResponse>> getCustomerById(@PathVariable Long id){
        CustomerResponse customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(getResponse(OK,"Get a customer by id successfully",customer));
    }

    @Operation(summary = "Update existing customer")
    @PutMapping("/{id}")
    ResponseEntity<Response<CustomerResponse>> updateCustomer(@PathVariable Long id,@RequestBody CustomerRequest customerRequest){
        CustomerResponse customers = customerService.updateCustomer(id,customerRequest);
        return ResponseEntity.ok(getResponse(OK,"Updated customer successfully",customers));
    }

    @Operation(summary = "Delete customer")
    @DeleteMapping("/{id}")
    ResponseEntity<Response<CustomerResponse>> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(getResponse(OK,"Deleted customer successfully",null));
    }
}
