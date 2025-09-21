package controller;

import dto.CustomerDTO;
import jakarta.validation.Valid;
import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.CustomerResponse;
import service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<CustomerDTO> addCustomerWithValidation(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.addCustomer(customerDTO);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/dto/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomersDTO() {
        List<CustomerDTO> customers = customerService.getAllCustomersDTO();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<CustomerDTO> getCustomerByIdDTO(@PathVariable long id) {
        CustomerDTO customer = customerService.getCustomerByIdDTO(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDTO> updateCustomerWithValidation(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    // Original endpoints (for backward compatibility)
    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/getCustomerById")
    public ResponseEntity<Customer> getCustomerById(@RequestParam long custId) {
        Customer customer = customerService.getCustomerById(custId);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        customerService.updateCustomer(customer);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<Void> deleteCustomerOld(@RequestParam long custId) {
        customerService.deleteCustomer(custId);
        return ResponseEntity.noContent().build();
    }

    // Pagination endpoint
    @GetMapping
    public ResponseEntity<CustomerResponse> getCustomers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        CustomerResponse customerResponse = customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(customerResponse);
    }
}