package com.GloLogisticsFreightManagement.IntroductiontoSpringDataJPA.controller;

import com.GloLogisticsFreightManagement.IntroductiontoSpringDataJPA.model.Customer;
import com.GloLogisticsFreightManagement.IntroductiontoSpringDataJPA.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public String addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return "Customer added successfully!";
    }

    @GetMapping("/getAll")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/getById/{id}")
    public Customer getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/update")
    public String updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
        return "Customer updated successfully!";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully!";
    }
}