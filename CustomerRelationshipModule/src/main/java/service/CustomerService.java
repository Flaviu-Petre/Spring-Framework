package service;

import dto.CustomerDTO;
import model.Customer;
import response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    // DTO-based methods
    CustomerDTO addCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> getAllCustomersDTO();
    CustomerDTO getCustomerByIdDTO(long custId);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(long custId);

    // Original entity-based methods (for backward compatibility)
    void addCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(long custId);
    void updateCustomer(Customer customer);

    // Pagination methods
    CustomerResponse getAllCustomers(int pageNo, int pageSize);
    CustomerResponse getAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir);
}