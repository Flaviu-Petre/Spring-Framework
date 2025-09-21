package controller.v1;

import dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerControllerV1 {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomersDTO();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") long custId) {
        CustomerDTO customer = customerService.getCustomerByIdDTO(custId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomersByName(@RequestParam("name") String name) {
        List<CustomerDTO> allCustomers = customerService.getAllCustomersDTO();

        // Simple filtering by name (case-insensitive)
        List<CustomerDTO> filteredCustomers = allCustomers.stream()
                .filter(customer -> customer.getCustName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        return ResponseEntity.ok(filteredCustomers);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<CustomerDTO>> getCustomersByCity(@PathVariable("city") String city) {
        List<CustomerDTO> allCustomers = customerService.getAllCustomersDTO();

        // Filter by city
        List<CustomerDTO> customersByCity = allCustomers.stream()
                .filter(customer -> customer.getCustCity().equalsIgnoreCase(city))
                .toList();

        return ResponseEntity.ok(customersByCity);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCustomerCount() {
        List<CustomerDTO> customers = customerService.getAllCustomersDTO();
        return ResponseEntity.ok((long) customers.size());
    }

    @GetMapping("/version")
    public ResponseEntity<String> getApiVersion() {
        return ResponseEntity.ok("Customer API Version 1.0 - Basic Operations");
    }
}