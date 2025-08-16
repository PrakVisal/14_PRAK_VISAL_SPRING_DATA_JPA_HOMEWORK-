package com.example.springdata_homework.model;

import com.example.springdata_homework.enumeration.Status;
import com.example.springdata_homework.model.dto.request.CustomerRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.jdi.PrimitiveValue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccounts {
    @Id
    @Column(name = "customer_id")
    private Long id;

    private String username;
    private String password;
    private Boolean isActive;

    @OneToOne
    @MapsId
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customers customers;

    public CustomerAccounts updateCustomerAccount(CustomerRequest customerRequest){
       return new CustomerAccounts(
               null,
               customerRequest.getUsername(),
               customerRequest.getPassword(),
               true,
               null);
    }
}
