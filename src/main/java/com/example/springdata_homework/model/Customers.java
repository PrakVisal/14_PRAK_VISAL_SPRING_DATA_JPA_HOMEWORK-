package com.example.springdata_homework.model;

import com.example.springdata_homework.model.dto.request.CustomerRequest;
import com.example.springdata_homework.model.dto.response.CustomerResponse;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String name;
    private String address;

    @Column(name = "phone_number",unique = true)
    private String phoneNumber;

    @OneToOne(mappedBy = "customers",
              cascade = CascadeType.ALL,
              orphanRemoval = true,
              fetch = FetchType.EAGER)
    @JsonManagedReference
    private CustomerAccounts customerAccounts;

    @OneToMany(
            mappedBy =  "customers",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();

    public CustomerResponse toResponse() {
        return new CustomerResponse(this.id,this.name,this.address,this.phoneNumber,
                this.customerAccounts.getUsername(),
                this.customerAccounts.getPassword(),
                this.customerAccounts.getIsActive());
    }
}
