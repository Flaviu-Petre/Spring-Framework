package com.RepoInSpring.RepositoriesInSpringDataJPA.service;

import com.RepoInSpring.RepositoriesInSpringDataJPA.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    // Basic CRUD operations
    void addCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Integer id);
    void updateCustomer(Customer customer);
    void deleteCustomer(Integer id);

    // Validation methods
    boolean existsByEmail(String email);
    boolean existsById(Integer id);
    Optional<Customer> findCustomerById(Integer id);
    Optional<Customer> findByEmail(String email);

    // Utility methods
    long getCustomerCount();
    Customer findCustomer(Integer id);
}
