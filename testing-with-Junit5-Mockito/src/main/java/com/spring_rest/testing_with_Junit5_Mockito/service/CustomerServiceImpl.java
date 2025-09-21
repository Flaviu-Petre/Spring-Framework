package com.spring_rest.testing_with_Junit5_Mockito.service;

import com.spring_rest.testing_with_Junit5_Mockito.entity.Customer;
import com.spring_rest.testing_with_Junit5_Mockito.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}