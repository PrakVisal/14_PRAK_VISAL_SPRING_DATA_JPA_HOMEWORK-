package com.example.springdata_homework.model;

import com.example.springdata_homework.enumeration.Status;
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

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customers customers;
}
