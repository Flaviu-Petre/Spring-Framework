package com.spring_rest.testing_with_Junit5_Mockito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_rest.testing_with_Junit5_Mockito.entity.Customer;
import com.spring_rest.testing_with_Junit5_Mockito.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Controller Tests")
class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private ObjectMapper objectMapper;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        customer1 = new Customer(1L, "John Doe", "1234567890");
        customer2 = new Customer(2L, "Jane Smith", "9876543210");
    }

    @Test
    @DisplayName("POST /api/customers - Create Customer with Valid Data")
    void testCreateCustomer_WithValidData_ShouldReturnCreated() throws Exception {
        Customer inputCustomer = new Customer(null, "John Doe", "1234567890");
        Customer savedCustomer = new Customer(1L, "John Doe", "1234567890");

        when(customerService.createCustomer(any(Customer.class))).thenReturn(savedCustomer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputCustomer)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.custId", is(1)))
                .andExpect(jsonPath("$.custName", is("John Doe")))
                .andExpect(jsonPath("$.custContactNo", is("1234567890")));

        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    @DisplayName("POST /api/customers - Create Customer with Invalid Data")
    void testCreateCustomer_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        Customer invalidCustomer = new Customer(null, "", ""); // Empty name and contact

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCustomer)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(customerService, never()).createCustomer(any(Customer.class));
    }

    @Test
    @DisplayName("POST /api/customers - Create Customer with Null Body")
    void testCreateCustomer_WithNullBody_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(customerService, never()).createCustomer(any(Customer.class));
    }

    @Test
    @DisplayName("GET /api/customers - Get All Customers with Data")
    void testGetAllCustomers_WithData_ShouldReturnCustomerList() throws Exception {
        List<Customer> customerList = Arrays.asList(customer1, customer2);
        when(customerService.getAllCustomers()).thenReturn(customerList);


        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].custId", is(1)))
                .andExpect(jsonPath("$[0].custName", is("John Doe")))
                .andExpect(jsonPath("$[0].custContactNo", is("1234567890")))
                .andExpect(jsonPath("$[1].custId", is(2)))
                .andExpect(jsonPath("$[1].custName", is("Jane Smith")))
                .andExpect(jsonPath("$[1].custContactNo", is("9876543210")));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("GET /api/customers - Get All Customers with Empty List")
    void testGetAllCustomers_WithEmptyList_ShouldReturnEmptyArray() throws Exception {
        List<Customer> emptyList = Arrays.asList();
        when(customerService.getAllCustomers()).thenReturn(emptyList);

        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("GET /api/customers - Service throws Exception")
    void testGetAllCustomers_ServiceThrowsException_ShouldReturnInternalServerError() throws Exception {
        when(customerService.getAllCustomers()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("POST /api/customers - Service throws Exception")
    void testCreateCustomer_ServiceThrowsException_ShouldReturnInternalServerError() throws Exception {
        Customer inputCustomer = new Customer(null, "John Doe", "1234567890");
        when(customerService.createCustomer(any(Customer.class)))
                .thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputCustomer)))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    @DisplayName("POST /api/customers - Verify Service Method Called with Correct Data")
    void testCreateCustomer_VerifyServiceMethodCalledWithCorrectData() throws Exception {
        Customer inputCustomer = new Customer(null, "Test User", "5555555555");
        Customer savedCustomer = new Customer(1L, "Test User", "5555555555");

        when(customerService.createCustomer(any(Customer.class))).thenReturn(savedCustomer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputCustomer)))
                .andExpect(status().isCreated());

        verify(customerService).createCustomer(argThat(customer ->
                customer.getCustName().equals("Test User") &&
                        customer.getCustContactNo().equals("5555555555") &&
                        customer.getCustId() == null
        ));
    }

    @Test
    @DisplayName("GET /api/customers - Verify Response Headers")
    void testGetAllCustomers_VerifyResponseHeaders() throws Exception {
        List<Customer> customerList = Arrays.asList(customer1);
        when(customerService.getAllCustomers()).thenReturn(customerList);

        mockMvc.perform(get("/api/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$", hasSize(1)));
    }
}