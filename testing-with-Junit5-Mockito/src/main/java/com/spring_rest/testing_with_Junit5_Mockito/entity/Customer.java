package com.spring_rest.testing_with_Junit5_Mockito.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long custId;

    @Column(name = "customer_name", nullable = false)
    private String custName;

    @Column(name = "customer_email", nullable = false, unique = true)
    private String custContactNo;
}