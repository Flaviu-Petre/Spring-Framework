package com.spring_rest.testing_with_Junit5_Mockito.service;

import com.spring_rest.testing_with_Junit5_Mockito.entity.Customer;
import com.spring_rest.testing_with_Junit5_Mockito.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service Tests")
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer1;
    private Customer customer2;
    private Customer savedCustomer;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(null, "John Doe", "1234567890");
        customer2 = new Customer(null, "Jane Smith", "9876543210");
        savedCustomer = new Customer(1L, "John Doe", "1234567890");
    }

    @Test
    @DisplayName("Create Customer - Should save and return customer")
    void testCreateCustomer_ShouldSaveAndReturnCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        Customer result = customerService.createCustomer(customer1);

        assertThat(result).isNotNull();
        assertThat(result.getCustId()).isEqualTo(1L);
        assertThat(result.getCustName()).isEqualTo("John Doe");
        assertThat(result.getCustContactNo()).isEqualTo("1234567890");

        verify(customerRepository, times(1)).save(customer1);
    }

    @Test
    @DisplayName("Create Customer - Should handle null input")
    void testCreateCustomer_WithNullInput_ShouldThrowException() {
        assertThatThrownBy(() -> customerService.createCustomer(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Customer cannot be null");

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Create Customer - Should handle repository exception")
    void testCreateCustomer_WhenRepositoryThrowsException_ShouldPropagateException() {
        when(customerRepository.save(any(Customer.class)))
                .thenThrow(new RuntimeException("Database connection failed"));

        assertThatThrownBy(() -> customerService.createCustomer(customer1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database connection failed");

        verify(customerRepository, times(1)).save(customer1);
    }

    @Test
    @DisplayName("Create Customer - Should verify correct customer object is passed")
    void testCreateCustomer_ShouldVerifyCorrectCustomerIsPassed() {
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        customerService.createCustomer(customer1);

        verify(customerRepository).save(argThat(customer ->
                customer.getCustName().equals("John Doe") &&
                        customer.getCustContactNo().equals("1234567890") &&
                        customer.getCustId() == null
        ));
    }

    @Test
    @DisplayName("Get All Customers - Should return list of customers")
    void testGetAllCustomers_ShouldReturnListOfCustomers() {
        List<Customer> expectedCustomers = Arrays.asList(
                new Customer(1L, "John Doe", "1234567890"),
                new Customer(2L, "Jane Smith", "9876543210")
        );
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        List<Customer> result = customerService.getAllCustomers();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCustName()).isEqualTo("John Doe");
        assertThat(result.get(1).getCustName()).isEqualTo("Jane Smith");

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Get All Customers - Should return empty list when no customers exist")
    void testGetAllCustomers_WhenNoCustomersExist_ShouldReturnEmptyList() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Customer> result = customerService.getAllCustomers();

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Get All Customers - Should handle repository exception")
    void testGetAllCustomers_WhenRepositoryThrowsException_ShouldPropagateException() {
        when(customerRepository.findAll())
                .thenThrow(new RuntimeException("Database connection failed"));

        assertThatThrownBy(() -> customerService.getAllCustomers())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database connection failed");

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Get All Customers - Should return exact same list from repository")
    void testGetAllCustomers_ShouldReturnExactSameListFromRepository() {
        List<Customer> repositoryResult = Arrays.asList(savedCustomer);
        when(customerRepository.findAll()).thenReturn(repositoryResult);

        List<Customer> result = customerService.getAllCustomers();

        assertThat(result).isSameAs(repositoryResult);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Create Customer - Should handle customer with empty name")
    void testCreateCustomer_WithEmptyName_ShouldStillCallRepository() {
        Customer customerWithEmptyName = new Customer(null, "", "1234567890");
        Customer savedCustomerWithEmptyName = new Customer(1L, "", "1234567890");
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomerWithEmptyName);

        Customer result = customerService.createCustomer(customerWithEmptyName);

        assertThat(result).isNotNull();
        assertThat(result.getCustName()).isEmpty();
        assertThat(result.getCustId()).isEqualTo(1L);

        verify(customerRepository, times(1)).save(customerWithEmptyName);
    }

    @Test
    @DisplayName("Create Customer - Should handle customer with empty contact")
    void testCreateCustomer_WithEmptyContact_ShouldStillCallRepository() {
        Customer customerWithEmptyContact = new Customer(null, "John Doe", "");
        Customer savedCustomerWithEmptyContact = new Customer(1L, "John Doe", "");
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomerWithEmptyContact);

        Customer result = customerService.createCustomer(customerWithEmptyContact);

        assertThat(result).isNotNull();
        assertThat(result.getCustContactNo()).isEmpty();
        assertThat(result.getCustId()).isEqualTo(1L);

        verify(customerRepository, times(1)).save(customerWithEmptyContact);
    }

    @Test
    @DisplayName("Repository Interaction - Should verify no unnecessary calls")
    void testRepositoryInteraction_ShouldVerifyNoUnnecessaryCalls() {
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerRepository.findAll()).thenReturn(Arrays.asList(savedCustomer));

        customerService.createCustomer(customer1);

        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(customerRepository, never()).findAll();
        verify(customerRepository, never()).findById(any(Long.class));
        verify(customerRepository, never()).delete(any(Customer.class));
        verify(customerRepository, never()).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("Multiple Service Calls - Should handle multiple operations correctly")
    void testMultipleServiceCalls_ShouldHandleCorrectly() {
        Customer savedCustomer1 = new Customer(1L, "John Doe", "1234567890");
        Customer savedCustomer2 = new Customer(2L, "Jane Smith", "9876543210");

        when(customerRepository.save(customer1)).thenReturn(savedCustomer1);
        when(customerRepository.save(customer2)).thenReturn(savedCustomer2);
        when(customerRepository.findAll()).thenReturn(Arrays.asList(savedCustomer1, savedCustomer2));

        Customer result1 = customerService.createCustomer(customer1);
        Customer result2 = customerService.createCustomer(customer2);
        List<Customer> allCustomers = customerService.getAllCustomers();

        assertThat(result1.getCustId()).isEqualTo(1L);
        assertThat(result2.getCustId()).isEqualTo(2L);
        assertThat(allCustomers).hasSize(2);

        verify(customerRepository, times(2)).save(any(Customer.class));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Service State - Should be stateless")
    void testServiceState_ShouldBeStateless() {
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        customerService.createCustomer(customer1);
        customerService.createCustomer(customer2);

        verify(customerRepository, times(2)).save(any(Customer.class));

        verify(customerRepository).save(customer1);
        verify(customerRepository).save(customer2);
    }

    @Test
    @DisplayName("Error Handling - Should not catch or modify exceptions")
    void testErrorHandling_ShouldNotCatchOrModifyExceptions() {
        RuntimeException originalException = new RuntimeException("Original error message");
        when(customerRepository.save(any(Customer.class))).thenThrow(originalException);

        assertThatThrownBy(() -> customerService.createCustomer(customer1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Original error message")
                .isSameAs(originalException);
    }
}