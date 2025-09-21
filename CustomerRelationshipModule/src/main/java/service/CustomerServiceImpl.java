package service;

import dto.CustomerDTO;
import exception.CustomerNotFoundException;
import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import repository.CustomerRepository;
import response.CustomerResponse;
import util.CustomerMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // DTO-based methods
    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.toDTO(savedCustomer);
    }

    @Override
    public List<CustomerDTO> getAllCustomersDTO() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerByIdDTO(long custId) {
        Customer customer = customerRepository.findById(custId)
                .orElseThrow(() -> new CustomerNotFoundException(custId));
        return CustomerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById((long) customerDTO.getCustId())
                .orElseThrow(() -> new CustomerNotFoundException(customerDTO.getCustId()));

        // Update existing customer with DTO data
        CustomerMapper.updateEntityFromDTO(existingCustomer, customerDTO);

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return CustomerMapper.toDTO(updatedCustomer);
    }


    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(long custId) {
        return customerRepository.findById(custId)
                .orElseThrow(() -> new CustomerNotFoundException(custId));
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (!customerRepository.existsById((long) customer.getCustId())) {
            throw new CustomerNotFoundException(customer.getCustId());
        }
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(long custId) {
        if (!customerRepository.existsById(custId)) {
            throw new CustomerNotFoundException(custId);
        }
        customerRepository.deleteById(custId);
    }

    @Override
    public CustomerResponse getAllCustomers(int pageNo, int pageSize) {
        return getAllCustomers(pageNo, pageSize, "id", "asc");
    }

    @Override
    public CustomerResponse getAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomers(customerPage.getContent());
        customerResponse.setPageNo(customerPage.getNumber());
        customerResponse.setPageSize(customerPage.getSize());
        customerResponse.setTotalPages(customerPage.getTotalPages());
        customerResponse.setTotalElements(customerPage.getTotalElements());
        customerResponse.setLast(customerPage.isLast());

        return customerResponse;
    }
}