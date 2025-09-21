package controller.v2;

import dto.ApiCapabilitiesDTO;
import dto.CustomerDTO;
import dto.CustomerStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.CustomerResponse;
import service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/customers")
public class CustomerControllerV2 {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerResponse> getAllCustomers(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "custId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        CustomerResponse customers = customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") long custId) {
        CustomerDTO customer = customerService.getCustomerByIdDTO(custId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/search")
    public ResponseEntity<CustomerResponse> searchCustomers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "custId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        CustomerResponse customerResponse = customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);

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
                .filter(customer -> phone == null || customer.getCustContactNo().contains(phone))
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

    @GetMapping("/city/{city}")
    public ResponseEntity<CustomerResponse> getCustomersByCity(
            @PathVariable("city") String city,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "custName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        CustomerResponse customerResponse = customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);

        // Filter by city
        List<CustomerDTO> customersByCity = customerResponse.getCustomers().stream()
                .map(customer -> new CustomerDTO(
                        customer.getCustId(),
                        customer.getCustName(),
                        customer.getCustContactNo(),
                        customer.getCustEmail(),
                        customer.getCustCity()
                ))
                .filter(customer -> customer.getCustCity().equalsIgnoreCase(city))
                .toList();

        customerResponse.setCustomers(customersByCity.stream()
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

    @GetMapping("/stats")
    public ResponseEntity<CustomerStatsDTO> getCustomerStatistics() {
        List<CustomerDTO> allCustomers = customerService.getAllCustomersDTO();

        CustomerStatsDTO stats = new CustomerStatsDTO();
        stats.setTotalCustomers(allCustomers.size());
        stats.setTotalCities((int) allCustomers.stream()
                .map(CustomerDTO::getCustCity)
                .distinct()
                .count());

        // Group by city
        var customersByCity = allCustomers.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        CustomerDTO::getCustCity,
                        java.util.stream.Collectors.counting()
                ));
        stats.setCustomersByCity(customersByCity);

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/sorted")
    public ResponseEntity<CustomerResponse> getCustomersSorted(
            @RequestParam(value = "sortBy", defaultValue = "custName") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {

        CustomerResponse customers = customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(customers);
    }


    @GetMapping("/version")
    public ResponseEntity<String> getApiVersion() {
        return ResponseEntity.ok("Customer API Version 2.0 - Advanced Operations with Pagination & Sorting");
    }

    @GetMapping("/capabilities")
    public ResponseEntity<ApiCapabilitiesDTO> getApiCapabilities() {
        ApiCapabilitiesDTO capabilities = new ApiCapabilitiesDTO();
        capabilities.setVersion("2.0");
        capabilities.setSupportsPagination(true);
        capabilities.setSupportsSorting(true);
        capabilities.setSupportsFiltering(true);
        capabilities.setMaxPageSize(100);
        capabilities.setSupportedSortFields(List.of("custId", "custName", "custEmail", "custCity", "custContactNo"));
        capabilities.setSupportedSortDirections(List.of("asc", "desc"));

        return ResponseEntity.ok(capabilities);
    }
}