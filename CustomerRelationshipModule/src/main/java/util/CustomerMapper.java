package util;

import dto.CustomerDTO;
import model.Customer;

public class CustomerMapper {

    // Convert Customer entity to CustomerDTO
    public static CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO dto = new CustomerDTO();
        dto.setCustId(customer.getCustId());
        dto.setCustName(customer.getCustName());
        dto.setCustContactNo(customer.getCustContactNo());
        dto.setCustEmail(customer.getCustEmail());
        dto.setCustCity(customer.getCustCity());

        return dto;
    }

    // Convert CustomerDTO to Customer entity
    public static Customer toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        Customer customer = new Customer();
        if (dto.getCustId() != null) {
            customer.setCustId(dto.getCustId());
        }
        customer.setCustName(dto.getCustName());
        customer.setCustContactNo(dto.getCustContactNo());
        customer.setCustEmail(dto.getCustEmail());
        customer.setCustCity(dto.getCustCity());

        return customer;
    }

    // Update existing Customer entity with DTO data
    public static void updateEntityFromDTO(Customer customer, CustomerDTO dto) {
        if (customer == null || dto == null) {
            return;
        }

        customer.setCustName(dto.getCustName());
        customer.setCustContactNo(dto.getCustContactNo());
        customer.setCustEmail(dto.getCustEmail());
        customer.setCustCity(dto.getCustCity());
    }
}