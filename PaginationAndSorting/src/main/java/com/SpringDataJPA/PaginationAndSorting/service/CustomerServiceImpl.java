package com.SpringDataJPA.PaginationAndSorting.service;

import com.SpringDataJPA.PaginationAndSorting.Enums.Address;
import com.SpringDataJPA.PaginationAndSorting.Exeptions.CustomerUnavailableExeption;
import com.SpringDataJPA.PaginationAndSorting.Response.CustomerResponse;
import jakarta.transaction.Transactional;
import com.SpringDataJPA.PaginationAndSorting.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.SpringDataJPA.PaginationAndSorting.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^(\\+?1-?)?\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$"
    );

    @Override
    @Transactional
    public void addCustomer(Customer customer) {
        if (!validateCustomerDetails(customer)) {
            throw new CustomerUnavailableExeption("Customer validation failed");
        }

        if (existsByEmail(customer.getCustEmail())) {
            throw new CustomerUnavailableExeption("Customer with this email already exists");
        }

        Customer savedCustomer = customerRepository.save(customer);

        if (savedCustomer == null || savedCustomer.getCustId() == null) {
            throw new CustomerUnavailableExeption("Failed to save customer");
        }
    }

    public boolean validateCustomerDetails(Customer customer) {
        if (customer == null) {
            return false;
        }

        return validateCustomerName(customer.getCustName()) &&
                validateEmail(customer.getCustEmail()) &&
                validateContactNumber(customer.getContactNo()) &&
                validateCity(customer.getCustCity()) &&
                validateAddress(customer.getAddress());
    }

    public boolean validateCustomerName(String custName) {
        if (custName == null || custName.trim().isEmpty()) {
            return false;
        }

        custName = custName.trim();

        if (custName.length() < 2 || custName.length() > 50) {
            return false;
        }

        return custName.matches("^[a-zA-Z\\s'-]+$");
    }

    public boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        email = email.trim().toLowerCase();

        if (email.length() > 100) {
            return false;
        }

        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean validateContactNumber(String contactNo) {
        if (contactNo == null || contactNo.trim().isEmpty()) {
            return false;
        }

        contactNo = contactNo.trim();

        String digitsOnly = contactNo.replaceAll("[^0-9]", "");

        if (digitsOnly.length() != 10) {
            return false;
        }

        return PHONE_PATTERN.matcher(contactNo).matches();
    }


    public boolean validateCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            return false;
        }

        city = city.trim();

        if (city.length() < 2 || city.length() > 50) {
            return false;
        }

        return city.matches("^[a-zA-Z\\s-]+$");
    }

    public boolean validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return false;
        }

        // Check if address exists in the enum
        return Arrays.stream(Address.values())
                .anyMatch(addr -> addr.name().equalsIgnoreCase(address.trim()));
    }

    public String validateCustomerDetailsWithMessages(Customer customer) {
        if (customer == null) {
            return "Customer object cannot be null";
        }

        StringBuilder errors = new StringBuilder();

        if (!validateCustomerName(customer.getCustName())) {
            errors.append("Invalid customer name. Must be 2-50 characters, letters only. ");
        }

        if (!validateEmail(customer.getCustEmail())) {
            errors.append("Invalid email format. ");
        }

        if (!validateContactNumber(customer.getContactNo())) {
            errors.append("Invalid contact number. Must be a valid 10-digit US phone number. ");
        }

        if (!validateCity(customer.getCustCity())) {
            errors.append("Invalid city name. Must be 2-50 characters, letters only. ");
        }

        if (!validateAddress(customer.getAddress())) {
            errors.append("Invalid address. Must be one of: " +
                    Arrays.toString(Address.values()) + ". ");
        }

        return errors.toString().trim();
    }

    public void validateAndThrow(Customer customer) {
        if (customer == null) {
            throw new CustomerUnavailableExeption("Customer object cannot be null");
        }

        if (!validateCustomerName(customer.getCustName())) {
            throw new CustomerUnavailableExeption("Invalid customer name. Must be 2-50 characters, letters only.");
        }

        if (!validateEmail(customer.getCustEmail())) {
            throw new CustomerUnavailableExeption("Invalid email format.");
        }

        if (existsByEmail(customer.getCustEmail())) {
            throw new CustomerUnavailableExeption("Customer with this email already exists.");
        }

        if (!validateContactNumber(customer.getContactNo())) {
            throw new CustomerUnavailableExeption("Invalid contact number. Must be a valid 10-digit US phone number.");
        }

        if (!validateCity(customer.getCustCity())) {
            throw new CustomerUnavailableExeption("Invalid city name. Must be 2-50 characters, letters only.");
        }

        if (!validateAddress(customer.getAddress())) {
            throw new CustomerUnavailableExeption("Invalid address. Must be one of: " +
                    Arrays.toString(Address.values()));
        }
    }

    @Override
    @Transactional
    public void updateCustomer(Customer customer) {
        if (!existsById(customer.getCustId())) {
            throw new CustomerUnavailableExeption("Customer not found with ID: " + customer.getCustId());
        }

        if (!validateCustomerDetails(customer)) {
            throw new CustomerUnavailableExeption("Customer validation failed: " +
                    validateCustomerDetailsWithMessages(customer));
        }

        Optional<Customer> existingCustomer = findByEmail(customer.getCustEmail());
        if (existingCustomer.isPresent() &&
                !existingCustomer.get().getCustId().equals(customer.getCustId())) {
            throw new CustomerUnavailableExeption("Email already exists for another customer");
        }

        customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
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

    @Override
    public CustomerResponse getAllCustomers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);

        Page<Customer> all = customerRepository.findAll(pageable);
        List<Customer> customers = all.getContent().stream().toList();

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomers(customers);
        customerResponse.setPageNo(all.getNumber());
        customerResponse.setPageSize(all.getSize());
        customerResponse.setTotalElements(all.getTotalElements());
        customerResponse.setTotalPages(all.getTotalPages());
        customerResponse.setLast(all.isLast());

        return customerResponse;
    }

    @Override
    public CustomerResponse getCustomersByEmailSuffix(String emailSuffix, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);

        Page<Customer> all = customerRepository.findByCustEmailEndingWith(emailSuffix, pageable);
        List<Customer> customers = all.getContent().stream().toList();

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomers(customers);
        customerResponse.setPageNo(all.getNumber());
        customerResponse.setPageSize(all.getSize());
        customerResponse.setTotalElements(all.getTotalElements());
        customerResponse.setTotalPages(all.getTotalPages());
        customerResponse.setLast(all.isLast());

        return customerResponse;
    }
}