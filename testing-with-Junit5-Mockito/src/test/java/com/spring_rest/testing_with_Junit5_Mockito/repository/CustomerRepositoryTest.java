package com.spring_rest.testing_with_Junit5_Mockito.repository;

import com.spring_rest.testing_with_Junit5_Mockito.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("Customer Repository Tests")
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(null, "John Doe", "1234567890");
        customer2 = new Customer(null, "Jane Smith", "9876543210");
    }

    @Test
    @DisplayName("Save Customer - Should persist customer and generate ID")
    void testSaveCustomer_ShouldPersistCustomerAndGenerateId() {
        Customer savedCustomer = customerRepository.save(customer1);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustId()).isNotNull();
        assertThat(savedCustomer.getCustName()).isEqualTo("John Doe");
        assertThat(savedCustomer.getCustContactNo()).isEqualTo("1234567890");
    }

    @Test
    @DisplayName("Find By ID - Should return customer when exists")
    void testFindById_WhenCustomerExists_ShouldReturnCustomer() {
        Customer persistedCustomer = entityManager.persistAndFlush(customer1);
        entityManager.clear();

        Optional<Customer> foundCustomer = customerRepository.findById(persistedCustomer.getCustId());

        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getCustId()).isEqualTo(persistedCustomer.getCustId());
        assertThat(foundCustomer.get().getCustName()).isEqualTo("John Doe");
        assertThat(foundCustomer.get().getCustContactNo()).isEqualTo("1234567890");
    }

    @Test
    @DisplayName("Find By ID - Should return empty when customer doesn't exist")
    void testFindById_WhenCustomerDoesNotExist_ShouldReturnEmpty() {
        Optional<Customer> foundCustomer = customerRepository.findById(999L);

        assertThat(foundCustomer).isNotPresent();
    }

    @Test
    @DisplayName("Find All - Should return all customers")
    void testFindAll_ShouldReturnAllCustomers() {
        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);
        entityManager.clear();

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).hasSize(2);
        assertThat(customers)
                .extracting(Customer::getCustName)
                .containsExactlyInAnyOrder("John Doe", "Jane Smith");
        assertThat(customers)
                .extracting(Customer::getCustContactNo)
                .containsExactlyInAnyOrder("1234567890", "9876543210");
    }

    @Test
    @DisplayName("Find All - Should return empty list when no customers exist")
    void testFindAll_WhenNoCustomers_ShouldReturnEmptyList() {
        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).isEmpty();
    }

    @Test
    @DisplayName("Update Customer - Should update existing customer")
    void testUpdateCustomer_ShouldUpdateExistingCustomer() {
        Customer persistedCustomer = entityManager.persistAndFlush(customer1);
        entityManager.clear();

        persistedCustomer.setCustName("John Smith");
        persistedCustomer.setCustContactNo("5555555555");
        Customer updatedCustomer = customerRepository.save(persistedCustomer);

        assertThat(updatedCustomer.getCustId()).isEqualTo(persistedCustomer.getCustId());
        assertThat(updatedCustomer.getCustName()).isEqualTo("John Smith");
        assertThat(updatedCustomer.getCustContactNo()).isEqualTo("5555555555");

        Customer fromDb = entityManager.find(Customer.class, persistedCustomer.getCustId());
        assertThat(fromDb.getCustName()).isEqualTo("John Smith");
        assertThat(fromDb.getCustContactNo()).isEqualTo("5555555555");
    }

    @Test
    @DisplayName("Delete By ID - Should delete existing customer")
    void testDeleteById_ShouldDeleteExistingCustomer() {
        Customer persistedCustomer = entityManager.persistAndFlush(customer1);
        Long customerId = persistedCustomer.getCustId();
        entityManager.clear();

        customerRepository.deleteById(customerId);

        Customer deletedCustomer = entityManager.find(Customer.class, customerId);
        assertThat(deletedCustomer).isNull();

        Optional<Customer> foundCustomer = customerRepository.findById(customerId);
        assertThat(foundCustomer).isNotPresent();
    }

    @Test
    @DisplayName("Delete - Should delete customer entity")
    void testDelete_ShouldDeleteCustomerEntity() {
        Customer persistedCustomer = entityManager.persistAndFlush(customer1);
        Long customerId = persistedCustomer.getCustId();
        entityManager.clear();

        customerRepository.delete(persistedCustomer);

        Customer deletedCustomer = entityManager.find(Customer.class, customerId);
        assertThat(deletedCustomer).isNull();
    }

    @Test
    @DisplayName("Exists By ID - Should return true when customer exists")
    void testExistsById_WhenCustomerExists_ShouldReturnTrue() {
        Customer persistedCustomer = entityManager.persistAndFlush(customer1);
        entityManager.clear();

        boolean exists = customerRepository.existsById(persistedCustomer.getCustId());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Exists By ID - Should return false when customer doesn't exist")
    void testExistsById_WhenCustomerDoesNotExist_ShouldReturnFalse() {
        boolean exists = customerRepository.existsById(999L);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Count - Should return correct number of customers")
    void testCount_ShouldReturnCorrectCount() {
        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);
        entityManager.clear();

        long count = customerRepository.count();

        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Count - Should return zero when no customers exist")
    void testCount_WhenNoCustomers_ShouldReturnZero() {
        long count = customerRepository.count();

        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("Save Customer with Null Name - Should throw exception")
    void testSaveCustomer_WithNullName_ShouldThrowException() {
        Customer invalidCustomer = new Customer(null, null, "1234567890");

        assertThatThrownBy(() -> {
            customerRepository.save(invalidCustomer);
            entityManager.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Save Customer with Duplicate Contact - Should allow duplicate contacts")
    void testSaveCustomer_WithDuplicateContact_ShouldAllowDuplicates() {
        Customer customer3 = new Customer(null, "Bob Wilson", "1234567890"); // Same contact as customer1
        entityManager.persistAndFlush(customer1);
        entityManager.clear();

        Customer savedCustomer = customerRepository.save(customer3);


        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCustId()).isNotNull();
        assertThat(savedCustomer.getCustContactNo()).isEqualTo("1234567890");

        List<Customer> allCustomers = customerRepository.findAll();
        assertThat(allCustomers).hasSize(2);
    }

    @Test
    @DisplayName("Save All - Should save multiple customers")
    void testSaveAll_ShouldSaveMultipleCustomers() {
        List<Customer> customersToSave = List.of(customer1, customer2);

        List<Customer> savedCustomers = customerRepository.saveAll(customersToSave);

        assertThat(savedCustomers).hasSize(2);
        assertThat(savedCustomers).allMatch(customer -> customer.getCustId() != null);

        List<Customer> allCustomers = customerRepository.findAll();
        assertThat(allCustomers).hasSize(2);
    }

    @Test
    @DisplayName("Delete All - Should delete all customers")
    void testDeleteAll_ShouldDeleteAllCustomers() {
        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);
        entityManager.clear();

        customerRepository.deleteAll();

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).isEmpty();

        long count = customerRepository.count();
        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("Find All By ID - Should return customers with matching IDs")
    void testFindAllById_ShouldReturnMatchingCustomers() {
        Customer persistedCustomer1 = entityManager.persistAndFlush(customer1);
        Customer persistedCustomer2 = entityManager.persistAndFlush(customer2);
        entityManager.clear();

        List<Long> ids = List.of(persistedCustomer1.getCustId(), persistedCustomer2.getCustId());

        List<Customer> foundCustomers = customerRepository.findAllById(ids);

        assertThat(foundCustomers).hasSize(2);
        assertThat(foundCustomers)
                .extracting(Customer::getCustId)
                .containsExactlyInAnyOrderElementsOf(ids);
    }

    @Test
    @DisplayName("Transaction Rollback - Should rollback on exception")
    void testTransactionRollback_ShouldRollbackOnException() {
        entityManager.persistAndFlush(customer1);
        long initialCount = customerRepository.count();

        assertThatThrownBy(() -> {
            Customer invalidCustomer = new Customer(null, null, "1234567890");
            customerRepository.save(invalidCustomer);
            entityManager.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);

        long finalCount = customerRepository.count();
        assertThat(finalCount).isEqualTo(initialCount);
    }
}