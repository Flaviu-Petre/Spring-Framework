package com.spring_rest.testing_with_Junit5_Mockito.repository;

import com.spring_rest.testing_with_Junit5_Mockito.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
