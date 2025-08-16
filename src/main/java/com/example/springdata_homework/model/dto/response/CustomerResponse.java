package com.example.springdata_homework.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String username;
    private String password;
    private Boolean isActive;
//    private CustomerAccountResponse account;
}

