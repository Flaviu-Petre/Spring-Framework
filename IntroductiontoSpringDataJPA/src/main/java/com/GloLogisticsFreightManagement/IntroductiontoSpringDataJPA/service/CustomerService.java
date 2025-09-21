package com.GloLogisticsFreightManagement.IntroductiontoSpringDataJPA.service;

import com.GloLogisticsFreightManagement.IntroductiontoSpringDataJPA.model.Customer;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);
    Customer getCustomerById(Integer id);
    void updateCustomer(Customer customer);
    void deleteCustomer(Integer id);
    List<Customer> getAllCustomers();
}
