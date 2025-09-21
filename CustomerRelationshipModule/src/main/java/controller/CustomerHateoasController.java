package controller;

import dto.CustomerDTO;
import dto.CustomerHateoasDTO;
import dto.CustomerCollectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.CustomerResponse;
import service.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerHateoasController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<String>>> getCustomerOperations() {
        CollectionModel<EntityModel<String>> operations = CollectionModel.empty();

        operations.add(linkTo(methodOn(CustomerHateoasController.class).getCustomerOperations()).withSelfRel());
        operations.add(linkTo(methodOn(CustomerHateoasController.class).getAllCustomers()).withRel("get-all-customers"));
        operations.add(linkTo(methodOn(CustomerHateoasController.class).getCustomerById(null)).withRel("get-customer-by-id"));
        operations.add(linkTo(methodOn(CustomerHateoasController.class).getCustomersWithPagination(0, 10, "custId", "asc")).withRel("get-customers-paginated"));

        operations.add(linkTo(CustomerController.class).slash("add").withRel("add-customer").withType("POST"));
        operations.add(linkTo(CustomerController.class).slash("update").withRel("update-customer").withType("PUT"));
        operations.add(linkTo(CustomerController.class).slash("{id}").withRel("delete-customer").withType("DELETE"));

        return ResponseEntity.ok(operations);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<CustomerHateoasDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomersDTO();

        List<CustomerHateoasDTO> customerHateoas = customers.stream()
                .map(customer -> {
                    CustomerHateoasDTO hateoasDTO = new CustomerHateoasDTO(customer);
                    addCustomerLinks(hateoasDTO);
                    return hateoasDTO;
                })
                .collect(Collectors.toList());

        CollectionModel<CustomerHateoasDTO> collectionModel = CollectionModel.of(customerHateoas);

        addCollectionLinks(collectionModel);

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerHateoasDTO> getCustomerById(@PathVariable Integer id) {
        CustomerDTO customer = customerService.getCustomerByIdDTO(id);
        CustomerHateoasDTO hateoasDTO = new CustomerHateoasDTO(customer);

        addCustomerLinks(hateoasDTO);

        return ResponseEntity.ok(hateoasDTO);
    }

    @GetMapping("/paginated")
    public ResponseEntity<CustomerCollectionModel> getCustomersWithPagination(
            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "custId") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {

        CustomerResponse customerResponse = customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDir);

        List<CustomerHateoasDTO> customerHateoas = customerResponse.getCustomers().stream()
                .map(customer -> {
                    CustomerDTO dto = new CustomerDTO(
                            customer.getCustId(),
                            customer.getCustName(),
                            customer.getCustContactNo(),
                            customer.getCustEmail(),
                            customer.getCustCity()
                    );
                    CustomerHateoasDTO hateoasDTO = new CustomerHateoasDTO(dto);
                    addCustomerLinks(hateoasDTO);
                    return hateoasDTO;
                })
                .collect(Collectors.toList());

        CustomerCollectionModel collectionModel = new CustomerCollectionModel(
                customerHateoas,
                customerResponse.getPageNo(),
                customerResponse.getPageSize(),
                customerResponse.getTotalPages(),
                customerResponse.getTotalElements(),
                customerResponse.isLast()
        );

        addPaginationLinks(collectionModel, pageNo, pageSize, sortBy, sortDir, customerResponse.getTotalPages());

        return ResponseEntity.ok(collectionModel);
    }

    private void addCustomerLinks(CustomerHateoasDTO customer) {
        customer.add(linkTo(methodOn(CustomerHateoasController.class).getCustomerById(customer.getCustId())).withSelfRel());

        customer.add(linkTo(methodOn(CustomerHateoasController.class).getAllCustomers()).withRel("all-customers"));

        customer.add(linkTo(CustomerController.class).slash("update").withRel("update").withType("PUT"));
        customer.add(linkTo(CustomerController.class).slash(customer.getCustId()).withRel("delete").withType("DELETE"));

        customer.add(linkTo(methodOn(CustomerHateoasController.class).getCustomerOperations()).withRel("operations"));
    }

    private void addCollectionLinks(CollectionModel<CustomerHateoasDTO> collectionModel) {
        collectionModel.add(linkTo(methodOn(CustomerHateoasController.class).getAllCustomers()).withSelfRel());

        collectionModel.add(linkTo(methodOn(CustomerHateoasController.class).getCustomerOperations()).withRel("root"));

        collectionModel.add(linkTo(methodOn(CustomerHateoasController.class).getCustomersWithPagination(0, 10, "custId", "asc")).withRel("paginated"));

        collectionModel.add(linkTo(CustomerController.class).slash("add").withRel("add-customer").withType("POST"));
    }

    private void addPaginationLinks(CustomerCollectionModel collectionModel, int pageNo, int pageSize,
                                    String sortBy, String sortDir, int totalPages) {
        collectionModel.add(linkTo(methodOn(CustomerHateoasController.class)
                .getCustomersWithPagination(pageNo, pageSize, sortBy, sortDir)).withSelfRel());

        collectionModel.add(linkTo(methodOn(CustomerHateoasController.class)
                .getCustomersWithPagination(0, pageSize, sortBy, sortDir)).withRel(IanaLinkRelations.FIRST));

        collectionModel.add(linkTo(methodOn(CustomerHateoasController.class)
                .getCustomersWithPagination(totalPages - 1, pageSize, sortBy, sortDir)).withRel(IanaLinkRelations.LAST));

        if (pageNo > 0) {
            collectionModel.add(linkTo(methodOn(CustomerHateoasController.class)
                    .getCustomersWithPagination(pageNo - 1, pageSize, sortBy, sortDir)).withRel(IanaLinkRelations.PREV));
        }

        if (pageNo < totalPages - 1) {
            collectionModel.add(linkTo(methodOn(CustomerHateoasController.class)
                    .getCustomersWithPagination(pageNo + 1, pageSize, sortBy, sortDir)).withRel(IanaLinkRelations.NEXT));
        }

        collectionModel.add(linkTo(methodOn(CustomerHateoasController.class).getCustomerOperations()).withRel("operations"));

        collectionModel.add(linkTo(methodOn(CustomerHateoasController.class).getAllCustomers()).withRel("all-customers"));
    }
}