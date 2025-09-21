package controller.versioning;

import dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.CustomerResponse;
import service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerMediaTypeVersioningController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(produces = "application/vnd.company.crm-v1+json")
    public ResponseEntity<List<CustomerDTO>> getAllCustomersV1() {
        List<CustomerDTO> customers = customerService.getAllCustomersDTO();
        return ResponseEntity.ok(customers);
    }

    @GetMapping(produces = "application/vnd.company.crm-v2+json")
    public ResponseEntity<CustomerResponse> getAllCustomersV2(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "custId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        CustomerResponse customers = customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(customers);
    }

    @GetMapping(value = "/{id}", produces = "application/vnd.company.crm-v1+json")
    public ResponseEntity<CustomerDTO> getCustomerByIdV1(@PathVariable("id") long custId) {
        CustomerDTO customer = customerService.getCustomerByIdDTO(custId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping(value = "/{id}", produces = "application/vnd.company.crm-v2+json")
    public ResponseEntity<CustomerDTO> getCustomerByIdV2(@PathVariable("id") long custId) {
        CustomerDTO customer = customerService.getCustomerByIdDTO(custId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping(value = "/search", produces = "application/vnd.company.crm-v1+json")
    public ResponseEntity<List<CustomerDTO>> searchCustomersV1(@RequestParam("name") String name) {
        List<CustomerDTO> allCustomers = customerService.getAllCustomersDTO();

        List<CustomerDTO> filteredCustomers = allCustomers.stream()
                .filter(customer -> customer.getCustName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        return ResponseEntity.ok(filteredCustomers);
    }

    @GetMapping(value = "/search", produces = "application/vnd.company.crm-v2+json")
    public ResponseEntity<CustomerResponse> searchCustomersV2(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "custId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        CustomerResponse customerResponse = customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);

        // Apply filters
        List<CustomerDTO> filteredCustomers = customerResponse.getCustomers().stream()
                .map(customer -> new CustomerDTO(
                        customer.getCustId(),
                        customer.getCustName(),
                        customer.getCustContactNo(),
                        customer.getCustEmail(),
                        customer.getCustCity()
                ))
                .filter(customer -> name == null || customer.getCustName().toLowerCase().contains(name.toLowerCase()))
                .filter(customer -> email == null || customer.getCustEmail().toLowerCase().contains(email.toLowerCase()))
                .filter(customer -> city == null || customer.getCustCity().toLowerCase().contains(city.toLowerCase()))
                .toList();

        customerResponse.setCustomers(filteredCustomers.stream()
                .map(dto -> {
                    model.Customer customer = new model.Customer();
                    customer.setCustId(dto.getCustId());
                    customer.setCustName(dto.getCustName());
                    customer.setCustContactNo(dto.getCustContactNo());
                    customer.setCustEmail(dto.getCustEmail());
                    customer.setCustCity(dto.getCustCity());
                    return customer;
                })
                .toList());

        return ResponseEntity.ok(customerResponse);
    }
}