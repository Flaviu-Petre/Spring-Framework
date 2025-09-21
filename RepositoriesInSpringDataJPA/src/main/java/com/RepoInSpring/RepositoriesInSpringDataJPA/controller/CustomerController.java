package com.RepoInSpring.RepositoriesInSpringDataJPA.controller;

import com.RepoInSpring.RepositoriesInSpringDataJPA.model.Customer;
import com.RepoInSpring.RepositoriesInSpringDataJPA.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addCustomer(@RequestBody Customer customer) {
        Map<String, String> response = new HashMap<>();

        try {
            if (customer == null) {
                response.put("error", "Customer data cannot be null");
                return ResponseEntity.badRequest().body(response);
            }

            if (!StringUtils.hasText(customer.getCustName())) {
                response.put("error", "Customer name is required");
                return ResponseEntity.badRequest().body(response);
            }

            if (!StringUtils.hasText(customer.getCustEmail())) {
                response.put("error", "Customer email is required");
                return ResponseEntity.badRequest().body(response);
            }

            if (!isValidEmail(customer.getCustEmail())) {
                response.put("error", "Invalid email format");
                return ResponseEntity.badRequest().body(response);
            }

            if (customerService.existsByEmail(customer.getCustEmail())) {
                response.put("error", "Customer with this email already exists");
                return ResponseEntity.badRequest().body(response);
            }

            customerService.addCustomer(customer);
            response.put("message", "Customer added successfully!");
            response.put("customerId", customer.getCustId().toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.put("error", "Failed to add customer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();

            if (customers.isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "No customers found");
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.ok(customers);

        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to retrieve customers: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Integer id) {
        Map<String, String> response = new HashMap<>();

        try {
            // Validation checks
            if (id == null || id <= 0) {
                response.put("error", "Invalid customer ID. ID must be a positive number");
                return ResponseEntity.badRequest().body(response);
            }

            Optional<Customer> customer = customerService.findCustomerById(id);

            if (customer.isEmpty()) {
                response.put("error", "Customer not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            return ResponseEntity.ok(customer.get());

        } catch (Exception e) {
            response.put("error", "Failed to retrieve customer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateCustomer(@RequestBody Customer customer) {
        Map<String, String> response = new HashMap<>();

        try {
            // Validation checks
            if (customer == null) {
                response.put("error", "Customer data cannot be null");
                return ResponseEntity.badRequest().body(response);
            }

            if (customer.getCustId() == null || customer.getCustId() <= 0) {
                response.put("error", "Valid customer ID is required for update");
                return ResponseEntity.badRequest().body(response);
            }

            if (!StringUtils.hasText(customer.getCustName())) {
                response.put("error", "Customer name is required");
                return ResponseEntity.badRequest().body(response);
            }

            if (!StringUtils.hasText(customer.getCustEmail())) {
                response.put("error", "Customer email is required");
                return ResponseEntity.badRequest().body(response);
            }

            if (!isValidEmail(customer.getCustEmail())) {
                response.put("error", "Invalid email format");
                return ResponseEntity.badRequest().body(response);
            }

            // Check if customer exists
            if (!customerService.existsById(customer.getCustId())) {
                response.put("error", "Customer not found with ID: " + customer.getCustId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Check if email is already used by another customer
            Optional<Customer> existingCustomerWithEmail = customerService.findByEmail(customer.getCustEmail());
            if (existingCustomerWithEmail.isPresent() &&
                    !existingCustomerWithEmail.get().getCustId().equals(customer.getCustId())) {
                response.put("error", "Email is already used by another customer");
                return ResponseEntity.badRequest().body(response);
            }

            customerService.updateCustomer(customer);
            response.put("message", "Customer updated successfully!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Failed to update customer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable Integer id) {
        Map<String, String> response = new HashMap<>();

        try {
            // Validation checks
            if (id == null || id <= 0) {
                response.put("error", "Invalid customer ID. ID must be a positive number");
                return ResponseEntity.badRequest().body(response);
            }

            // Check if customer exists
            if (!customerService.existsById(id)) {
                response.put("error", "Customer not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            customerService.deleteCustomer(id);
            response.put("message", "Customer deleted successfully!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Failed to delete customer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        return email.matches(emailRegex);
    }
}