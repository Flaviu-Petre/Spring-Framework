package com.RepoInSpring.RepositoriesInSpringDataJPA.service;

import com.RepoInSpring.RepositoriesInSpringDataJPA.model.Customer;
import com.RepoInSpring.RepositoriesInSpringDataJPA.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public boolean existsByEmail(String custEmail) {
        return customerRepository.findAll().stream()
                .anyMatch(customer -> customer.getCustEmail().equalsIgnoreCase(custEmail));
    }

    @Override
    public boolean existsById(Integer id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Optional<Customer> findCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findAll().stream()
                .filter(customer -> customer.getCustEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public long getCustomerCount() {
        return customerRepository.count();
    }
    @Override
    public Customer findCustomer(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }


}
