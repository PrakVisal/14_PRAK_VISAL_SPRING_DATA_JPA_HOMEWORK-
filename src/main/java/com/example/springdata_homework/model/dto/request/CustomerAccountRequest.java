package com.example.springdata_homework.model.dto.request;

import com.example.springdata_homework.enumeration.Status;
import com.example.springdata_homework.model.CustomerAccounts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CustomerAccountRequest {
    private String username;
    private String password;
    private Boolean isActive;

    public CustomerAccounts toCustomerAccount(){
        return new CustomerAccounts(null,this.username,this.password,this.isActive,null);
    }
}
