package com.example.springdata_homework.model.dto.request;

import com.example.springdata_homework.model.CustomerAccounts;
import com.example.springdata_homework.model.Customers;
import com.example.springdata_homework.model.dto.response.CustomerResponse;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String name;
    private String address;
    private String phoneNumber;
    private String username;
    private String password;

    public CustomerAccounts toCustomerAccount(){
        return new CustomerAccounts(null,this.username,this.password,true,null);
    }
    public Customers toCustomer(){
        return new Customers(null,name,address,phoneNumber,
                toCustomerAccount(),null);
    }
//    public CustomerResponse toCustomerResponse() {
//        return new CustomerResponse(null,this.name,this.address,this.phoneNumber,this.username,this.password,this.isActive);
//    }
}
