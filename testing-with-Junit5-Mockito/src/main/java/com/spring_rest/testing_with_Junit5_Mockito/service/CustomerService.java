package com.spring_rest.testing_with_Junit5_Mockito.service;

import com.spring_rest.testing_with_Junit5_Mockito.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    List<Customer> getAllCustomers();
}
