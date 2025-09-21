package com.spring_rest.testing_with_Junit5_Mockito.controller;

import com.spring_rest.testing_with_Junit5_Mockito.entity.Customer;
import com.spring_rest.testing_with_Junit5_Mockito.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers =
                customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
